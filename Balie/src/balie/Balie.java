package balie;
 
import common.IBalieNaarGUI;
import common.IBalieObserver;
import common.IBankNaarBalie;
import common.IGUIObserver;
import common.Transactie;
import database.DatabaseConnectieMock;
import database.IDatabaseConnectie;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Balie {

    private final String naam;
    private final String bankCode;
    private final Map<String, Sessie> sessies;
    private final IDatabaseConnectie database;
    private final LogWindow logger;

    // biedt aan
    private final IBalieObserver balieObserver;
    private final IBalieNaarGUI balieNaarGUI;

    // heeft nodig 
    private IBankNaarBalie bank;

    public Balie(String n, String b, int port) throws RemoteException {
        naam = n;
        bankCode = b;
        sessies = Collections.synchronizedMap(new HashMap<>());
        database = new DatabaseConnectieMock();
        balieObserver = new BalieObserver();
        balieNaarGUI = new BalieNaarGUI();
        logger = new LogWindow(naam, bankCode);
        connectBank(port, 10000);
    }

    private void connectBank(int port, int reconnectMillis) {
        new Thread(() -> {
            while (true) {
                if (bank == null) {
                    try {
                        Registry registry = LocateRegistry.getRegistry("127.0.0.1", port + 1);
                        bank = (IBankNaarBalie) registry.lookup("BankNaarBalie");
                        bank.registreerBalie(bankCode, balieObserver);
                        logger.log("Bank gevonden");
                        logger.exitOnClose(this);
                        registreer(port);
                    } catch (RemoteException | NotBoundException ex) {
                        bank = null;
                        logger.log("Kan bank niet vinden :(");
                    }
                }
                try {
                    Thread.sleep(reconnectMillis);
                } catch (InterruptedException ex) {
                }
            }
        }).start();
    }

    private void registreer(int port) {
        try {
            Registry registry = java.rmi.registry.LocateRegistry.createRegistry(port);
            registry.rebind("BalieNaarGUI", balieNaarGUI);
            logger.log("Balie is geregistreerd");
        } catch (RemoteException ex) {
            logger.log("Kan balie niet registreren");
        }
    }

    void exitOnClose() {
        try {
            bank.deregistreerBalie(bankCode, balieObserver);
            System.exit(0);
        } catch (RemoteException ex) {
            logger.log("Kan niet deregistreren");
        }
    }

    void removeVerlopenSessie(String sessieId) {
        sessies.remove(sessieId);
        logger.log("Sessie " + sessieId + " is verlopen");
    }

    private class BalieObserver extends UnicastRemoteObject implements IBalieObserver {

        Random r = new Random();

        public BalieObserver() throws RemoteException {
        }

        @Override
        public void updateView(int klantnummer, Transactie t) throws RemoteException {
            for (Sessie s : sessies.values()) {
                if (s.getKlantNummer() == klantnummer) {
                    IGUIObserver gui = s.getGUI();
                    if (gui != null) {
                        gui.updateView(t);
                    }
                }
            }
        }
    }

    private class BalieNaarGUI extends UnicastRemoteObject implements IBalieNaarGUI {

        public BalieNaarGUI() throws RemoteException {
        }

        @Override
        public String registreren(String naam, String woonplaats, String wachtwoord) throws RemoteException {

            // check wachtwoord geldig
            if (wachtwoord == null || wachtwoord.length() < 8) {
                throw new RemoteException("Wachtwoord moet minimaal 8 karakters bevatten");
            }
            boolean bevatHoofdletter = false;
            boolean bevatKleineletter = false;
            boolean bevatCijfer = false;
            for (char ch : wachtwoord.toCharArray()) {
                bevatHoofdletter = bevatHoofdletter || (ch >= 'A' && ch <= 'Z');
                bevatKleineletter = bevatKleineletter || (ch >= 'a' && ch <= 'z');
                bevatCijfer = bevatCijfer || (ch >= '0' && ch <= '9');
            }
            if (!(bevatHoofdletter && bevatKleineletter && bevatCijfer)) {
                throw new RemoteException("Wachtwoord moet minimaal 1 hoofdletter, 1 kleine letter en 1 cijfer bevatten");
            }

            // check wachtwoord uniek
            String login = naam + woonplaats;
            if (database.getUser(login) != null) {
                throw new RemoteException("Inlognaam is al in gebruik");
            }

            // meld aan bij bank
            int klantNummer = bank.registreerKlant(naam, woonplaats);

            // sla klant op
            database.insertUser(new User(login, wachtwoord, klantNummer));

            // return 
            logger.log("Registratie nieuwe klant " + login);
            return login;
        }

        @Override
        public String inloggen(String login, String wachtwoord) throws RemoteException {

            User k = database.getUser(login);
            if (k == null || !k.getWachtwoord().equals(wachtwoord)) {
                throw new RemoteException("Inloggegevens niet geldig");
            }

            Sessie sessie = new Sessie(k.getKlantnr(), Balie.this);
            sessies.put(sessie.getSessieId(), sessie);
            logger.log(login + " heeft ingelogd");

            return sessie.getSessieId();
        }

        @Override
        public String rekeningOpenen(String sessieid) throws RemoteException {

            Sessie sessie = sessies.get(sessieid);
            if (sessie == null) {
                throw new RemoteException("Sessie is verlopen");
            }

            int klantnummer = sessie.getKlantNummer();
            logger.log("Sessie " + sessieid + " heeft nieuwe rekening geopend");
            return bank.openRekening(klantnummer);
        }

        @Override
        public Map<String, Integer> saldiInzien(String sessieid) throws RemoteException {

            Sessie sessie = sessies.get(sessieid);
            if (sessie == null) {
                throw new RemoteException("Sessie is verlopen");
            }

            int klantnummer = sessie.getKlantNummer();
            return bank.getSaldi(klantnummer);
        }

        @Override
        public Collection<Transactie> transactiesInzien(String sessieid, String rekeningnummer) throws RemoteException {

            Sessie sessie = sessies.get(sessieid);
            if (sessie == null) {
                throw new RemoteException("Sessie is verlopen");
            }

            int klantnummer = sessie.getKlantNummer();
            return bank.getTransacties(klantnummer, rekeningnummer);
        }

        @Override
        public boolean overboekenNaar(String sessieid, String rekeningVan, String rekeningNaar, int bedrag, String omschrijving) throws RemoteException {

            Sessie sessie = sessies.get(sessieid);
            if (sessie == null) {
                throw new RemoteException("Sessie is verlopen");
            }

            int klantnummer = sessie.getKlantNummer();
            return bank.doeTransactie(klantnummer, rekeningVan, rekeningNaar, bedrag, omschrijving);
        }

        @Override
        public boolean uitloggen(String sessieid) throws RemoteException {
            sessies.remove(sessieid);
            logger.log("Sessie " + sessieid + " is uitgelogd");
            return true;
        }

        @Override
        public void registreerGUI(String sessieid, IGUIObserver gui) throws RemoteException {

            Sessie sessie = sessies.get(sessieid);
            if (sessie == null) {
                throw new RemoteException("Sessie is verlopen");
            }

            sessie.setGUI(gui);
            logger.log("Sessie " + sessieid + " heeft GUI geregistreerd");
        }
    }

    public static void main(String[] args) {
        String naam = args.length > 0 ? args[0] : "ING Bank";
        String code = args.length > 1 ? args[1] : "INGB";
        int port = args.length > 2 ? Integer.valueOf(args[2]) : 1098;
        try {
            new Balie(naam, code, port);
        } catch (RemoteException ex) {
            Logger.getLogger(Balie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
