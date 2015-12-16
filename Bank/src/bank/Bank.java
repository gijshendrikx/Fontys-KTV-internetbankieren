package bank;
 
import common.IBalieObserver;
import common.IBankNaarBalie;
import common.IBankNaarCentrale;
import common.ICentraleNaarBank;
import common.Transactie;
import database.DatabaseConnectieMock;
import database.IDatabaseConnectie;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bank {

    private final String naam;
    private final String bankCode;
    private final IDatabaseConnectie database;
    private final LogWindow logger;
    private Timer scheduler;

    // biedt aan
    private final IBankNaarCentrale bankNaarCentrale;
    private final IBankNaarBalie bankNaarBalie;

    // heeft nodig
    private ICentraleNaarBank centraleNaarBank;
    private IBalieObserver balieObserver;

    public Bank(String n, String b, int port) throws RemoteException {
        naam = n;
        bankCode = b;
        database = new DatabaseConnectieMock(bankCode);
        bankNaarCentrale = new BankNaarCentrale();
        bankNaarBalie = new BankNaarBalie();
        logger = new LogWindow(naam, bankCode);
        connectCentraleBank(10000);
        registreer(port);
    }

    private void connectCentraleBank(int reconnectMillis) {
        new Thread(() -> {
            while (true) {
                if (centraleNaarBank == null) {
                    try {
                        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1100);
                        centraleNaarBank = (ICentraleNaarBank) registry.lookup("CentraleNaarBank");
                        centraleNaarBank.registreerBank(bankCode, bankNaarCentrale);
                        logger.log("Centrale bank gevonden");
                        logger.exitOnClose(this);
                        setScheduler(centraleNaarBank);
                    } catch (RemoteException | NotBoundException ex) {
                        centraleNaarBank = null;
                        logger.log("Kan centrale bank niet vinden, kan niet overboeken naar andere bank");
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
            registry.rebind("BankNaarBalie", bankNaarBalie);
            logger.log("Bank is geregistreerd");
        } catch (RemoteException ex) {
            logger.log("Kan bank niet registreren");
        }
    }

    void exitOnClose() {
        try {
            centraleNaarBank.deregistreerBank(bankCode, bankNaarCentrale);
            System.exit(0);
        } catch (RemoteException ex) {
            logger.log("Kan niet deregistreren");
        }
    }

    /*
    
     /**
     * Queue die periodiek de openstaande transacties verwerkt
     *
     * @param centraleNaarBank
     */
    void setScheduler(ICentraleNaarBank centraleNaarBank) {
        if (scheduler != null) {
            scheduler.cancel();
        }
        scheduler = new Timer();

        TimerTask batch = new TimerTask() {
            @Override
            public void run() {
                Collection<Transactie> transacties = database.getOpenTransacties();
                for (Transactie t : transacties) {
                    Transactie.Status status = t.getStatus();
                    logger.log("Verwerken: " + t);

                    if (status == Transactie.Status.PENDING) {
                        // transactie gaat naar buiten:
                        // update transactie, verstuur naar centrale bank

                        try {
                            t.setStatus(Transactie.Status.FORWARDED);
                            centraleNaarBank.verwerk(t);
                            database.updateTransactie(t);
                        } catch (RemoteException ex) {
                            // volgende ronde beter
                        }

                    } else if (status == Transactie.Status.ACCEPTED) {
                        // bijboeking tegenrekening gelukt:
                        // voltooi transactie, update view        

                        t.setStatus(Transactie.Status.DONE);
                        database.updateTransactie(t);
                        Rekening r = database.getRekening(t.getRekeningVan());
                        try {
                            balieObserver.updateView(r.getKlantNr(), t);
                        } catch (NullPointerException | RemoteException ex) {
                            // balie of rekeninghouder is niet online
                        }

                    } else if (status == Transactie.Status.CANCELLED) {
                        // bijboeking tegenrekening mislukt:
                        // voltooi transactie, maak afboeking ongedaan met tegentransactie, update rekening, update view

                        t.setStatus(Transactie.Status.DONE);
                        database.updateTransactie(t);
                        Transactie tt = database.getTransactie(t.getRekeningNaar(), t.getRekeningVan(), t.getBedrag(),
                                "Tegenboeking " + t.getTransactieNr(), Transactie.Status.DONE);
                        database.insertTransactie(tt);
                        Rekening rekVan = database.getRekening(t.getRekeningVan());
                        rekVan.boekBij(t.getBedrag());
                        database.updateRekening(rekVan);
                        try {
                            balieObserver.updateView(rekVan.getKlantNr(), t);
                        } catch (NullPointerException | RemoteException ex) {
                            // balie of rekeninghouder is niet online
                        }

                    } else if (status == Transactie.Status.FORWARDED) {
                        Rekening rekNaar = database.getRekening(t.getRekeningNaar());

                        if (rekNaar == null) {
                            // rekeningnummer onbekend:
                            // cancel transactie, stuur terug naar centrale bank

                            try {
                                t.setStatus(Transactie.Status.CANCELLED);
                                centraleNaarBank.verwerk(t);
                            } catch (RemoteException ex) {
                                // volgende ronde beter
                            }

                        } else {
                            // rekeningnummer bekend:
                            // accepteer transactie, stuur terug naar centrale bank, update rekening, voltooi transactie, update view

                            try {
                                t.setStatus(Transactie.Status.ACCEPTED);
                                centraleNaarBank.verwerk(t);
                                rekNaar.boekBij(t.getBedrag());
                                database.updateRekening(rekNaar);
                                t.setStatus(Transactie.Status.DONE);
                                database.updateTransactie(t);
                                try {
                                    balieObserver.updateView(rekNaar.getKlantNr(), t);
                                } catch (NullPointerException | RemoteException ex) {
                                    // balie of rekeninghouder is niet online
                                }
                            } catch (RemoteException ex) {
                                // volgende ronde beter
                            }
                        }

                    } else {
                        // Dit mag niet gebeuren. Pak slaag voor de centrale bank!
                    }

                }
                setScheduler(centraleNaarBank);
            }
        };

        scheduler.schedule(batch, 1 * 10 * 1000);

    }

    private class BankNaarCentrale extends UnicastRemoteObject implements IBankNaarCentrale {

        public BankNaarCentrale() throws RemoteException {
        }

        @Override
        public void verwerk(Transactie t) throws RemoteException {
            logger.log("CentraleBank: " + t);
            if (database.getTransactie(t.getTransactieNr()) == null) {
                database.insertTransactie(t);
            } else {
                database.updateTransactie(t);
            }
        }
    }

    private class BankNaarBalie extends UnicastRemoteObject implements IBankNaarBalie {

        public BankNaarBalie() throws RemoteException {
        }

        @Override
        public int registreerKlant(String naam, String woonplaats) throws RemoteException {

            Klant geregistreerd = database.getKlant(naam, woonplaats);
            if (geregistreerd != null) {
                throw new RemoteException("Er is al een klant met deze naam en woonplaats geregistreerd");
            }

            Klant k = database.insertKlant(naam, woonplaats);
            logger.log("Klant geregistreerd: " + k);
            return k.getKlantNummer();
        }

        @Override
        public String openRekening(int klantnummer) throws RemoteException {

            Klant k = database.getKlant(klantnummer);
            if (k == null) {
                throw new RemoteException("Klant is onbekend");
            }

            Rekening r = database.insertRekening(k);
            logger.log("Rekening geopend: " + r);
            return r.getRekeningNr();

        }

        @Override
        public Map<String, Integer> getSaldi(int klantnummer) throws RemoteException {

            Klant k = database.getKlant(klantnummer);
            if (k == null) {
                throw new RemoteException("Klant is onbekend");
            }

            Collection<Rekening> rekeningen = database.getRekeningen(klantnummer);
            Map<String, Integer> map = new TreeMap<>();
            for (Rekening r : rekeningen) {
                map.put(r.getRekeningNr(), r.getSaldo());
            }

            return map;
        }

        @Override
        public Collection<Transactie> getTransacties(int klantnummer, String rekeningnummer) throws RemoteException {

            Rekening r = database.getRekening(rekeningnummer);
            if (r == null || r.getKlantNr() != klantnummer) {
                throw new RemoteException("Rekening is onbekend");
            }

            return database.getTransacties(rekeningnummer);
        }

        @Override
        public boolean doeTransactie(int klantnummer, String rekeningVan, String rekeningNaar, int bedrag, String omschr) throws RemoteException {

            Rekening van = database.getRekening(rekeningVan);
            Rekening naar = database.getRekening(rekeningNaar);

            if (van == null || van.getKlantNr() != klantnummer) {
                throw new RemoteException("Rekeningnummer is onbekend");
            }

            if (van.equals(naar)) {
                throw new RemoteException("U kunt niet overboeken naar uw eigen rekening");
            }

            if (naar == null && rekeningNaar.substring(0, 4).equals(bankCode)) {
                throw new RemoteException("De tegenrekening is binnen onze bank niet bekend");
            }

            if (bedrag <= 0 || bedrag > 10000000) {
                throw new RemoteException("Bedrag wordt niet geaccepteerd");
            }

            if (!van.kanAfboeken(bedrag)) {
                throw new RemoteException("Saldo niet toereikend");
            }

            if (naar != null) {

                // intern verwerken
                van.boekAf(bedrag);
                naar.boekBij(bedrag);
                database.updateRekening(van);
                database.updateRekening(naar);
                Transactie t = database.getTransactie(rekeningVan, rekeningNaar, bedrag, omschr, Transactie.Status.DONE);
                database.insertTransactie(t);
                logger.log("Transactie aangemaakt: " + t);
                if (balieObserver != null) {
                    balieObserver.updateView(van.getKlantNr(), t);
                    try {
                        balieObserver.updateView(naar.getKlantNr(), t);
                    } catch (RemoteException ex) {
                        // houder van rekeningNaar is niet online
                    }
                }

            } else {

                // via centrale bank
                van.boekAf(bedrag);
                database.updateRekening(van);
                Transactie t = database.getTransactie(rekeningVan, rekeningNaar, bedrag, omschr, Transactie.Status.PENDING);
                database.insertTransactie(t);
                logger.log("Transactie aangemaakt: " + t);
                if (balieObserver != null) {
                    balieObserver.updateView(van.getKlantNr(), t);
                }

            }

            return true;
        }

        @Override
        public void registreerBalie(String bCode, IBalieObserver balie) throws RemoteException {
            if (!bCode.equals(bankCode)) {
                throw new RemoteException("Geen autorisatie");
            }

            if (balieObserver == null) {
                balieObserver = balie;
                logger.log("Balie " + bCode + " heeft zich geregistreerd");
            }
        }

        @Override
        public void deregistreerBalie(String bankCode, IBalieObserver balie) throws RemoteException {
            if (balieObserver != null && balieObserver.equals(balie)) {
                balieObserver = null;
                logger.log("Balie " + bankCode + " heeft zich afgemeld");
            }
        }

    }

    public static void main(String[] args) {
        String naam = args.length > 0 ? args[0] : "ING Bank";
        String code = args.length > 1 ? args[1] : "INGB";
        int port = args.length > 2 ? Integer.valueOf(args[2]) : 1099;
        try {
            new Bank(naam, code, port);
        } catch (RemoteException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
