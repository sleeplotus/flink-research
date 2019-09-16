package factory;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
class Dog extends AbstractAnimal {

    Dog(String typeName, String name, String sex) {
        this.setTypeName(typeName);
        this.setName(name);
        this.setSex(sex);
    }
}
