package factory;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
public class CatFactory extends AbstractAnimalFactory {

    CatFactory(String name) {
        this.name = name;
    }

    @Override
    public AbstractAnimal getInstance(String sex) {
        return new Cat(this.name, sex);
    }
}
