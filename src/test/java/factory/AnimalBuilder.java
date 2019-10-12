package factory;

/**
 * Animal builder.
 *
 * @author SleepLotus
 * @since 2019-10-12
 */
public class AnimalBuilder {

    public static AbstractAnimal<String, Integer> getAnimalInstanceByNameAndSex(String typeName, String name,
                                                                                String sex) {
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
