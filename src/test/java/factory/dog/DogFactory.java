package factory.dog;

import factory.AbstractAnimal;
import factory.AbstractAnimalFactory;

/**
 * Dog factory.
 *
 * @author SleepLotus
 * @since 2019-10-12
 */
public class DogFactory extends AbstractAnimalFactory<String, Integer> {

    public DogFactory(String type) {
        super(type);
    }

    @Override
    public AbstractAnimal<String, Integer> getInstance(String name, String sex) {
        return new Dog(getType(), name, sex);
    }
}
