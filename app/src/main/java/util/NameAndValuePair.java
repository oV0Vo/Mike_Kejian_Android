package util;

/**
 * Created by violetMoon on 2015/9/10.
 */
public class NameAndValuePair<Name, Value> {
    private Name name;
    private Value value;

    public NameAndValuePair() {
        //requried empty construtor
    }

    public NameAndValuePair(Name name, Value value) {
        this.name = name;
        this.value = value;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

}
