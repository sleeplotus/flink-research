package factory;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
public class Dog implements Animal {

    private String name;

    Dog(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
