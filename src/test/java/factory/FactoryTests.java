package factory;

import org.junit.Test;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
public class FactoryTests {
    @Test
    public void factoryTests() {
        System.out.println(AbstractAnimalFactory.getAnimalInstanceByName("Cat", "female").getName());
        System.out.println(AbstractAnimalFactory.getAnimalInstanceByName("Cat", "female").getSex());
        System.out.println(AbstractAnimalFactory.getAnimalInstanceByName("Dog", "male").getName());
        System.out.println(AbstractAnimalFactory.getAnimalInstanceByName("Dog", "male").getSex());
        System.out.println(AbstractAnimalFactory.getAnimalInstanceByName("Bird", "female").getName());
    }


}
