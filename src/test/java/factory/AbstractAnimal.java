package factory;

/**
 * @param <IN>  Type of the input elements.
 * @param <OUT> Type of the returned elements.
 * @author Created by SleepLotus on 2019-09-12
 */
public abstract class AbstractAnimal<IN, OUT> {

    private String type;

    private String name;

    private String sex;

    public abstract OUT transform(IN in);

    public String getType() {
        return type;
    }

    protected void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    protected void setSex(String sex) {
        this.sex = sex;
    }
}
