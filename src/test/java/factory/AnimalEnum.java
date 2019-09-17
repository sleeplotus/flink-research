package factory;

import factory.cat.CatFactory;
import factory.dog.DogFactory;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
public enum AnimalEnum {

    DOG(new DogFactory("Dog")), CAT(new CatFactory("Cat"));

    private final String type;

    private final AbstractAnimalFactory<String, Integer> animalFactory;

    AnimalEnum(AbstractAnimalFactory<String, Integer> animalFactory) {
        this.type = animalFactory.getType();
        this.animalFactory = animalFactory;
    }

    public String getType() {
        return type;
    }

    public AbstractAnimalFactory<String, Integer> getAnimalFactory() {
        return animalFactory;
    }
}
