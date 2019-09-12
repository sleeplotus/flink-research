package factory;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
public class CatFactory extends AbstractAnimalFactory {

    private String name;

    CatFactory(String name){
        this.name = name;
    }

    @Override
    public Animal getInstance() {
        return new Cat(this.name);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
