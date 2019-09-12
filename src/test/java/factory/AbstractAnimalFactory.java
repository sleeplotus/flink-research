package factory;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
abstract class AbstractAnimalFactory {

    String name;

    abstract AbstractAnimal getInstance(String sex);

    static AbstractAnimal getAnimalInstanceByName(String name, String sex) {
        AbstractAnimal animal = null;
        for (AnimalEnum element : AnimalEnum.values()) {
            if (element.getName().equalsIgnoreCase(name)) {
                animal = element.getAnimalFactory().getInstance(sex);
                break;
            }
        }
        return animal;
    }
}
