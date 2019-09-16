package factory;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
abstract class AbstractAnimalFactory {

    private String typeName;

    abstract AbstractAnimal getInstance(String name, String sex);

    String getTypeName() {
        return typeName;
    }

    void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
