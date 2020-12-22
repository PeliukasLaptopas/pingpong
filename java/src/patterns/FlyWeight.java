package patterns;

import java.util.ArrayList;
import java.util.List;

public class FlyWeight {
    private static final List<String> startTypes = new ArrayList<String>();
    private static final String types[] = { "Start", "Begin", "Lets go" };

    public static String getType(String type) {
        boolean m = startTypes.contains(type);

        if(!m) {
            startTypes.add(type);
            return type;
        }

        return types[0];
    }
}
