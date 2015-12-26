package bank.suite;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This category will only run the speed-related tests from the given test classes
 * @author fhict
 *
 */
@RunWith(Categories.class)
//@IncludeCategory(SpeedUpTestsCat.class)
//@ExcludeCategory(SpeedDownTestsCat.class)
//@SuiteClasses({ BicycleTest.class, MountainBikeTest.class })
public class BankSuite {

}
