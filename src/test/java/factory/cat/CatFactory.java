package factory.cat;

import factory.AbstractAnimal;
import factory.AbstractAnimalFactory;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
public class CatFactory extends AbstractAnimalFactory<String, Integer> {

    public CatFactory(String typeName) {
        this.setType(typeName);
    }

    @Override
    public AbstractAnimal<String, Integer> getInstance(String name, String sex) {
        return new Cat(this.getType(), name, sex);
    }
}
