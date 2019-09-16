package factory;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
class DogFactory extends AbstractAnimalFactory {

    DogFactory(String name) {
        this.setTypeName(name);
    }

    @Override
    public AbstractAnimal getInstance(String name, String sex) {
        return new Dog(this.getTypeName(), name, sex);
    }
}
