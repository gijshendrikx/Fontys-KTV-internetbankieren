/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.test.integration;

import bank.Bank;
import bank.category.IntegrationCat;
import common.Transactie;
import common.Transactie.Status;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;

/**
 *
 * @author Gijs
 */
public class IntegrationTest {

    private Bank bankRabo = null;
    private Bank bankING = null;
    private int klantRabo;
    private int klantING;
    private String rekRabo = null;
    private String rekING = null;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        try {
            bankRabo = new Bank("Rabobank", "RABO", 2001);
            klantRabo = bankRabo.getBankNaarBalie().registreerKlant("henk", "venlo");
            rekRabo = bankRabo.getBankNaarBalie().openRekening(klantRabo);

            bankING = new Bank("ING Bank", "INGB", 2002);
            klantING = bankING.getBankNaarBalie().registreerKlant("gert", "eindhoven");
            rekING = bankING.getBankNaarBalie().openRekening(klantRabo);
        } catch (RemoteException ex) {
            fail("Unexpected Remote Exception: " + ex.getMessage());
        }
    }

    /**
     * Test of transcations flow.
     *
     * <br>Check that a transcation to a non-existing bank will return a 
     * cancelled transaction
     * <br>Check that a transaction to an existing bank / non existing account
     * will return a cancelled transaction
     * <br>Check that a transaction to an existing bank / existing account will
     * return an accepted transaction
     */
    @Test
    @Category(IntegrationCat.class)
    public void testOverboeken() {

        //transfer to non-existing bank
        try {
            String transOmschrijving = "testfoutbank";
            bankRabo.getBankNaarBalie().doeTransactie(
                    klantRabo, rekRabo, "BESTAATNIET", 100, transOmschrijving);
            //wait for CentraleBank to receive/process transaction
            Thread.sleep(30000);

            //check that transcation was processed/returned
            Collection<Transactie> transacties = bankRabo.getBankNaarBalie()
                    .getTransacties(klantRabo, rekRabo);
            Transactie transactie = transacties.stream()
                    .filter(t -> t.getOmschrijving().equals(transOmschrijving))
                    .findAny().get();
            assertEquals(Status.DONE, transactie.getStatus());

            //check that no amount has been written of account
            Map<String, Integer> saldi = bankRabo.getBankNaarBalie()
                    .getSaldi(klantRabo);
            assertEquals(0, (int) saldi.get(rekRabo));

        } catch (RemoteException ex) {
            fail("Unexpected Remote Exception: " + ex.getMessage());
        } catch (NoSuchElementException ex) {
            fail("Failed to retrieve transaction from account");
        } catch (InterruptedException ex) {
            fail("InterruptedException on sleep");
        }

        //transfer to existing bank / non-existing account
        try {
            String transOmschrijving = "testfoutrek";
            bankRabo.getBankNaarBalie().doeTransactie(
                    klantRabo, rekRabo, "INGBFOUT", 100, transOmschrijving);
            //wait for CentraleBank to receive/process transaction
            Thread.sleep(30000);

            //check that transcation was processed/returned
            Collection<Transactie> transacties = bankRabo.getBankNaarBalie()
                    .getTransacties(klantRabo, rekRabo);
            Transactie transactie = transacties.stream()
                    .filter(t -> t.getOmschrijving().equals(transOmschrijving))
                    .findAny().get();
            assertEquals(Status.DONE, transactie.getStatus());

            //check that no amount has been written of account
            Map<String, Integer> saldi = bankRabo.getBankNaarBalie()
                    .getSaldi(klantRabo);
            assertEquals(0, (int) saldi.get(rekRabo));

        } catch (RemoteException ex) {
            fail("Unexpected Remote Exception: " + ex.getMessage());
        } catch (NoSuchElementException ex) {
            fail("Failed to retrieve transaction from account");
        } catch (InterruptedException ex) {
            fail("InterruptedException on sleep");
        }

        //transfer to existing bank / account
        try {
            String transOmschrijving = "testcorrect";
            bankRabo.getBankNaarBalie().doeTransactie(
                    klantRabo, rekRabo, rekING, 100, transOmschrijving);
            //wait for CentraleBank to receive/process transaction
            Thread.sleep(60000);

            //check that transcation was processed/returned
            Collection<Transactie> transacties = bankRabo.getBankNaarBalie()
                    .getTransacties(klantRabo, rekRabo);
            Transactie transactie = transacties.stream()
                    .filter(t -> t.getOmschrijving().equals(transOmschrijving))
                    .findAny().get();
            assertEquals(Status.DONE, transactie.getStatus());

            //check that amount has been written of sending account
            Map<String, Integer> saldiRabo = bankRabo.getBankNaarBalie()
                    .getSaldi(klantRabo);
            assertEquals(-100, (int) saldiRabo.get(rekRabo));
            
            //check that amount has been written to receiving account
            Map<String, Integer> saldiING = bankING.getBankNaarBalie()
                    .getSaldi(klantING);
            assertEquals(100, (int) saldiING.get(rekING));

        } catch (RemoteException ex) {
            fail("Unexpected Remote Exception: " + ex.getMessage());
        } catch (NoSuchElementException ex) {
            fail("Failed to retrieve transaction from account");
        } catch (InterruptedException ex) {
            fail("InterruptedException on sleep");
        }

    }

}
