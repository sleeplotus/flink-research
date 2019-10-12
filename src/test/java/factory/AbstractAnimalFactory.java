package factory;

/**
 * Abstract animal factory.
 *
 * @param <IN>  Type of the input elements.
 * @param <OUT> Type of the returned elements.
 * @author SleepLotus
 * @since 2019-10-12
 */
public abstract class AbstractAnimalFactory<IN, OUT> {

    private String type;

    public AbstractAnimalFactory(String type) {
        this.type = type;
    }

    public abstract AbstractAnimal<IN, OUT> getInstance(String name, String sex);

    protected String getType() {
        return type;
    }
}
