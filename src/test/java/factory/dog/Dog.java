package factory.dog;

import factory.AbstractAnimal;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
class Dog extends AbstractAnimal<String, Integer> {

    Dog(String type, String name, String sex) {
        super(type, name, sex);
    }

    @Override
    public Integer transform(String string) {
        return Integer.valueOf(string);
    }
}
