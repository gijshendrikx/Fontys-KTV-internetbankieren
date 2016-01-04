/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.test;

import bank.Bank;
import bank.category.BankCat;
import bank.category.BankNaarBalieCat;
import common.IBankNaarBalie;
import common.IBankNaarCentrale;
import common.ICentraleNaarBank;
import common.Transactie;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.fail;
import org.junit.experimental.categories.Category;
import org.easymock.*;
import static org.easymock.EasyMock.mock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

/**
 *
 * @author Gijs
 */
public class BankTest {

    private IBankNaarBalie bankNaarBalie = null;
    private IBankNaarCentrale bankNaarCentrale = null;
    private Bank bank = null;

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
            bank = new Bank("Rabobank", "Rabo", 1000);
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

    
    @Rule
    public EasyMockRule rule = new EasyMockRule(this);

    @Mock
    private final ICentraleNaarBank centraleBank = mock(ICentraleNaarBank.class); // 1
    
    @Test
    public void testSetScheduler() {
        try{
            bank.setCentraleBank(centraleBank);
            Transactie t = new Transactie("1",new Date(),"INGB00000001","ABNA1234","test",100);
            
            
            centraleBank.verwerk(t); //3
            replay(centraleBank); //4
            int klantnr = bankNaarBalie.registreerKlant("Gijs", "Tegelen");
            String rek = bankNaarBalie.openRekening(klantnr);
            bankNaarBalie.doeTransactie(klantnr, rek, "TEST123", 100, "test");
            Thread.sleep(20000);
            verify(centraleBank);
            
//        classUnderTest.addDocument("New Document", "content"); // 5
//        verifyAll(); // 6
        } catch (RemoteException | InterruptedException ex){
            fail("Unexpected exception while testing scheduler");
        }
    }

    /**
     * Registreert het IBankNaarBalie object op een aangegeven poort.
     * 
     * <br>Pre: poort is nog niet in gebruik.
     * <br>Post: IBankNaarBalie is op poort geregistreerd.
     * 
     * @param port poort waarop IBankNaarBalie object op geregistreerd wordt
     */
    //private void registreer(int port) {
    
