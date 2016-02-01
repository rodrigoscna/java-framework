package tech.arauk.ark.arel;

import java.util.Iterator;
import java.util.List;

public class ArelUtils {
    public static String join(List<String> stringsList, String separator) {
        if (stringsList == null) {
            return null;
        }

        Iterator iterator = stringsList.iterator();

        if (!iterator.hasNext()) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(iterator.next());

        while (iterator.hasNext()) {
            if (separator != null) {
                stringBuilder.append(separator);
            }

            Object object = iterator.next();

            if (object != null) {
                stringBuilder.append(object);
            }
        }

        return stringBuilder.toString();
    }
}
