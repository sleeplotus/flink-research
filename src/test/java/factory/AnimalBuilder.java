package factory;

/**
 * @author Created by SleepLotus on 2019-09-16
 */
public class AnimalBuilder {

    public static AbstractAnimal<String, Integer> getAnimalInstanceByNameAndSex(String typeName, String name, String sex) {
        AbstractAnimal<String, Integer> animal = null;
        for (AnimalEnum element : AnimalEnum.values()) {
            if (element.getType().equalsIgnoreCase(typeName)) {
                animal = element.getAnimalFactory().getInstance(name, sex);
                break;
            }
        }
        return animal;
    }
}