    /**
     * Test of registreer method, of class Bank
     *
     * <br>Check that creation of bank calls registreer method and 
     * registers IBankNaarBalie object on given port.
     */
    @Test
    @Category(BankCat.class)
    public void testRegistreer(){
        int port = 9999;
        try{
            Bank bankABN = new Bank("ABN Amro","ABNA",port);
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", port);
            IBankNaarBalie bnb = (IBankNaarBalie) registry.lookup("BankNaarBalie");
            Assert.assertEquals(bankABN.getBankNaarBalie(), bnb);
        } catch (RemoteException | NotBoundException ex){
            fail("IBankNaarBalie not registered");
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
            bankNaarBalie.registreerKlant("henk", "belfeld");
        } catch (RemoteException ex) {
            fail("Unexpected exception thrown when creating a new customer");
        }

        //register customer already known in the system
        try {
            bankNaarBalie.registreerKlant("henk", "belfeld");
            fail("Customer registered with an"
                    + " already existing name/city combination");
        } catch (RemoteException ex) {
            Assert.assertEquals(
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
            int klantnr = bankNaarBalie.registreerKlant("henk", "belfeld");
            bankNaarBalie.openRekening(klantnr);
        } catch (RemoteException ex) {
            fail("Unexpected exception thrown when creating a new account");
        }

        //register account with invalid customer number
        try {
            bankNaarBalie.openRekening(Integer.MAX_VALUE);
            fail("Account opened with an invalid customer number");
        } catch (RemoteException ex) {
            Assert.assertEquals("Klant is onbekend", ex.getMessage());
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
            int klantnr = bankNaarBalie.registreerKlant("henk", "belfeld");
            String rekening = bankNaarBalie.openRekening(klantnr);
            Map<String, Integer> saldi = bankNaarBalie.getSaldi(klantnr);
            Assert.assertEquals(0, (int) saldi.get(rekening));
        } catch (RemoteException ex) {
            fail("Unexpected exception thrown when getting account balance");
        }

        //get balance with an invalid customer number
        try {
            Map<String, Integer> saldi = bankNaarBalie.getSaldi(Integer.MAX_VALUE);
            fail("Received current balance with an invalid customer number");
        } catch (RemoteException ex) {
            Assert.assertEquals("Klant is onbekend", ex.getMessage());
        }
    }

    /**
     * Test of getTransacties method, of private class BankNaarBalie.
     *
     * <br>Check that an unknown customer number will throw a RemoteException.
     * <br>Check that an unknown account number will throw a RemoteException.
     * <br>Check that a valid customer number / account number combination will
     * return a collection of all transactions on that account.
     */
    @Test
    @Category(BankNaarBalieCat.class)
    public void testGetTransacties() {
        //get transactions correctly
        try {
            int klantnr = bankNaarBalie.registreerKlant("henk", "belfeld");
            String rekening = bankNaarBalie.openRekening(klantnr);
            Collection<Transactie> transacties
                    = bankNaarBalie.getTransacties(klantnr, rekening);
        } catch (RemoteException ex) {
            fail("Unexpected exception thrown when getting transactions");
        }

        //get transactions with an invalid customernumber
        try {
            int klantnr = bankNaarBalie.registreerKlant("bert", "belfeld");
            String rekening = bankNaarBalie.openRekening(klantnr);
            Collection<Transactie> transacties
                    = bankNaarBalie.getTransacties(Integer.MAX_VALUE, rekening);
            fail("transactions received with an invalid customer number");
        } catch (RemoteException ex) {
            Assert.assertEquals("Rekening is onbekend", ex.getMessage());
        }

        //get transactions with an invalid accountnumber
        try {
            int klantnr = bankNaarBalie.registreerKlant("wil", "belfeld");
            Collection<Transactie> transacties
                    = bankNaarBalie.getTransacties(klantnr, "abc");
            fail("transactions received with an invalid account number");
        } catch (RemoteException ex) {
            Assert.assertEquals("Rekening is onbekend", ex.getMessage());
        }

        //get transactions with an invalid account / customer combination
        try {
            int klantnr1 = bankNaarBalie.registreerKlant("jochem", "belfeld");
            int klantnr2 = bankNaarBalie.registreerKlant("hans", "belfeld");
            String rekening = bankNaarBalie.openRekening(klantnr1);
            Collection<Transactie> transacties
                    = bankNaarBalie.getTransacties(klantnr2, rekening);
            fail("transactions received with an invalid account / customer combination");
        } catch (RemoteException ex) {
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

        //setup
        int klantnr1 = 0;
        int klantnr2 = 0;
        String rekeningVan1 = null;
        String rekeningVan2 = null;
        try {
            klantnr1 = bankNaarBalie.registreerKlant("Bertus", "Weert");
            klantnr2 = bankNaarBalie.registreerKlant("Bertus", "Venlo");
            rekeningVan1 = bankNaarBalie.openRekening(klantnr1);
            rekeningVan2 = bankNaarBalie.openRekening(klantnr2);
        } catch (RemoteException ex) {
            fail("Initialization of test failed");
        }

        //create transaction with an unknown customer number
        try {
            bankNaarBalie.doeTransactie(Integer.MAX_VALUE, rekeningVan1, "INGB1234", 100, "test");
            fail("transaction created with an invalid customer number");
        } catch (RemoteException ex) {
            Assert.assertEquals("Rekeningnummer is onbekend", ex.getMessage());
        }

        //create transaction with an unknown sending account number
        try {
            bankNaarBalie.doeTransactie(klantnr1, "DITISONGELDIG", "INGB1234", 100, "test");
            fail("transaction created with an invalid account number");
        } catch (RemoteException ex) {
            Assert.assertEquals("Rekeningnummer is onbekend", ex.getMessage());
        }

        //create transaction with an invalid customer/account combination
        try {
            bankNaarBalie.doeTransactie(klantnr1, rekeningVan2, "INGB1234", 100, "test");
            fail("transaction created with an invalid customer/account combination");
        } catch (RemoteException ex) {
            Assert.assertEquals("Rekeningnummer is onbekend", ex.getMessage());
        }

        //create transaction with an invalid (negative) amount
        try {
            bankNaarBalie.doeTransactie(klantnr1, rekeningVan1, "INGB1234", -100, "test");
            fail("transaction created with an invalid (negative) amount");
        } catch (RemoteException ex) {
            Assert.assertEquals("Bedrag wordt niet geaccepteerd", ex.getMessage());
        }
        
        //create transaction with an invalid (to high) amount
        try {
            bankNaarBalie.doeTransactie(klantnr1, rekeningVan1, "INGB1234", 10000001, "test");
            fail("transaction created with an invalid (to high) amount");
        } catch (RemoteException ex) {
            Assert.assertEquals("Bedrag wordt niet geaccepteerd", ex.getMessage());
        }
        
        //create transaction from/to the same account
        try {
            bankNaarBalie.doeTransactie(klantnr1, rekeningVan1, rekeningVan1, 100, "test");
            fail("transaction created with same from/to account");
        } catch (RemoteException ex) {
            Assert.assertEquals("U kunt niet overboeken naar uw eigen rekening", ex.getMessage());
        }

        //create transaction with unknown receiving account from this bank
        try {
            bankNaarBalie.doeTransactie(klantnr1, rekeningVan1, "Rabo1234", 100, "test");
            fail("transaction created with invalid receiving account from this bank");
        } catch (RemoteException ex) {
            Assert.assertEquals("De tegenrekening is binnen onze bank niet bekend", ex.getMessage());
        }

        //create transaction with insufficient balance (10000 is start balance)
        try {
            bankNaarBalie.doeTransactie(klantnr1, rekeningVan1, "INGB1234", 10001, "test");
            fail("transaction created with insufficient balance");
        } catch (RemoteException ex) {
            Assert.assertEquals("Saldo niet toereikend", ex.getMessage());
        }

        //create valid transaction
        try {
            Assert.assertTrue(bankNaarBalie.doeTransactie(
                    klantnr2, rekeningVan2, "INGB1234", 10000, "test"));
        } catch (RemoteException ex) {
            fail("Transaction should be valid");
        }
    }


    
    
//    
//    
//    //BANKNAARCENTRALe
//        /**
//     * Deze methode verzoekt om verwerking van transactie t van bank A naar bank
//     * B
//     *
//     * @param t de transactie
//     * @throws java.rmi.RemoteException
//     */
//    void verwerk(Transactie t) throws RemoteException;
//    
    /**
     * Registreert het IBankNaarBalie object op een aangegeven poort.
     *
     * <br>Pre: poort is nog niet in gebruik.
     * <br>Post: IBankNaarBalie is op poort geregistreerd.
     *
     * @param port poort waarop IBankNaarBalie object op geregistreerd wordt
     */
//    private void registreer(int port) {
//
    //create mock
//        ICentraleNaarBank centraleBank = createNiceMock(ICentraleNaarBank.class);
//        
//        // setup the mock object
//        expect(centraleBank)
//expect(calcMethod.calc(Position.BOSS)).andReturn(70000.0).times(2);
//    }
}
