package tech.arauk.ark.activesupport.inflector;

import java.util.regex.Pattern;

import tech.arauk.ark.activesupport.Inflector;
import tech.arauk.ark.activesupport.annotations.Beta;

/**
 * Default set of inflections for the English (US) language.
 *
 * @author Rodrigo Scomasson do Nascimento <rodrigo.sc.na@gmail.com>
 */
@Beta
public class DefaultInflections {
    public static Inflections getDefaultInflections() {
        return Inflector.inflections("en-US");
    }

    public static void initializeDefaultInflections() {
        Inflections inflect = getDefaultInflections();

        inflect.plural("$", "s");
        inflect.plural("s$", "s", Pattern.CASE_INSENSITIVE);
        inflect.plural("^(ax|test)is$", "$1es", Pattern.CASE_INSENSITIVE);
        inflect.plural("(octop|vir)us$", "$1i", Pattern.CASE_INSENSITIVE);
        inflect.plural("(octop|vir)i$", "$1i", Pattern.CASE_INSENSITIVE);
        inflect.plural("(alias|status)$", "$1es", Pattern.CASE_INSENSITIVE);
        inflect.plural("(bu)s$", "$1ses", Pattern.CASE_INSENSITIVE);
        inflect.plural("(buffal|tomat)o$", "$1oes", Pattern.CASE_INSENSITIVE);
        inflect.plural("([ti])um$", "$1a", Pattern.CASE_INSENSITIVE);
        inflect.plural("([ti])a$", "$1a", Pattern.CASE_INSENSITIVE);
        inflect.plural("sis$", "ses", Pattern.CASE_INSENSITIVE);
        inflect.plural("(?:([^f])fe|([lr])f)$", "$1$2ves", Pattern.CASE_INSENSITIVE);
        inflect.plural("(hive)$", "$1s", Pattern.CASE_INSENSITIVE);
        inflect.plural("([^aeiouy]|qu)y$", "$1ies", Pattern.CASE_INSENSITIVE);
        inflect.plural("(x|ch|ss|sh)$", "$1es", Pattern.CASE_INSENSITIVE);
        inflect.plural("(matr|vert|ind)(?:ix|ex)$", "$1ices", Pattern.CASE_INSENSITIVE);
        inflect.plural("^(m|l)ouse$", "$1ice", Pattern.CASE_INSENSITIVE);
        inflect.plural("^(m|l)ice$", "$1ice", Pattern.CASE_INSENSITIVE);
        inflect.plural("^(ox)$", "$1en", Pattern.CASE_INSENSITIVE);
        inflect.plural("^(oxen)$", "$1", Pattern.CASE_INSENSITIVE);
        inflect.plural("(quiz)$", "$1zes", Pattern.CASE_INSENSITIVE);

        inflect.singular("s$", "", Pattern.CASE_INSENSITIVE);
        inflect.singular("(ss)$", "$1", Pattern.CASE_INSENSITIVE);
        inflect.singular("(n)ews$", "$1ews", Pattern.CASE_INSENSITIVE);
        inflect.singular("([ti])a$", "$1um", Pattern.CASE_INSENSITIVE);
        inflect.singular("((a)naly|(b)a|(d)iagno|(p)arenthe|(p)rogno|(s)ynop|(t)he)(sis|ses)$", "$1sis", Pattern.CASE_INSENSITIVE);
        inflect.singular("(^analy)(sis|ses)$", "$1sis", Pattern.CASE_INSENSITIVE);
        inflect.singular("([^f])ves$", "$1fe", Pattern.CASE_INSENSITIVE);
        inflect.singular("(hive)s$", "$1", Pattern.CASE_INSENSITIVE);
        inflect.singular("(tive)s$", "$1", Pattern.CASE_INSENSITIVE);
        inflect.singular("([lr])ves$", "$1f", Pattern.CASE_INSENSITIVE);
        inflect.singular("([^aeiouy]|qu)ies$", "$1y", Pattern.CASE_INSENSITIVE);
        inflect.singular("(s)eries$", "$1eries", Pattern.CASE_INSENSITIVE);
        inflect.singular("(m)ovies$", "$1ovie", Pattern.CASE_INSENSITIVE);
        inflect.singular("(x|ch|ss|sh)es$", "$1", Pattern.CASE_INSENSITIVE);
        inflect.singular("^(m|l)ice$", "$1ouse", Pattern.CASE_INSENSITIVE);
        inflect.singular("(bus)(es)?$", "$1", Pattern.CASE_INSENSITIVE);
        inflect.singular("(o)es$", "$1", Pattern.CASE_INSENSITIVE);
        inflect.singular("(shoe)s$", "$1", Pattern.CASE_INSENSITIVE);
        inflect.singular("(cris|test)(is|es)$", "$1is", Pattern.CASE_INSENSITIVE);
        inflect.singular("^(a)x[ie]s$", "$1xis", Pattern.CASE_INSENSITIVE);
        inflect.singular("(octop|vir)(us|i)$", "$1us", Pattern.CASE_INSENSITIVE);
        inflect.singular("(alias|status)(es)?$", "$1", Pattern.CASE_INSENSITIVE);
        inflect.singular("^(ox)en", "$1", Pattern.CASE_INSENSITIVE);
        inflect.singular("(vert|ind)ices$", "$1ex", Pattern.CASE_INSENSITIVE);
        inflect.singular("(matr)ices$", "$1ix", Pattern.CASE_INSENSITIVE);
        inflect.singular("(quiz)zes$", "$1", Pattern.CASE_INSENSITIVE);
        inflect.singular("(database)s$", "$1", Pattern.CASE_INSENSITIVE);

        inflect.irregular("person", "people");
        inflect.irregular("man", "men");
        inflect.irregular("child", "children");
        inflect.irregular("sex", "sexes");
        inflect.irregular("move", "moves");
        inflect.irregular("zombie", "zombies");

        inflect.uncountable("equipment", "information", "rice", "money", "species", "series", "fish", "sheep", "jeans", "police");
    }
}
