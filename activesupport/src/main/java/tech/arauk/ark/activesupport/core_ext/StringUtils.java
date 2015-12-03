package tech.arauk.ark.activesupport.core_ext;

import java.util.Collection;
import java.util.Iterator;

import tech.arauk.ark.activesupport.Inflector;
import tech.arauk.ark.activesupport.annotations.Beta;

/**
 * @author Rodrigo Scomazzon do Nascimento <rodrigo.sc.na@gmail.com>
 */
@Beta
public class StringUtils {
    /**
     * Returns true if str has a length of zero.
     * <pre>{@code
     * StringUtils.isEmpty("hello") == false
     * StringUtils.isEmpty("") == true
     * }</pre>
     *
     * @param term The string to be tested.
     * @return True if term has a length of zero.
     */
    public static boolean isEmpty(String term) {
        return (term == null) || (term.length() == 0);
    }

    /**
     * Returns a string created by converting each element of the array to a
     * string, separated by glue.
     *
     * @param glue The desired separator.
     * @param array The array of strings to join.
     * @return A string joined by the desired separator.
     */
    public static String join(String[] array, String glue) {
        StringBuilder joinedString = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            joinedString.append(array[i]);

            if (i < array.length - 1) {
                joinedString.append(glue);
            }
        }

        return joinedString.toString();
    }

    /**
     * Returns a string created by converting each element of the array to a
     * string, separated by glue.
     *
     * @param glue The desired separator.
     * @param list The list of elements to join.
     * @return A string joined by the desired separator.
     */
    public static String join(Collection<String> list, String glue) {
        StringBuilder joinedString = new StringBuilder();

        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            joinedString.append(iterator.next().toString());

            if (iterator.hasNext()) {
                joinedString.append(glue);
            }
        }

        return joinedString.toString();
    }

    /**
     * By default, camelize converts strings to UpperCamelCase. If the argument
     * firstLetter is set to "lower" then camelize produces lowerCamelCase.
     * <p>
     * camelize will also convert "/" to "." which is useful for converting
     * paths to packages.
     * <pre>{@code
     * StringUtils.camelize("active_record") == "ActiveRecord"
     * StringUtils.camelize("active_record", "lower") == "activeRecord"
     * StringUtils.camelize("ark/activerecord/errors") == "ark.activerecord.Errors"
     * StringUtils.camelize("ark/activerecord/errors", "lower") == "ark.activerecord.errors"
     * }</pre>
     *
     * @param term The string to be converted.
     * @return A new camel case string.
     */
    public static String camelize(String term) {
        return camelize(term, null);
    }

    /**
     * By default, camelize converts strings to UpperCamelCase. If the argument
     * firstLetter is set to "lower" then camelize produces lowerCamelCase.
     * <p>
     * camelize will also convert "/" to "." which is useful for converting
     * paths to packages.
     * <pre>{@code
     * StringUtils.camelize("active_record") == "ActiveRecord"
     * StringUtils.camelize("active_record", "lower") == "activeRecord"
     * StringUtils.camelize("ark/activerecord/errors") == "ark.activerecord.Errors"
     * StringUtils.camelize("ark/activerecord/errors", "lower") == "ark.activerecord.errors"
     * }</pre>
     *
     * @param term        The string to be converted.
     * @param firstLetter Indicates whether the first letter should be upper or
     *                    lower cased.
     * @return A new camel case string.
     */
    public static String camelize(String term, String firstLetter) {
        if (firstLetter == null) {
            firstLetter = "upper";
        }

        switch (firstLetter) {
            case "lower":
                return Inflector.camelize(term, false);
            default:
                return Inflector.camelize(term, true);
        }
    }

    public static String capitalize(String word) {
        return Inflector.capitalize(word);
    }

    /**
     * Creates a class name from a plural table name like Ark does for table
     * names to models. Note that this returns a string and not a class. (To
     * convert to an actual class follow Inflector with
     * {@link #constantize(String)}.)
     * <pre>{@code
     * StringUtils.classify("egg_and_hams") == "EggAndHam"
     * StringUtils.classify("posts") == "Post"
     * }</pre>
     *
     * @param tableName The plural table name to be converted.
     * @return A new class name string.
     */
    public static String classify(String tableName) {
        return Inflector.classify(tableName);
    }

    /**
     * Tries to find a declared constant with the name specified in the string.
     * It throws an IllegalArgumentException when the name is not in CamelCase
     * or is not initialized.
     * <pre>{@code
     * StringUtils.constantize("com.example.MyClass") == MyClass.class
     * StringUtils.constantize("com.example.blargle") throws IllegalArgumentException: wrong constant name blargle
     * }</pre>
     *
     * @param camelCasedWord The camel case term that references the class.
     * @return A Class as specified in the string.
     * @throws ClassNotFoundException Is thrown when the constant is unknown.
     * @see tech.arauk.ark.activesupport.Inflector#constantize(String)
     */
    public static Class constantize(String camelCasedWord) throws ClassNotFoundException {
        return Inflector.constantize(camelCasedWord);
    }

    /**
     * Replaces underscores with dashes in the string.
     * <pre>{@code
     * StringUtils.dasherize("puni_puni") == "puni-puni"
     * }</pre>
     *
     * @param underscoredWord The underscored word to be converted.
     * @return A new string with dashes instead of underscores.
     */
    public static String dasherize(String underscoredWord) {
        return Inflector.dasherize(underscoredWord);
    }

    /**
     * Removes the rightmost segment from the constant expression in the string.
     * <pre>{@code
     * StringUtils.deconstantize("com.example.HTTP") == "com.example"
     * StringUtils.deconstantize("String") == ""
     * StringUtils.deconstantize("") == ""
     * }</pre>
     *
     * @param path The path from which to obtain the modules.
     * @return A new string with the retrieved modules.
     * @see #demodulize(String)
     */
    public static String deconstantize(String path) {
        return Inflector.deconstantize(path);
    }

    /**
     * Removes the module part from the constant expression in the string.
     * <pre>{@code
     * StringUtils.demodulize("com.example.Inflections") == "Inflections"
     * StringUtils.demodulize("String") == "String"
     * StringUtils.demodulize("") == ""
     * }</pre>
     *
     * @param path The path from which to obtain the constant.
     * @return A new string with the retrieved constant.
     * @see #deconstantize(String)
     */
    public static String demodulize(String path) {
        return Inflector.demodulize(path);
    }

    /**
     * Creates a foreign key name from a class name.
     * separateClassNameAndIdWithUnderscore sets whether the method should put
     * "_" between the name and "id".
     * <pre>{@code
     * StringUtils.foreignKey("Message") == "message_id"
     * StringUtils.foreignKey("Message", false) == "messageid"
     * StringUtils.foreignKey("com.example.Post") == "post_id"
     * }</pre>
     *
     * @param className The name of the class which the foreign key will
     *                  reference.
     * @return A new string with the foreign key name.
     */
    public static String foreignKey(String className) {
        return foreignKey(className, true);
    }

    /**
     * Creates a foreign key name from a class name.
     * separateClassNameAndIdWithUnderscore sets whether the method should put
     * "_" between the name and "id".
     * <pre>{@code
     * StringUtils.foreignKey("Message") == "message_id"
     * StringUtils.foreignKey("Message", false) == "messageid"
     * StringUtils.foreignKey("com.example.Post") == "post_id"
     * }</pre>
     *
     * @param className                            The name of the class which
     *                                             the foreign key will
     *                                             reference.
     * @param separateClassNameAndIdWithUnderscore Indicates whether the method
     *                                             should put "_" between the
     *                                             name and "id".
     * @return A new string with the foreign key name.
     */
    public static String foreignKey(String className, boolean separateClassNameAndIdWithUnderscore) {
        return Inflector.foreignKey(className, separateClassNameAndIdWithUnderscore);
    }

    /**
     * Capitalizes the first word, turns underscores into spaces, and strips a
     * trailing '_id' if present. Like titleize, this is meant for creating
     * pretty output.
     * <p>
     * The capitalization of the first word can be turned off by setting the
     * optional parameter capitalize to false. By default, this parameter is
     * true.
     * <pre>{@code
     * StringUtils.humanize("employee_salary") == "Employee salary"
     * StringUtils.humanize("author_id") == "Author"
     * StringUtils.humanize("author_id", false) == "author"
     * StringUtils.humanize("_id") == "id"
     * }</pre>
     *
     * @param lowerCaseAndUnderscoredWord The word to be humanized.
     * @return The humanized version of the word.
     */
    public static String humanize(String lowerCaseAndUnderscoredWord) {
        return humanize(lowerCaseAndUnderscoredWord, true);
    }

    /**
     * Capitalizes the first word, turns underscores into spaces, and strips a
     * trailing '_id' if present. Like {@link #titleize(String)}, this is meant
     * for creating pretty output.
     * <p>
     * The capitalization of the first word can be turned off by setting the
     * optional parameter capitalize to false. By default, this parameter is
     * true.
     * <pre>{@code
     * StringUtils.humanize("employee_salary") == "Employee salary"
     * StringUtils.humanize("author_id") == "Author"
     * StringUtils.humanize("author_id", false) == "author"
     * StringUtils.humanize("_id") == "id"
     * }</pre>
     *
     * @param lowerCaseAndUnderscoredWord The word to be humanized.
     * @param capitalizeFirstWord         Indicates whether the first word
     *                                    should be capitalized.
     * @return The humanized version of the word.
     */
    public static String humanize(String lowerCaseAndUnderscoredWord, boolean capitalizeFirstWord) {
        return Inflector.humanize(lowerCaseAndUnderscoredWord, capitalizeFirstWord);
    }

    /**
     * Replaces special characters in a string so that it may be used as part of
     * a "pretty" URL.
     * <pre>{@code
     * StringUtils.parameterize("Donald E. Knuth") == "donald-e-knuth"
     * StringUtils.parameterize("^trés|Jolie-- ") == "tres-jolie"
     * }</pre>
     *
     * @param string The string to be parameterized.
     * @return A new string without special characters.
     */
    public static String parameterize(String string) {
        return Inflector.parameterize(string);
    }

    /**
     * Returns the plural form of the word in the string.
     * <p>
     * If the optional parameter count is specified, the singular form will be
     * returned if count == 1. For any other value of count the plural will be
     * returned.
     * <p>
     * If the optional parameter locale is specified, the word will be
     * pluralized as a word of that language. By default, this parameter is set
     * to "en-US". You must define your own inflection rules for languages other
     * than English.
     * <pre>{@code
     * StringUtils.pluralize("post") == "posts"
     * StringUtils.pluralize("octopus") == "octopi"
     * StringUtils.pluralize("sheep") == "sheep"
     * StringUtils.pluralize("words") == "words"
     * StringUtils.pluralize("the blue mailman") == "the blue mailmen"
     * StringUtils.pluralize("CamelOctopus") == "CamelOctopi"
     * StringUtils.pluralize("apple", 1) == "apple"
     * StringUtils.pluralize("apple", 2) == "apples"
     * StringUtils.pluralize("papel", "pt-BR") == "papéis"
     * StringUtils.pluralize("papel", 1, "pt-BR") == "papel"
     * }</pre>
     *
     * @param word The word to be pluralized.
     * @return A new string with the plural form of the word.
     */
    public static String pluralize(String word) {
        return pluralize(word, null, "en-US");
    }

    /**
     * Returns the plural form of the word in the string.
     * <p>
     * If the optional parameter count is specified, the singular form will be
     * returned if count == 1. For any other value of count the plural will be
     * returned.
     * <p>
     * If the optional parameter locale is specified, the word will be
     * pluralized as a word of that language. By default, this parameter is set
     * to "en-US". You must define your own inflection rules for languages other
     * than English.
     * <pre>{@code
     * StringUtils.pluralize("post") == "posts"
     * StringUtils.pluralize("octopus") == "octopi"
     * StringUtils.pluralize("sheep") == "sheep"
     * StringUtils.pluralize("words") == "words"
     * StringUtils.pluralize("the blue mailman") == "the blue mailmen"
     * StringUtils.pluralize("CamelOctopus") == "CamelOctopi"
     * StringUtils.pluralize("apple", 1) == "apple"
     * StringUtils.pluralize("apple", 2) == "apples"
     * StringUtils.pluralize("papel", "pt-BR") == "papéis"
     * StringUtils.pluralize("papel", 1, "pt-BR") == "papel"
     * }</pre>
     *
     * @param word  The word to be pluralized.
     * @param count If the value of this parameter is equal to 1 the singular
     *              form will be returned. For any other value the plural form
     *              will be returned.
     * @return A new string with the plural form of the word.
     */
    public static String pluralize(String word, Integer count) {
        return pluralize(word, count, "en-US");
    }

    /**
     * Returns the plural form of the word in the string.
     * <p>
     * If the optional parameter count is specified, the singular form will be
     * returned if count == 1. For any other value of count the plural will be
     * returned.
     * <p>
     * If the optional parameter locale is specified, the word will be
     * pluralized as a word of that language. By default, this parameter is set
     * to "en-US". You must define your own inflection rules for languages other
     * than English.
     * <pre>{@code
     * StringUtils.pluralize("post") == "posts"
     * StringUtils.pluralize("octopus") == "octopi"
     * StringUtils.pluralize("sheep") == "sheep"
     * StringUtils.pluralize("words") == "words"
     * StringUtils.pluralize("the blue mailman") == "the blue mailmen"
     * StringUtils.pluralize("CamelOctopus") == "CamelOctopi"
     * StringUtils.pluralize("apple", 1) == "apple"
     * StringUtils.pluralize("apple", 2) == "apples"
     * StringUtils.pluralize("papel", "pt-BR") == "papéis"
     * StringUtils.pluralize("papel", 1, "pt-BR") == "papel"
     * }</pre>
     *
     * @param word   The word to be pluralized.
     * @param locale The language in which the word will be pluralized.
     * @return A new string with the plural form of the word.
     */
    public static String pluralize(String word, String locale) {
        return pluralize(word, null, locale);
    }

    /**
     * Returns the plural form of the word in the string.
     * <p>
     * If the optional parameter count is specified, the singular form will be
     * returned if count == 1. For any other value of count the plural will be
     * returned.
     * <p>
     * If the optional parameter locale is specified, the word will be
     * pluralized as a word of that language. By default, this parameter is set
     * to "en-US". You must define your own inflection rules for languages other
     * than English.
     * <pre>{@code
     * StringUtils.pluralize("post") == "posts"
     * StringUtils.pluralize("octopus") == "octopi"
     * StringUtils.pluralize("sheep") == "sheep"
     * StringUtils.pluralize("words") == "words"
     * StringUtils.pluralize("the blue mailman") == "the blue mailmen"
     * StringUtils.pluralize("CamelOctopus") == "CamelOctopi"
     * StringUtils.pluralize("apple", 1) == "apple"
     * StringUtils.pluralize("apple", 2) == "apples"
     * StringUtils.pluralize("papel", "pt-BR") == "papéis"
     * StringUtils.pluralize("papel", 1, "pt-BR") == "papel"
     * }</pre>
     *
     * @param word   The word to be pluralized.
     * @param count  If the value of this parameter is equal to 1 the singular
     *               form will be returned. For any other value the plural form
     *               will be returned.
     * @param locale The language in which the word will be pluralized.
     * @return A new string with the plural form of the word.
     */
    public static String pluralize(String word, Integer count, String locale) {
        if (count != null && count == 1) {
            return word;
        } else {
            return Inflector.pluralize(word, locale);
        }
    }

    /**
     * Tries to find a declared constant with the name specified in the string.
     * It returns null when the name is not in CamelCase or is not initialized.
     * <pre>{@code
     * StringUtils.constantize("com.example.MyClass") == MyClass.class
     * StringUtils.constantize("com.example.blargle") == null
     * }</pre>
     *
     * @param camelizedWord The camel case term that references the class.
     * @return A Class as specified in the string.
     * @see tech.arauk.ark.activesupport.Inflector#safeConstantize(String)
     */
    public static Class safeConstantize(String camelizedWord) {
        return Inflector.safeConstantize(camelizedWord);
    }

    /**
     * The reverse of {@link #pluralize(String)}, returns the singular form of a
     * word in a string.
     * <p>
     * If the optional parameter locale is specified, the word will be
     * singularized as a word of that language. By default, this parameter is
     * set to "en-US". You must define your own inflection rules for languages
     * other than English.
     * <pre>{@code
     * StringUtils.singularize("posts") == "post"
     * StringUtils.singularize("octopi") == "octopus"
     * StringUtils.singularize("sheep") == "sheep"
     * StringUtils.singularize("word") == "word"
     * StringUtils.singularize("the blue mailmen") == "the blue mailman"
     * StringUtils.singularize("CamelOctopi") == "CamelOctopus"
     * StringUtils.singularize("papéis", "pt-BR") == "papel"
     * }</pre>
     *
     * @param word The word to be singularized.
     * @return A new string with the singular form of the word.
     */
    public static String singularize(String word) {
        return singularize(word, "en-US");
    }

    /**
     * The reverse of {@link #pluralize(String, String)}, returns the singular
     * form of a word in a string.
     * <p>
     * If the optional parameter locale is specified, the word will be
     * singularized as a word of that language. By default, this parameter is
     * set to "en-US". You must define your own inflection rules for languages
     * other than English.
     * <pre>{@code
     * StringUtils.singularize("posts") == "post"
     * StringUtils.singularize("octopi") == "octopus"
     * StringUtils.singularize("sheep") == "sheep"
     * StringUtils.singularize("word") == "word"
     * StringUtils.singularize("the blue mailmen") == "the blue mailman"
     * StringUtils.singularize("CamelOctopi") == "CamelOctopus"
     * StringUtils.singularize("papéis", "pt-BR") == "papel"
     * }</pre>
     *
     * @param word   The word to be singularized.
     * @param locale The language in which the word will be singularized.
     * @return A new string with the singular form of the word.
     */
    public static String singularize(String word, String locale) {
        return Inflector.singularize(word, locale);
    }

    /**
     * Creates the name of a table like Ark does for models to table names.
     * This method uses the {@link #pluralize(String)} method on the last word
     * in the string.
     * <pre>{@code
     * StringUtils.tableize("RawScaledScorer") == "raw_scaled_scorers"
     * StringUtils.tableize("egg_and_ham") == "egg_and_hams"
     * StringUtils.tableize("fancyCategory") == "fancy_categories"
     * }</pre>
     *
     * @param className The name of the class from which you want the table
     *                  name.
     * @return A new string with the table name.
     */
    public static String tableize(String className) {
        return Inflector.tableize(className);
    }

    /**
     * Capitalizes all the words and replaces some characters in the string to
     * create a nicer looking title. titlecase is meant for creating pretty
     * output. It is not used in the StringUtils internals.
     * <p>
     * titlecase is also aliased as {@link #titleize(String)}.
     * <pre>{@code
     * StringUtils.titlecase("man from the boondocks") == "Man From The Boondocks"
     * StringUtils.titlecase("x-men: the last stand") == "X Men: The Last Stand"
     * }</pre>
     *
     * @param word The word to be titlecased.
     * @return A new string in the format of a nicer looking title.
     */
    public static String titlecase(String word) {
        return Inflector.titlecase(word);
    }

    /**
     * Capitalizes all the words and replaces some characters in the string to
     * create a nicer looking title. titleize is meant for creating pretty
     * output. It is not used in the StringUtils internals.
     * <p>
     * titleize is also aliased as {@link #titlecase(String)}.
     * <pre>{@code
     * StringUtils.titleize("man from the boondocks") == "Man From The Boondocks"
     * StringUtils.titleize("x-men: the last stand") == "X Men: The Last Stand"
     * }</pre>
     *
     * @param word The word to be titleized.
     * @return A new string in the format of a nicer looking title.
     */
    public static String titleize(String word) {
        return Inflector.titleize(word);
    }

    /**
     * The reverse of {@link #camelize(String)}. Makes an underscored, lowercase
     * form from the expression in the string.
     * <p>
     * underscore will also change "." to "/" to convert packages to paths.
     * <pre>{@code
     * StringUtils.underscore("ActiveRecord") == "active_record"
     * StringUtils.underscore("ark.activerecord.Errors") == "ark/activerecord/errors"
     * }</pre>
     *
     * @param camelizedWord The camel case word to be underscored.
     * @return A new string in the underscored format.
     */
    public static String underscore(String camelizedWord) {
        return Inflector.underscore(camelizedWord);
    }
}
