package factory.cat;

import factory.AbstractAnimal;

/**
 * Cat.
 *
 * @author SleepLotus
 * @since 2019-10-12
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
