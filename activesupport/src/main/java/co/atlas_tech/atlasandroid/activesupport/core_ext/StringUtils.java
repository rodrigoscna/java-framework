package co.atlas_tech.atlasandroid.activesupport.core_ext;

import java.util.List;

import co.atlas_tech.atlasandroid.activesupport.annotations.Beta;

/**
 * @author Rodrigo Scomasson do Nascimento <rodrigo.sc.na@gmail.com>
 */
@Beta
public class StringUtils {
    /**
     * Returns a string created by converting each element of the array to a
     * string, separated by glue.
     *
     * @param glue The desired separator.
     * @param list The list of elements to join.
     * @return A string joined by the desired separator.
     */
    public static String join(String glue, List<String> list) {
        StringBuilder joinedString = new StringBuilder();

        int listSize = list.size();

        for (int i = 0; i < listSize; i++) {
            if (i == listSize - 1) {
                joinedString.append(list.get(i));
            } else {
                joinedString.append(list.get(i)).append(glue);
            }
        }

        return joinedString.toString();
    }
}
