package factory;

/**
 * Abstract animal.
 *
 * @param <IN>  Type of the input elements.
 * @param <OUT> Type of the returned elements.
 * @author SleepLotus
 * @since 2019-10-12
 */
public abstract class AbstractAnimal<IN, OUT> {

    private String type;

    private String name;

    private String sex;

    public AbstractAnimal(String type, String name, String sex) {
        this.type = type;
        this.name = name;
        this.sex = sex;
    }

    public abstract OUT transform(IN in);

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }
}
