package factory;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
class Cat extends AbstractAnimal {

    Cat(String typeName, String name, String sex) {
        this.setTypeName(typeName);
        this.setName(name);
        this.setSex(sex);
    }
}
