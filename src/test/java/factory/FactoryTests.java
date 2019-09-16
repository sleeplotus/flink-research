package factory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
public class FactoryTests {
    @Before
    public void setUp() {
        System.out.println("Setup before running test method!");
    }

    @Test
    public void factoryTests() {
        System.out.println(AnimalBuilder.getAnimalInstanceByNameAndSex("Cat","Cat001", "Female").getName());
        System.out.println(AnimalBuilder.getAnimalInstanceByNameAndSex("Cat","Cat002", "Female").getSex());
        System.out.println(AnimalBuilder.getAnimalInstanceByNameAndSex("Cat","Cat003", "Female").getTypeName());
        System.out.println(AnimalBuilder.getAnimalInstanceByNameAndSex("Dog","Dog001", "Male").getName());
        System.out.println(AnimalBuilder.getAnimalInstanceByNameAndSex("Dog","Dog002", "Male").getSex());
        System.out.println(AnimalBuilder.getAnimalInstanceByNameAndSex("Dog","Dog003", "Male").getTypeName());
        System.out.println(AnimalBuilder.getAnimalInstanceByNameAndSex("Bird","Bird001", "female").getName());
    }

    @After
    public void tearDown() {
        System.out.println("tear down after running test method!");
    }
}
