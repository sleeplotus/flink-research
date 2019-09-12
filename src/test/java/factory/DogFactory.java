package factory;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
public class DogFactory extends AbstractAnimalFactory {

    private String name;

    DogFactory(String name){
        this.name = name;
    }

    @Override
    public Animal getInstance() {
        return new Dog(this.name);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
