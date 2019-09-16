package factory;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
public enum AnimalEnum {

    DOG(new DogFactory("Dog")), CAT(new CatFactory("Cat"));

    private final String typeName;

    private final AbstractAnimalFactory animalFactory;

    AnimalEnum(AbstractAnimalFactory animalFactory) {
        this.typeName = animalFactory.getTypeName();
        this.animalFactory = animalFactory;
    }

    public String getTypeName() {
        return typeName;
    }

    public AbstractAnimalFactory getAnimalFactory() {
        return animalFactory;
    }
}
