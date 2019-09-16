package factory;

/**
 * @author Created by SleepLotus on 2019-09-16
 */
class AnimalBuilder {

    static AbstractAnimal getAnimalInstanceByNameAndSex(String typeName, String name, String sex) {
        AbstractAnimal animal = null;
        for (AnimalEnum element : AnimalEnum.values()) {
            if (element.getTypeName().equalsIgnoreCase(typeName)) {
                animal = element.getAnimalFactory().getInstance(name, sex);
                break;
            }
        }
        return animal;
    }
}
