package factory;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
public class DogFactory extends AbstractAnimalFactory {

    DogFactory(String name) {
        this.name = name;
    }

    @Override
    public AbstractAnimal getInstance(String sex) {
        return new Dog(this.name, sex);
    }

}
