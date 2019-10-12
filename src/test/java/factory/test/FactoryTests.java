package factory.test;

import factory.AbstractAnimal;
import factory.AnimalBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Factory tests.
 *
 * @author SleepLotus
 * @since 2019-10-12
 */
public class FactoryTests {
    @Before
    public void setUp() {
        System.out.println("Setup before running test method!");
    }

    @Test
    public void factoryTests() {
        AbstractAnimal<String, Integer> cat = AnimalBuilder.getAnimalInstanceByNameAndSex("Cat", "Cat001", "Female");
        System.out.println(cat.getType());
        System.out.println(cat.getName());
        System.out.println(cat.getSex());
        System.out.println(cat.transform("001"));

        AbstractAnimal<String, Integer> dog = AnimalBuilder.getAnimalInstanceByNameAndSex("Dog", "Dog002", "Male");
        System.out.println(dog.getType());
        System.out.println(dog.getName());
        System.out.println(dog.getSex());
        System.out.println(dog.transform("002"));

        System.out.println(AnimalBuilder.getAnimalInstanceByNameAndSex("Bird", "Bird001", "female").getName());
    }

    @After
    public void tearDown() {
        System.out.println("tear down after running test method!");
    }
}
