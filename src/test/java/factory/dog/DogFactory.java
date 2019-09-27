package factory.dog;

import factory.AbstractAnimal;
import factory.AbstractAnimalFactory;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
public class DogFactory extends AbstractAnimalFactory<String, Integer> {

    public DogFactory(String type) {
        super(type);
    }

    @Override
    public AbstractAnimal<String, Integer> getInstance(String name, String sex) {
        return new Dog(this.getType(), name, sex);
    }
}
