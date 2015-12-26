/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.test;

import bank.Bank;
import common.IBalieObserver;
import common.Transactie;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Gijs
 */
public class BankTest {
    
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
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of exitOnClose method, of class Bank.
     */
    @Test
    public void testExitOnClose() {

    }

//     /**
//     *
//     * @param naam naam van de gebruiker
//     * @param woonplaats Nederlandse woonplaats van de gebruiker
//     * @return klantnummer van de geregistreerde gebruiker
//     * @throws RemoteException indien woonplaats niet geldig is
//     */
//    int registreerKlant(String naam, String woonplaats) throws RemoteException;
//
//    /**
//     *
//     * @param klantnummer nummer behorende bij een regegistreerde klant
//     * @return rekeningnummer van de geopende rekening
//     * @throws RemoteException indien klantnummer niet bij de bank bekend is
//     */
//    String openRekening(int klantnummer) throws RemoteException;
//
//    /**
//     *
//     * @param klantnummer nummer behorende bij een regegistreerde klant
//     * @return map van rekeningen en het daarbij behorende saldo in eurocenten
//     * van de gebruiker
//     * @throws RemoteException indien klantnummer niet bij de bank bekend is
//     */
//    Map<String, Integer> getSaldi(int klantnummer) throws RemoteException;
//
//    /**
//     *
//     * @param klantnummer nummer behorende bij een regegistreerde klant
//     * @param rekeningnummer rekeningnummer waarvan transacties getoond worden
//     * @return collectie met bij rekening behorende Transacties
//     * @throws RemoteException indien klantnummer of rekeningnummer niet bij de
//     * bank bekend is
//     */
//    Collection<Transactie> getTransacties(int klantnummer, String rekeningnummer) throws RemoteException;
//
//    /**
//     *
//     * @param klantnummer nummer behorende bij een regegistreerde klant
//     * @param rekeningVan een rekening behorende bij klantnummer
//     * @param rekeningNaar een tegenrekening waar bedrag op gestort wordt
//     * @param bedrag een bedrag in eurocenten, groter dan 0 en kleiner dan
//     * 10000000 (honderd duizend euro)
//     * @param omschr een door de gebruiker opgegeven omschrijving
//     * @return true indien afboeken op eigen rekening gelukt, anders false
//     * @throws RemoteException indien klantnummer of rekeningVan niet bij de
//     * bank bekend is of bedrag niet geldig is
//     */
//    boolean doeTransactie(int klantnummer, String rekeningVan, String rekeningNaar, int bedrag, String omschr) throws RemoteException;
//
//    /**
//     * Deze methode geeft een IBalieObserver terug aan de bank zodra de bank is
//     * gevonden door de balie. Dit maakt tweezijdige communicatie tussen bank en
//     * balie mogelijk.
//     *
//     * @param bankCode de code van de balie (moet dezelfde zijn als die van de
//     * bank)
//     * @param balie IBalieObserver interface
//     * @throws java.rmi.RemoteException
//     */
//    public void registreerBalie(String bankCode, IBalieObserver balie) throws RemoteException;
//
//    /**
//     * Deze methode maak registratie van een balie bij een bank ongedaan.
//     *
//     * @param bankCode de code van de balie (moet dezelfde zijn als die van de
//     * bank)
//     * @param balie IBalieObserver interface
//     * @throws java.rmi.RemoteException
//     */
//    public void deregistreerBalie(String bankCode, IBalieObserver balie) throws RemoteException;
//    
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
//    //JAVADOC MAKEN
//    private void registreer(int port) {
//
//        try{
//        Bank bank = new Bank("a","s",1);
//        } catch (RemoteException e){
//            
//        }
//    }
    
}
