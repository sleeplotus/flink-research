package factory.cat;

import factory.AbstractAnimal;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
class Cat extends AbstractAnimal<String, Integer> {

    Cat(String type, String name, String sex) {
        super(type, name, sex);
    }

    @Override
    public Integer transform(String string) {
        return Integer.valueOf(string);
    }
}
