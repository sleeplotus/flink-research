package factory.cat;

import factory.AbstractAnimal;
import factory.AbstractAnimalFactory;

/**
 * Cat factory.
 *
 * @author SleepLotus
 * @since 2019-10-12
 */
public class CatFactory extends AbstractAnimalFactory<String, Integer> {

    public CatFactory(String type) {
        super(type);
    }

    @Override
    public AbstractAnimal<String, Integer> getInstance(String name, String sex) {
        return new Cat(getType(), name, sex);
    }
}
