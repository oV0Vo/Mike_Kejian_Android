package model.user;

import java.io.Serializable;

/**
 * Created by kisstheraik on 15/9/10.
 */
public class school implements Serializable{

    private String id;
    private String name;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String number;

}
