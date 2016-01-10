package bank.suite;

import bank.category.BankCat;
import bank.category.BankNaarBalieCat;
import bank.category.BankNaarCentraleCat;
import bank.category.RekeningCat;
import bank.test.unit.BankTest;
import bank.test.unit.RekeningTest;
import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Run all tests in categories as included below
 *
 */
@RunWith(Categories.class)
@IncludeCategory({BankCat.class, BankNaarBalieCat.class,BankNaarCentraleCat.class, RekeningCat.class})
@SuiteClasses({ BankTest.class, RekeningTest.class })
public class BankSuite {

}
