package util;

import java.util.ArrayList;

/**
 * Created by violetMoon on 2015/9/10.
 */
public class PropertiesFileIO {

    public static <Name, Value> boolean savedProperties(ArrayList<NameAndValuePair<Name, Value>> properties, String fileName) {
        return true;
    }

    public static <Name, Value> ArrayList<NameAndValuePair<Name, Value>> getProperties(String fileName) {
        return new ArrayList<NameAndValuePair<Name, Value>>();
    }

}
