package factory;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
public enum AnimalEnum {
    DOG(new DogFactory("Dog")), CAT(new CatFactory("Cat"));

    private String name;

    private AbstractAnimalFactory animalFactory;

    public String getName() {
        return name;
    }

    public AbstractAnimalFactory getAnimalFactory() {
        return animalFactory;
    }

    AnimalEnum(AbstractAnimalFactory animalFactory) {
        this.animalFactory = animalFactory;
        this.name = animalFactory.getName();
    }
}
