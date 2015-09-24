package bl.User;

/**
 * Created by kisstheraik on 15/9/23.
 */
public abstract class Subject {

    public abstract boolean addObserver(Observer observer);
    public abstract boolean deleteObserver(Observer observer);
    public abstract boolean notifyObserver();

}
