package factory.dog;

import factory.AbstractAnimal;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
class Dog extends AbstractAnimal<String, Integer> {

    Dog(String type, String name, String sex) {
        this.setType(type);
        this.setName(name);
        this.setSex(sex);
    }

    @Override
    public Integer transform(String string) {
        return Integer.valueOf(string);
    }
}
