package factory;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
abstract class AbstractAnimal {

    private String typeName;

    private String name;

    private String sex;

    String getTypeName() {
        return typeName;
    }

    void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getSex() {
        return sex;
    }

    void setSex(String sex) {
        this.sex = sex;
    }
}
