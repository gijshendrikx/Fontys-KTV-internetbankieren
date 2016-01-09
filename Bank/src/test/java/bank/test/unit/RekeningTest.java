/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.test.unit;

import bank.Rekening;
import bank.category.RekeningCat;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.experimental.categories.Category;

/**
 *
 * @author Gijs
 */
public class RekeningTest {

    /**
     * Test of kanAfboeken method, of class Rekening.
     *
     * <br>Check if true/false is returned corresponding to the amount to be
     * written off and the maximal amount possible to write off
     */
    @Test
    @Category(RekeningCat.class)
    public void testKanAfboeken() {
        //rekening met 10000 cent kredietlimiet
        Rekening r = new Rekening(10000, "ABNA1", 1234);

        //meer afboeken dan mogelijk
        assertFalse(r.kanAfboeken(10001));

        //minder/evenveel afboeken dan mogelijk.
        assertTrue(r.kanAfboeken(10000));
    }

    /**
     * Test of boekAf method, of class Rekening.
     *
     * <br>Check if amount is written off correctly
     */
    @Test
    @Category(RekeningCat.class)
    public void testBoekAf() {
        //0 saldo, 10000 krediet
        Rekening r = new Rekening(10000, "ABNA1", 1234);
        r.boekAf(6000);
        assertEquals(-6000, r.getSaldo());
    }

    /**
     * Test of boekBij method, of class Rekening.
     *
     * <br>Check if amount is added to account correctly
     */
    @Test
    @Category(RekeningCat.class)
    public void testBoekBij() {
        //0 saldo, 10000 krediet
        Rekening r = new Rekening(10000, "ABNA1", 1234);
        r.boekBij(6000);
        assertEquals(6000, r.getSaldo());
    }
}
