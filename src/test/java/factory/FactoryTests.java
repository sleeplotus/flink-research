package factory;

import org.junit.Test;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
public class FactoryTests {
    @Test
    public void factoryTests() {
        System.out.println(AbstractAnimalFactory.getAnimalInstanceByNameAndSex("Cat", "female").getName());
        System.out.println(AbstractAnimalFactory.getAnimalInstanceByNameAndSex("Cat", "female").getSex());
        System.out.println(AbstractAnimalFactory.getAnimalInstanceByNameAndSex("Dog", "male").getName());
        System.out.println(AbstractAnimalFactory.getAnimalInstanceByNameAndSex("Dog", "male").getSex());
        System.out.println(AbstractAnimalFactory.getAnimalInstanceByNameAndSex("Bird", "female").getName());
    }


}
