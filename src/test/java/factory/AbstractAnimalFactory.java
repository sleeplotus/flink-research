package factory;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
abstract class AbstractAnimalFactory {

    abstract Animal getInstance();

    abstract String getName();

    static Animal getAnimalInstanceByName(String name) {
        Animal animal = null;
        for (AnimalEnum element : AnimalEnum.values()) {
            if (element.getName().equalsIgnoreCase(name)) {
                animal = element.getAnimalFactory().getInstance();
                break;
            }
        }
        return animal;
    }
}
