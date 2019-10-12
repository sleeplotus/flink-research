package factory.dog;

import factory.AbstractAnimal;

/**
 * Abstract animal.
 *
 * @author SleepLotus
 * @since 2019-10-12
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
