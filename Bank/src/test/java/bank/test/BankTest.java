/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.test;

import bank.Bank;
import bank.Klant;
import bank.Rekening;
import bank.category.BankCat;
import bank.category.BankNaarBalieCat;
import bank.category.BankNaarCentraleCat;
import common.IBalieObserver;
import common.IBankNaarBalie;
import common.IBankNaarCentrale;
import common.ICentraleNaarBank;
import common.Transactie;
import database.IDatabaseConnectie;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.fail;
import org.junit.experimental.categories.Category;
import org.easymock.*;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.mock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Gijs
 */
public class BankTest {

    private IBankNaarBalie bankNaarBalie = null;
    private IBankNaarCentrale bankNaarCentrale = null;
    private Bank bank = null;
    private final ICentraleNaarBank centraleBank = mock(ICentraleNaarBank.class);
    private final IDatabaseConnectie database = mock(IDatabaseConnectie.class);
    private final IBalieObserver balie = mock(IBalieObserver.class);

    public BankTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        try {
            bank = new Bank("Rabobank", "RABO", 1000,
                    centraleBank, database, balie);
            bankNaarBalie = bank.getBankNaarBalie();
            bankNaarCentrale = bank.getBankNaarCentrale();
        } catch (RemoteException ex) {
            Logger.getLogger(BankTest.class.getName())
                    .log(Level.SEVERE, ex.getMessage(), ex);
            fail("Exception thrown in setup");
        }
    }

    @After
    public void tearDown() {
    }

    //SETSCHEDULER???
    
    /**
     * Test of registreer method, of class Bank
     *
     * <br>Check that registreer method registers IBankNaarBalie object on given
     * port.
     */
    @Test
    @Category(BankCat.class)
    public void testRegistreer() {
        try {
            int port = 9999;
            bank.registreer(port);
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", port);
            IBankNaarBalie bnb = (IBankNaarBalie) registry.lookup("BankNaarBalie");
            assertEquals(bankNaarBalie.hashCode(), bnb.hashCode());
        } catch (RemoteException ex) {
            fail("Remote exception: " + ex.getMessage());
        } catch (NotBoundException ex) {
            fail("BankNaarBalie not registered");
        }
    }

    /**
     * Test of registreerKlant method, of private class BankNaarBalie.
     *
     * <br>Check that a registered name/city will throw a RemoteException.
     * <br>Check that after succesfull registration the customernumber is
     * returned.
     */
    @Test
    @Category(BankNaarBalieCat.class)
    public void testRegistreerKlant() {
        //register customer
        try {
            expect(database.getKlant("henk", "venlo")).andReturn(null);
            expect(database.insertKlant("henk", "venlo"))
                    .andReturn(new Klant(1234, "henk", "venlo"));

            replay(database);
            bank.getBankNaarBalie().registreerKlant("henk", "venlo");
            verify(database);
            reset(database);
        } catch (RemoteException ex) {
            fail("Unexpected exception thrown when creating a new customer");
        }

        //register customer already known in the system
        try {
            EasyMock.expect(database.getKlant("henk", "venlo"))
                    .andReturn(new Klant(1234, "henk", "venlo"));
            replay(database);
            bankNaarBalie.registreerKlant("henk", "venlo");
            fail("Customer registered with an"
                    + " already existing name/city combination");
        } catch (RemoteException ex) {
            verify(database);
            assertEquals(
                    "Er is al een klant met deze naam en woonplaats geregistreerd",
                    ex.getMessage());
        }
    }

    /**
     * Test of openRekening method, of private class BankNaarBalie.
     *
     * <br>Check that an unknown customernumber will throw a RemoteException.
     * <br>Check that after succesfull registration the accountnumber is
     * returned.
     */
    @Test
    @Category(BankNaarBalieCat.class)
    public void testOpenRekening() {
        //register account correctly
        try {
            Klant klant = new Klant(1234, "henk", "venlo");
            expect(database.getKlant(1234))
                    .andReturn(klant);
            expect(database.insertRekening(klant))
                    .andReturn(new Rekening(10000, "RABO1234", klant.getKlantNummer()));
            replay(database);
            String rek = bankNaarBalie.openRekening(1234);
            assertEquals("RABO1234", rek);
            verify(database);
            reset(database);
        } catch (RemoteException ex) {
            fail("Unexpected exception thrown when creating a new account");
        }

        //register account with invalid customer number
        try {
            expect(database.getKlant(9999)).andReturn(null);
            replay(database);
            bankNaarBalie.openRekening(9999);
            fail("Account opened with an invalid customer number");
        } catch (RemoteException ex) {
            verify(database);
            assertEquals("Klant is onbekend", ex.getMessage());
        }
    }

    /**
     * Test of getSaldi method, of private class BankNaarBalie.
     *
     * <br>Check that an unknown customernumber will throw a RemoteException.
     * <br>Check that a valid customernumber will return a list of all accounts
     * and corresponding balance.
     */
    @Test
    @Category(BankNaarBalieCat.class)
    public void testGetSaldi() {
        //get balance correctly
        try {
            expect(database.getKlant(1234))
                    .andReturn(new Klant(1234, "henk", "venlo"));
            expect(database.getRekeningen(1234))
                    .andReturn(new ArrayList<>(Arrays.asList(
                            new Rekening(100, "RABO1", 1234),
                            new Rekening(100, "RABO2", 1234))));
            replay(database);
            Map<String, Integer> saldi = bankNaarBalie.getSaldi(1234);
            Assert.assertEquals(0, (int) saldi.get("RABO2"));
            verify(database);
            reset(database);
        } catch (RemoteException ex) {
            fail("Unexpected exception thrown when getting account balance");
        }

        //get balance with an invalid customer number
        try {
            expect(database.getKlant(1234)).andReturn(null);
            replay(database);
            bankNaarBalie.getSaldi(1234);
            fail("Received current balance with an invalid customer number");
        } catch (RemoteException ex) {
            verify(database);
            Assert.assertEquals("Klant is onbekend", ex.getMessage());
        }
    }

    /**
     * Test of getTransacties method, of private class BankNaarBalie.
     *
     * <br>Check that an unknown account number will throw a RemoteException.
     * <br>Check that an invalid customer / account combination will throw a
     * RemoteException.
     * <br>Check that a valid customer number / account number combination will
     * return a collection of all transactions on that account.
     */
    @Test
    @Category(BankNaarBalieCat.class)
    public void testGetTransacties() {
        //get transactions correctly
        try {
            Transactie t = new Transactie("1", new Date(), "RABO1", "INGB1", "test", 100);
            expect(database.getRekening("RABO1"))
                    .andReturn(new Rekening(10000, "RABO1", 1234));
            expect(database.getTransacties("RABO1"))
                    .andReturn(new ArrayList<>(Arrays.asList(t)));
            replay(database);
            Collection transacties = bankNaarBalie.getTransacties(1234, "RABO1");
            assertEquals(t, transacties.iterator().next());
            verify(database);
            reset(database);
        } catch (RemoteException | NoSuchElementException ex) {
            fail("Unexpected exception thrown when getting transactions");
        }

        //get transactions with an invalid accountnumber
        try {
            expect(database.getRekening("RABO1"))
                    .andReturn(null);
            replay(database);
            bankNaarBalie.getTransacties(1234, "RABO1");
            fail("transactions received with an invalid account number");
        } catch (RemoteException ex) {
            verify(database);
            reset(database);
            Assert.assertEquals("Rekening is onbekend", ex.getMessage());
        }

        //get transactions with an invalid customer/acount combination
        try {
            expect(database.getRekening("RABO1"))
                    .andReturn(new Rekening(100, "RABO1", 9999));
            replay(database);
            bankNaarBalie.getTransacties(1234, "RABO1");
            fail("transactions received with an invalid customer number");
        } catch (RemoteException ex) {
            verify(database);
            Assert.assertEquals("Rekening is onbekend", ex.getMessage());
        }
    }

    /**
     * Test of doeTransactie method, of private class BankNaarBalie.
     *
     * <br>Check that an unknown customer number will throw a RemoteException.
     * <br>Check that an unknown sending account number will throw a
     * RemoteException.
     * <br>Check that an invalid customer/sending account combination will throw
     * a RemoteException.
     * <br>Check that an invalid amount will throw a RemoteException.
     * <br>Check that a transaction from/to the same account will throw a
     * RemoteException.
     * <br>Check that an unknown receiving account from this bank will throw a
     * RemoteException.
     * <br>Check that an insufficient balance will throw a RemoteException.
     * <br>Check that a valid customer number / account number /amount
     * combination creates a transaction.
     */
    @Test
    @Category(BankNaarBalieCat.class)
    public void testDoeTransactie() {

        //create transaction with an unknown customer number or combination
        try {
            expect(database.getRekening("RABO1")).andReturn(new Rekening(100, "RABO1", 1234));
            expect(database.getRekening("INGB1")).andReturn(null);
            replay(database);
            bankNaarBalie.doeTransactie(9999, "RABO1", "INGB1", 100, "test");
            fail("transaction created with an invalid customer number");
        } catch (RemoteException ex) {
            verify(database);
            reset(database);
            Assert.assertEquals("Rekeningnummer is onbekend", ex.getMessage());
        }

        //create transaction with an unknown sending account number
        try {
            expect(database.getRekening("GEENREK")).andReturn(null);
            expect(database.getRekening("INGB1")).andReturn(null);
            replay(database);
            bankNaarBalie.doeTransactie(1234, "GEENREK", "INGB1", 100, "test");
            fail("transaction created with an invalid account number");
        } catch (RemoteException ex) {
            verify(database);
            reset(database);
            Assert.assertEquals("Rekeningnummer is onbekend", ex.getMessage());
        }

        //create transaction with an invalid (negative) amount
        try {
            expect(database.getRekening("RABO1")).andReturn(new Rekening(100, "RABO1", 1234));
            expect(database.getRekening("INGB1")).andReturn(null);
            replay(database);
            bankNaarBalie.doeTransactie(1234, "RABO1", "INGB1", -100, "test");
            fail("transaction created with an invalid (negative) amount");
        } catch (RemoteException ex) {
            verify(database);
            reset(database);
            Assert.assertEquals("Bedrag wordt niet geaccepteerd", ex.getMessage());
        }

        //create transaction with an invalid (to high) amount
        try {
            expect(database.getRekening("RABO1")).andReturn(new Rekening(100, "RABO1", 1234));
            expect(database.getRekening("INGB1")).andReturn(null);
            replay(database);
            bankNaarBalie.doeTransactie(1234, "RABO1", "INGB1", 10000001, "test");
            fail("transaction created with an invalid (to high) amount");
        } catch (RemoteException ex) {
            verify(database);
            reset(database);
            Assert.assertEquals("Bedrag wordt niet geaccepteerd", ex.getMessage());
        }

        //create transaction from/to the same account
        try {
            expect(database.getRekening("RABO1"))
                    .andReturn(new Rekening(100, "RABO1", 1234)).times(2);
            replay(database);
            bankNaarBalie.doeTransactie(1234, "RABO1", "RABO1", 100, "test");
            fail("transaction created with same from/to account");
        } catch (RemoteException ex) {
            verify(database);
            reset(database);
            Assert.assertEquals("U kunt niet overboeken naar uw eigen rekening", ex.getMessage());
        }

        //create transaction with unknown receiving account from this bank
        try {
            expect(database.getRekening("RABO1")).andReturn(new Rekening(100, "RABO1", 1234));
            expect(database.getRekening("RABO2")).andReturn(null);
            replay(database);
            bankNaarBalie.doeTransactie(1234, "RABO1", "RABO2", 100, "test");
            fail("transaction created with invalid receiving account from this bank");
        } catch (RemoteException ex) {
            verify(database);
            reset(database);
            Assert.assertEquals("De tegenrekening is binnen onze bank niet bekend", ex.getMessage());
        }

        //create transaction with insufficient balance
        try {
            expect(database.getRekening("RABO1")).andReturn(new Rekening(100, "RABO1", 1234));
            expect(database.getRekening("INGB1")).andReturn(null);
            replay(database);
            bankNaarBalie.doeTransactie(1234, "RABO1", "INGB1", 101, "test");
            fail("transaction created with insufficient balance");
        } catch (RemoteException ex) {
            verify(database);
            reset(database);
            Assert.assertEquals("Saldo niet toereikend", ex.getMessage());
        }

        //create valid transaction to internal account
        try {
            Rekening rekVan = new Rekening(100, "RABO1", 1234);
            Rekening rekNaar = new Rekening(100, "RABO2", 4321);
            expect(database.getRekening("RABO1")).andReturn(rekVan);
            expect(database.getRekening("RABO2")).andReturn(rekNaar);
            database.updateRekening(rekVan);
            expectLastCall();
            database.updateRekening(rekNaar);
            expectLastCall();
            Transactie t = new Transactie("1", new Date(), "RABO1", "RABO2", "test", 100);
            expect(database.getTransactie("RABO1", "RABO2", 100, "test", Transactie.Status.DONE))
                    .andReturn(t);
            database.insertTransactie(t);
            expectLastCall();
            balie.updateView(rekVan.getKlantNr(), t);
            expectLastCall();
            balie.updateView(rekNaar.getKlantNr(), t);
            expectLastCall();
            replay(database);
            replay(balie);
            assertTrue(bankNaarBalie.doeTransactie(1234, "RABO1", "RABO2", 100, "test"));
            verify(database);
            reset(database);
            verify(balie);
            reset(balie);
        } catch (RemoteException ex) {
            fail("Transaction should be valid");
        }

        //create valid transaction to external account
        try {
            Rekening rekVan = new Rekening(100, "RABO1", 1234);
            expect(database.getRekening("RABO1")).andReturn(rekVan);
            expect(database.getRekening("INGB1")).andReturn(null);
            database.updateRekening(rekVan);
            expectLastCall();
            Transactie t = new Transactie("1", new Date(), "RABO1", "RABO2", "test", 100);
            expect(database.getTransactie("RABO1", "INGB1", 100, "test", Transactie.Status.PENDING))
                    .andReturn(t);
            database.insertTransactie(t);
            expectLastCall();
            balie.updateView(rekVan.getKlantNr(), t);
            expectLastCall();
            replay(database);
            replay(balie);
            assertTrue(bankNaarBalie.doeTransactie(1234, "RABO1", "INGB1", 100, "test"));
            verify(database);
            verify(balie);
        } catch (RemoteException ex) {
            fail("Transaction should be valid");
        }
    }

    /**
     * Test of verwerk method, of private class BankNaarCentrale.
     *
     * <br>Check that a transaction will be inserted or updated in database.
     */
    @Test
    @Category(BankNaarCentraleCat.class)
    public void testVerwerk() {
        
        //receive already present transaction
        try{
            Transactie t = new Transactie("1",new Date(),"RABO1","INGB1","",100);
            expect(database.getTransactie(t.getTransactieNr())).andReturn(t);
            database.updateTransactie(t);
            expectLastCall();
            replay(database);
            bankNaarCentrale.verwerk(t);
            verify(database);
            reset(database);
        } catch(RemoteException ex){
            fail("Unexpected exception thrown when receiving a transaction");
        }
        
        //receive new transaction
        try{
            Transactie t = new Transactie("1",new Date(),"RABO1","INGB1","",100);
            expect(database.getTransactie(t.getTransactieNr())).andReturn(null);
            database.insertTransactie(t);
            expectLastCall();
            replay(database);
            bankNaarCentrale.verwerk(t);
            verify(database);
            reset(database);
        } catch(RemoteException ex){
            fail("Unexpected exception thrown when receiving a transaction");
        }
        
    }
}
