package factory;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
class CatFactory extends AbstractAnimalFactory {

    CatFactory(String typeName) {
        this.setTypeName(typeName);
    }

    @Override
    public AbstractAnimal getInstance(String name, String sex) {
        return new Cat(this.getTypeName(), name, sex);
    }
}
