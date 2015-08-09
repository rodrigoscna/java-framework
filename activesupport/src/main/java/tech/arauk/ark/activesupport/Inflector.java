package tech.arauk.ark.activesupport;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tech.arauk.ark.activesupport.annotations.Beta;
import tech.arauk.ark.activesupport.core_ext.StringUtils;
import tech.arauk.ark.activesupport.inflector.Inflections;

/**
 * The Inflector transforms words from singular to plural, class names to table
 * names, modularized class names to ones without, and class names to foreign
 * keys. The default inflections for pluralization, singularization, and
 * uncountable words are kept in
 * {@link tech.arauk.ark.activesupport.inflector.DefaultInflections#initializeDefaultInflections() initializeDefaultInflections}.
 * <p/>
 * The Ark core team has stated patches for the inflections library will not
 * be accepted in order to avoid breaking legacy applications which may be
 * relying on errant inflections. If you discover an incorrect inflection and
 * require it for your application or wish to define rules for languages other
 * than English, please correct or add them yourself (explained below).
 *
 * @author Rodrigo Scomasson do Nascimento <rodrigo.sc.na@gmail.com>
 */
@Beta
public class Inflector {
    public static Inflections inflections() {
        return inflections("en-US");
    }

    public static Inflections inflections(String locale) {
        return Inflections.getInstance(locale);
    }

    /**
     * Converts strings to UpperCamelCase. If the uppercaseFirstLetter parameter
     * is set to false, then produces lowerCamelCase.
     * <p/>
     * Also converts "/" to "." which is useful for converting paths to
     * packages.
     * <pre>{@code
     * StringUtils.camelize("active_model") == "ActiveModel"
     * StringUtils.camelize("active_model", false) == "activeModel"
     * StringUtils.camelize("ark/activemodel/errors") == "ark.activemodel.Errors"
     * StringUtils.camelize("ark/activemodel/errors", false) == "ark.activemodel.errors"
     * }</pre>
     * As a rule of thumb you can think of camelize as the inverse of
     * {@link #underscore(String)}, though there are cases where that does not
     * hold:
     * <pre>{@code
     * StringUtils.camelize(StringUtils.underscore("SSLError")) == "SslError"
     * }</pre>
     *
     * @param term The string to be converted.
     * @return A new camel case string.
     */
    public static String camelize(String term) {
        return camelize(term, true);
    }

