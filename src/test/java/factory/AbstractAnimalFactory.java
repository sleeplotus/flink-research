package factory;

/**
 * @param <IN>  Type of the input elements.
 * @param <OUT> Type of the returned elements.
 * @author Created by SleepLotus on 2019-09-12
 */
public abstract class AbstractAnimalFactory<IN, OUT> {

    private String type;

    public abstract AbstractAnimal<IN, OUT> getInstance(String name, String sex);

    protected String getType() {
        return type;
    }

    protected void setType(String type) {
        this.type = type;
    }
}
