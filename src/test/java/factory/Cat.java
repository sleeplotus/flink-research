package factory;

/**
 * @author Created by SleepLotus on 2019-09-12
 */
public class Cat implements Animal {

    private String name;

    Cat(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