    /**
     * Converts strings to UpperCamelCase. If the uppercaseFirstLetter parameter
     * is set to false, then produces lowerCamelCase.
     * <p/>
     * Also converts "/" to "." which is useful for converting paths to
     * packages.
     * <pre>{@code
     * StringUtils.camelize("active_model") == "ActiveModel"
     * StringUtils.camelize("active_model", false) == "activeModel"
     * StringUtils.camelize("ark/activemodel/errors") == "ark.activemodel.Errors"
     * StringUtils.camelize("ark/activemodel/errors", false) == "ark.activemodel.errors"
     * }</pre>
     * As a rule of thumb you can think of camelize as the inverse of
     * {@link #underscore(String)}, though there are cases where that does not
     * hold:
     * <pre>{@code
     * StringUtils.camelize(StringUtils.underscore("SSLError")) == "SslError"
     * }</pre>
     *
     * @param term                 The string to be converted.
     * @param uppercaseFirstLetter Indicates whether the first letter should be
     *                             upper or lower cased.
     * @return A new camel case string.
     */
    public static String camelize(String term, boolean uppercaseFirstLetter) {
        Map<String, String> acronyms = inflections().getAcronyms();

        String camelizedString = term;

        Pattern pattern;
        Matcher matcher;
        if (uppercaseFirstLetter) {
            pattern = Pattern.compile("^[a-z\\d]*");
            matcher = pattern.matcher(camelizedString);
            if (matcher.find()) {
                String match = matcher.group();
                camelizedString = camelizedString.replaceFirst(pattern.pattern(), acronyms.containsKey(match) ? acronyms.get(match) : capitalize(match));
            }
        } else {
            pattern = Pattern.compile("^(?:" + inflections().getAcronymRegex() + "(?=\\b|[A-Z_])|\\w)");
            matcher = pattern.matcher(camelizedString);
            if (matcher.find()) {
                String match = matcher.group();
                camelizedString = camelizedString.replaceFirst(pattern.pattern(), match.toLowerCase());
            }
        }

        pattern = Pattern.compile("(?:_|(\\/))([a-z\\d]*)", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(camelizedString);
        while (matcher.find()) {
            String match1 = matcher.group(1) != null ? matcher.group(1) : "";
            String match2 = matcher.group(2) != null ? matcher.group(2) : "";
            camelizedString = camelizedString.replaceFirst(matcher.group(), match1 + (acronyms.containsKey(match2) ? acronyms.get(match2) : capitalize(match2)));
        }

        camelizedString = camelizedString.replaceAll("\\/", "\\.");

        return camelizedString;
    }

    public static String capitalize(String word) {
        StringBuilder capitalized = new StringBuilder();
        if (word != null && word.length() > 0) {
            capitalized.append(word.substring(0, 1).toUpperCase());
            if (word.length() > 1) {
                capitalized.append(word.substring(1).toLowerCase());
            }
        }
        return capitalized.toString();
    }

    /**
     * Creates a class name from a plural table name like Rails does for table
     * names to models. Note that this returns a string and not a Class (To
     * convert to an actual class follow classify with
     * {@link #constantize(String)}.)
     * <pre>{@code
     * Inflector.classify("egg_and_hams") == "EggAndHam"
     * Inflector.classify("posts") == "Post"
     * }</pre>
     * Singular names are not handled correctly:
     * <pre>{@code
     * Inflector.classify("calculus") == "Calculu"
     * }</pre>
     *
     * @param tableName The plural table name to be converted.
     * @return A new class name string.
     */
    public static String classify(String tableName) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Tries to find a constant with the name specified in the argument string.
     * <pre>{@code
     * Inflector.constantize("com.example.MyClass") == MyClass.class
     * }</pre>
     * The name is assumed to be composed of the full package name plus the
     * class name.
     *
     * @param camelizedWord The camel case term that references the class.
     * @return A Class as specified in the string.
     * @throws IllegalArgumentException Is thrown when the name is not in
     *                                  CamelCase or the constant is unknown.
     */
    public static Class constantize(String camelizedWord) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Replaces underscores with dashes in the string.
     * <pre>{@code
     * Inflector.dasherize("puni_puni") == "puni-puni"
     * }</pre>
     *
     * @param underscoredWord The underscored word to be converted.
     * @return A new string with dashes instead of underscores.
     */
    public static String dasherize(String underscoredWord) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Removes the rightmost segment from the constant expression in the string.
     * <pre>{@code
     * Inflector.deconstantize("com.example.HTTP") == "com.example"
     * Inflector.deconstantize("String") == ""
     * Inflector.deconstantize("") == ""
     * }</pre>
     *
     * @param path The path from which to obtain the modules.
     * @return A new string with the retrieved modules.
     * @see #demodulize(String)
     */
    public static String deconstantize(String path) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Removes the module part from the expression in the string.
     * <pre>{@code
     * Inflector.demodulize("com.example.Inflections") == "Inflections"
     * Inflector.demodulize("String") == "String"
     * Inflector.demodulize("") == ""
     * }</pre>
     *
     * @param path The path from which to obtain the constant.
     * @return A new string with the retrieved constant.
     * @see #deconstantize(String)
     */
    public static String demodulize(String path) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Creates a foreign key name from a class name.
     * <p/>
     * The argument separateClassNameAndIdWithUnderscore sets whether the method
     * should put "_" between the name and "id".
     * <pre>{@code
     * Inflector.foreignKey("Message") == "message_id"
     * Inflector.foreignKey("Message", false) == "messageid"
     * Inflector.foreignKey("com.example.Post") == "post_id"
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
     * <p/>
     * The argument separateClassNameAndIdWithUnderscore sets whether the method
     * should put "_" between the name and "id".
     * <pre>{@code
     * Inflector.foreignKey("Message") == "message_id"
     * Inflector.foreignKey("Message", false) == "messageid"
     * Inflector.foreignKey("com.example.Post") == "post_id"
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
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Tweaks an attribute name for display to end users.
     * <p/>
     * Specifically, performs these transformations:
     * <ul>
     * <li>Applies human inflection rules to the argument.</li>
     * <li>Deletes leading underscores, if any.</li>
     * <li>Removes a "_id" suffix if present.</li>
     * <li>Replaces underscores with spaces, if any.</li>
     * <li>Downcases all words except acronyms.</li>
     * <li>Capitalizes the first word.</li>
     * </ul>
     * <p/>
     * The capitalization of the first word can be turned off by setting the
     * capitalizeFirstWord option to false (default is true).
     * <pre>{@code
     * Inflector.humanize("employee_salary") == "Employee salary"
     * Inflector.humanize("author_id") == "Author"
     * Inflector.humanize("author_id", false) == "author"
     * Inflector.humanize("_id") == "id"
     * }</pre>
     * If "SSL" was defined to be an acronym:
     * <pre>{@code
     * Inflector.humanize("ssl_error") == "SSL error"
     * }</pre>
     *
     * @param lowerCaseAndUnderscoredWord The word to be humanized.
     * @return The humanized version of the word.
     */
    public static String humanize(String lowerCaseAndUnderscoredWord) {
        return humanize(lowerCaseAndUnderscoredWord, true);
    }

    /**
     * Tweaks an attribute name for display to end users.
     * <p/>
     * Specifically, performs these transformations:
     * <ul>
     * <li>Applies human inflection rules to the argument.</li>
     * <li>Deletes leading underscores, if any.</li>
     * <li>Removes a "_id" suffix if present.</li>
     * <li>Replaces underscores with spaces, if any.</li>
     * <li>Downcases all words except acronyms.</li>
     * <li>Capitalizes the first word.</li>
     * </ul>
     * <p/>
     * The capitalization of the first word can be turned off by setting the
     * capitalizeFirstWord option to false (default is true).
     * <pre>{@code
     * Inflector.humanize("employee_salary") == "Employee salary"
     * Inflector.humanize("author_id") == "Author"
     * Inflector.humanize("author_id", false) == "author"
     * Inflector.humanize("_id") == "id"
     * }</pre>
     * If "SSL" was defined to be an acronym:
     * <pre>{@code
     * Inflector.humanize("ssl_error") == "SSL error"
     * }</pre>
     *
     * @param lowerCaseAndUnderscoredWord The word to be humanized.
     * @param capitalizeFirstWord         Indicates whether the first word
     *                                    should be capitalized.
     * @return The humanized version of the word.
     */
    public static String humanize(String lowerCaseAndUnderscoredWord, boolean capitalizeFirstWord) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Returns the suffix that should be added to a number to denote the
     * position in an ordered sequence such as 1st, 2nd, 3rd, 4th.
     * <pre>{@code
     * Inflector.ordinal(1) == "st"
     * Inflector.ordinal(2) == "nd"
     * Inflector.ordinal(1002) == "nd"
     * Inflector.ordinal(1003) == "rd"
     * Inflector.ordinal(-11) == "th"
     * Inflector.ordinal(-1021) == "st"
     * }</pre>
     *
     * @param number The number to be converted.
     * @return The ordinal suffix for the number.
     */
    public static String ordinal(String number) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Returns the suffix that should be added to a number to denote the
     * position in an ordered sequence such as 1st, 2nd, 3rd, 4th.
     * <pre>{@code
     * Inflector.ordinal(1) == "st"
     * Inflector.ordinal(2) == "nd"
     * Inflector.ordinal(1002) == "nd"
     * Inflector.ordinal(1003) == "rd"
     * Inflector.ordinal(-11) == "th"
     * Inflector.ordinal(-1021) == "st"
     * }</pre>
     *
     * @param number The number to be converted.
     * @return The ordinal suffix for the number.
     */
    public static String ordinal(Number number) {
        return ordinal(String.valueOf(number));
    }

    /**
     * Turns a number into an ordinal string used to denote the position in an
     * ordered sequence such as 1st, 2nd, 3rd, 4th.
     * <pre>{@code
     * Inflector.ordinalize(1) == "1st"
     * Inflector.ordinalize(2) == "2nd"
     * Inflector.ordinalize(1002) == "1002nd"
     * Inflector.ordinalize(1003) == "1003rd"
     * Inflector.ordinalize(-11) == "-11th"
     * Inflector.ordinalize(-1021) == "-1021st"
     * }</pre>
     *
     * @param number The number to be converted.
     * @return The number into an ordinal string.
     */
    public static String ordinalize(String number) {
        return number + ordinal(number);
    }

    /**
     * Turns a number into an ordinal string used to denote the position in an
     * ordered sequence such as 1st, 2nd, 3rd, 4th.
     * <pre>{@code
     * Inflector.ordinalize(1) == "1st"
     * Inflector.ordinalize(2) == "2nd"
     * Inflector.ordinalize(1002) == "1002nd"
     * Inflector.ordinalize(1003) == "1003rd"
     * Inflector.ordinalize(-11) == "-11th"
     * Inflector.ordinalize(-1021) == "-1021st"
     * }</pre>
     *
     * @param number The number to be converted.
     * @return The number into an ordinal string.
     */
    public static String ordinalize(Number number) {
        return ordinalize(String.valueOf(number));
    }

    /**
     * Replaces special characters in a string so that it may be used as part of
     * a "pretty" URL.
     * <pre>{@code
     * Inflector.parameterize("Donald E. Knuth") == "donald-e-knuth"
     * Inflector.parameterize("^trés|Jolie-- ") == "tres-jolie"
     * }</pre>
     *
     * @param string The string to be parameterized.
     * @return A new string without special characters.
     */
    public static String parameterize(String string, String separator) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Replaces special characters in a string so that it may be used as part of
     * a "pretty" URL.
     * <pre>{@code
     * Inflector.parameterize("Donald E. Knuth") == "donald-e-knuth"
     * Inflector.parameterize("^trés|Jolie-- ") == "tres-jolie"
     * }</pre>
     *
     * @param string The string to be parameterized.
     * @return A new string without special characters.
     */
    public static String parameterize(String string) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Returns the plural form of the word in the string.
     * <p/>
     * If passed an optional locale parameter, the word will be pluralized using
     * rules defined for that language. By default,this parameter is set to
     * "en-US".
     * <pre>{@code
     * Inflector.pluralize("post") == "posts"
     * Inflector.pluralize("octopus") == "octopi"
     * Inflector.pluralize("sheep") == "sheep"
     * Inflector.pluralize("words") == "words"
     * Inflector.pluralize("CamelOctopus") == "CamelOctopi"
     * Inflector.pluralize("papel", "pt-BR") == "papéis"
     * }</pre>
     *
     * @param word The word to be pluralized.
     * @return A new string with the plural form of the word.
     */
    public static String pluralize(String word) {
        return pluralize(word, "en-US");
    }

    /**
     * Returns the plural form of the word in the string.
     * <p/>
     * If passed an optional locale parameter, the word will be pluralized using
     * rules defined for that language. By default,this parameter is set to
     * "en-US".
     * <pre>{@code
     * Inflector.pluralize("post") == "posts"
     * Inflector.pluralize("octopus") == "octopi"
     * Inflector.pluralize("sheep") == "sheep"
     * Inflector.pluralize("words") == "words"
     * Inflector.pluralize("CamelOctopus") == "CamelOctopi"
     * Inflector.pluralize("papel", "pt-BR") == "papéis"
     * }</pre>
     *
     * @param word   The word to be pluralized.
     * @param locale The language in which the word will be pluralized.
     * @return A new string with the plural form of the word.
     */
    public static String pluralize(String word, String locale) {
        return applyInflections(word, inflections(locale).getPlurals());
    }

    /**
     * Tries to find a constant with the name specified in the argument string.
     * <pre>{@code
     * Inflector.constantize("com.example.MyClass") == MyClass.class
     * }</pre>
     * The name is assumed to be composed of the full package name plus the
     * class name.
     * <p/>
     * null is returned when the name is not in CamelCase or the constant (or
     * part of it) is unknown.
     * <pre>{@code
     * Inflector.safeConstantize("com.example.blargle") == null
     * Inflector.safeConstantize("com.example.UnknownModule") == null
     * Inflector.safeConstantize("unknown.package.FooBar") == null
     * }</pre>
     *
     * @param camelizedWord The camel case term that references the class.
     * @return A Class as specified in the string or null if the constant is
     * unknown.
     */
    public static Class safeConstantize(String camelizedWord) {
        try {
            return constantize(camelizedWord);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * The reverse of {@link #pluralize(String)}, returns the singular form of a
     * word in a string.
     * <p/>
     * If passed an optional locale parameter, the word will be singularized
     * using rules defined for that language. By default, this parameter is set
     * to "en-US".
     * <pre>{@code
     * Inflector.singularize("posts") == "post"
     * Inflector.singularize("octopi") == "octopus"
     * Inflector.singularize("sheep") == "sheep"
     * Inflector.singularize("word") == "word"
     * Inflector.singularize("CamelOctopi") == "CamelOctopus"
     * Inflector.singularize("papéis", "pt-BR") == "papel"
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
     * <p/>
     * If passed an optional locale parameter, the word will be singularized
     * using rules defined for that language. By default, this parameter is set
     * to "en-US".
     * <pre>{@code
     * Inflector.singularize("posts") == "post"
     * Inflector.singularize("octopi") == "octopus"
     * Inflector.singularize("sheep") == "sheep"
     * Inflector.singularize("word") == "word"
     * Inflector.singularize("CamelOctopi") == "CamelOctopus"
     * Inflector.singularize("papéis", "pt-BR") == "papel"
     * }</pre>
     *
     * @param word   The word to be singularized.
     * @param locale The language in which the word will be singularized.
     * @return A new string with the singular form of the word.
     */
    public static String singularize(String word, String locale) {
        return applyInflections(word, inflections(locale).getSingulars());
    }

    /**
     * Creates the name of a table like Ark does for models to table names.
     * This method uses the {@link #pluralize(String)} method on the last word
     * in the string.
     * <pre>{@code
     * Inflector.tableize("RawScaledScorer") == "raw_scaled_scorers"
     * Inflector.tableize("egg_and_ham") == "egg_and_hams"
     * Inflector.tableize("fancyCategory") == "fancy_categories"
     * }</pre>
     *
     * @param className The name of the class from which you want the table
     *                  name.
     * @return A new string with the table name.
     */
    public static String tableize(String className) {
        String tableizedString = className;
        tableizedString = underscore(tableizedString);
        tableizedString = pluralize(tableizedString);
        return tableizedString;
    }

    /**
     * Capitalizes all the words and replaces some characters in the string to
     * create a nicer looking title. titlecase is meant for creating pretty
     * output. It is not used in the Inflector internals.
     * <p/>
     * titlecase is also aliased as {@link #titleize(String)}.
     * <pre>{@code
     * Inflector.titleize("man from the boondocks") == "Man From The Boondocks"
     * Inflector.titleize("x-men: the last stand") == "X Men: The Last Stand"
     * Inflector.titleize("TheManWithoutAPast") == "The Man Without A Past"
     * Inflector.titleize("raiders_of_the_lost_ark") == "Raiders Of The Lost Ark"
     * }</pre>
     *
     * @param word The word to be titlecased.
     * @return A new string in the format of a nicer looking title.
     */
    public static String titlecase(String word) {
        return titleize(word);
    }

    /**
     * Capitalizes all the words and replaces some characters in the string to
     * create a nicer looking title. titleize is meant for creating pretty
     * output. It is not used in the Inflector internals.
     * <p/>
     * titleize is also aliased as {@link #titlecase(String)}.
     * <pre>{@code
     * Inflector.titleize("man from the boondocks") == "Man From The Boondocks"
     * Inflector.titleize("x-men: the last stand") == "X Men: The Last Stand"
     * Inflector.titleize("TheManWithoutAPast") == "The Man Without A Past"
     * Inflector.titleize("raiders_of_the_lost_ark") == "Raiders Of The Lost Ark"
     * }</pre>
     *
     * @param word The word to be titleized.
     * @return A new string in the format of a nicer looking title.
     */
    public static String titleize(String word) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Makes an underscored, lowercase form from the expression in the string.
     * <p/>
     * Changes "." to "/" to convert namespaces to paths.
     * <pre>{@code
     * Inflector.underscore("ActiveModel") == "active_model"
     * Inflector.underscore("ark.activemodel.Errors") == "ark/activemodel/errors"
     * }</pre>
     * <p/>
     * As a rule of thumb you can think of underscore as the inverse of
     * {@link #camelize(String)}, though there are cases where that does not
     * hold:
     * <pre>{@code
     * Inflector.camelize(Inflector.underscore("SSLError")) == "SslError"
     * }</pre>
     *
     * @param camelizedWord The camel case word to be underscored.
     * @return A new string in the underscored format.
     */
    public static String underscore(String camelizedWord) {
        String underscoredString = camelizedWord;
        underscoredString = underscoredString.replaceAll("\\.", "\\/");

        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("(?:(?<=([A-Za-z\\d]))|\\b)(" + inflections().getAcronymRegex() + ")(?=\\b|[^a-z])");
        matcher = pattern.matcher(underscoredString);
        while (matcher.find()) {
            String match1 = matcher.group(1) != null ? matcher.group(1) : "";
            String match2 = matcher.group(2) != null ? matcher.group(2) : "";

            underscoredString = underscoredString.replaceFirst(matcher.group(), (match1 != null) ? match1 + "_" : "" + match2.toLowerCase());
        }

        underscoredString = underscoredString.replaceAll("([A-Z\\d]+)([A-Z][a-z])", "$1_$2");
        underscoredString = underscoredString.replaceAll("([a-z\\d])([A-Z])", "$1_$2");
        underscoredString = underscoredString.replaceAll("-", "_");
        underscoredString = underscoredString.toLowerCase();

        return underscoredString;
    }

    /**
     * Applies inflection rules for {@link #singularize(String)} and
     * {@link #pluralize(String)}.
     * <pre>{@code
     * Inflector.applyInflections("post", inflections.plurals) == "posts"
     * Inflector.applyInflections("posts", inflections.singulars) == "post"
     * }</pre>
     *
     * @param word  The word to be inflected.
     * @param rules The rules to be used for the inflection.
     * @return A new string in the inflected format.
     */
    private static String applyInflections(String word, List<String[]> rules) {
        String result = word;

        if (StringUtils.isEmpty(word) || inflections().getUncountables().contains(result.toLowerCase())) {
            return result;
        } else {
            for (String[] rule : rules) {
                result = result.replaceAll(rule[0], rule[1]);
            }

            return result;
        }
    }
}
