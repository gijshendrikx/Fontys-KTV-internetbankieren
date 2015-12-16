package centralebank;

import common.IBankNaarCentrale;
import common.ICentraleNaarBank;
import common.Transactie;
import database.DatabaseConnectieMock;
import database.IDatabaseConnectie;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class CentraleBank extends UnicastRemoteObject implements ICentraleNaarBank {

    private final Map<String, IBankNaarCentrale> banken;
    private final IDatabaseConnectie database;
    private final LogWindow logger;
    private final Timer scheduler;

    public CentraleBank(String n, String b) throws RemoteException {
        banken = Collections.synchronizedMap(new HashMap<>());
        database = new DatabaseConnectieMock();
        logger = new LogWindow(n, b);
        scheduler = new Timer();
        registreer();
        setScheduler();
    }

    private void registreer() {
        try {
            Registry registry = java.rmi.registry.LocateRegistry.createRegistry(1100);
            registry.rebind("CentraleNaarBank", (ICentraleNaarBank) this);
            logger.log("Centrale bank is geregistreerd");
        } catch (RemoteException ex) {
            logger.log("Kan centrale bank niet registreren");
        }
    }

    /**
     * Queue die periodiek de transacties verwerkt (i.e. op basis van de
     * TransactieStatus een transactie terugstuurt of doorstuurt).
     */
    private void setScheduler() {
        TimerTask batch = new TimerTask() {
            @Override
            public void run() {
                Collection<Transactie> transacties = database.getTransacties();
                for (Transactie t : transacties) {

                    if (t.getStatus() == Transactie.Status.FORWARDED) {

                        // doorsturen 
                        try {
                            String bankCode = t.getRekeningNaar().substring(0, 4);
                            IBankNaarCentrale bank = banken.get(bankCode);
                            bank.verwerk(t);
                            database.deleteTransactie(t);
                            logger.log("Uit: " + t);
                        } catch (NullPointerException | RemoteException ex) {
                        }

                    } else if (t.getStatus() == Transactie.Status.CANCELLED
                            || t.getStatus() == Transactie.Status.ACCEPTED) {

                        // terugsturen
                        try {
                            String bankCode = t.getRekeningVan().substring(0, 4);
                            IBankNaarCentrale bank = banken.get(bankCode);
                            bank.verwerk(t);
                            database.deleteTransactie(t);
                            logger.log("Uit: " + t);
                        } catch (NullPointerException | RemoteException ex) {
                        }

                    } else {

                        // Status = PENDING of DONE. Mag niet gebeuren. Pak slaag voor de bank!
                    }
                }
                setScheduler();
            }
        };
        scheduler.schedule(batch, 1 * 10 * 917);
    }

    @Override
    public void registreerBank(String bankCode, IBankNaarCentrale bank) throws RemoteException {

        if (!database.bankHeeftAutorisatie(bankCode)) {
            logger.log("Geen autorisatie: " + bankCode);
            throw new RemoteException("Geen autorisatie");
        }

        if (!banken.containsKey(bankCode)) {
            banken.put(bankCode, bank);
            logger.log("Bank " + bankCode + " heeft zich geregistreerd");
        }
    }

    @Override
    public void deregistreerBank(String bankCode, IBankNaarCentrale bank) throws RemoteException {
        if (bank.equals(banken.get(bankCode))) {
            banken.remove(bankCode);
            logger.log("Bank " + bankCode + " heeft zich afgemeld");
        }
    }

    @Override
    public void verwerk(Transactie t) throws RemoteException {

        logger.log("In:  " + t);

        String bankCode = t.getRekeningNaar().substring(0, 4).toUpperCase();
        if (!database.bankHeeftAutorisatie(bankCode)) {
            t.setStatus(Transactie.Status.CANCELLED);
        }

        database.insertTransactie(t);

    }

    public static void main(String[] args) throws RemoteException {
        new CentraleBank("Centrale Bank", "C*B");
    }
}
