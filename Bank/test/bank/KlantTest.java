/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gijs
 */
public class KlantTest {
    
    public KlantTest() {
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
     * Test of getKlantNummer method, of class Klant.
     */
    @Test
    public void testGetKlantNummer() {
        System.out.println("getKlantNummer");
        Klant instance = null;
        int expResult = 0;
        int result = instance.getKlantNummer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNaam method, of class Klant.
     */
    @Test
    public void testGetNaam() {
        System.out.println("getNaam");
        Klant instance = null;
        String expResult = "";
        String result = instance.getNaam();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWoonplaats method, of class Klant.
     */
    @Test
    public void testGetWoonplaats() {
        System.out.println("getWoonplaats");
        Klant instance = null;
        String expResult = "";
        String result = instance.getWoonplaats();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Klant.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Klant instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
