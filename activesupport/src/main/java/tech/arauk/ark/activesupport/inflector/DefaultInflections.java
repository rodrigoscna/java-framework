package tech.arauk.ark.activesupport.inflector;

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
        inflect.plural("s$", "s");
        inflect.plural("^(ax|test)is$", "$1es");
        inflect.plural("(octop|vir)us$", "$1i");
        inflect.plural("(octop|vir)i$", "$1i");
        inflect.plural("(alias|status)$", "$1es");
        inflect.plural("(bu)s$", "$1ses");
        inflect.plural("(buffal|tomat)o$", "$1oes");
        inflect.plural("([ti])um$", "$1a");
        inflect.plural("([ti])a$", "$1a");
        inflect.plural("sis$", "ses");
        inflect.plural("(?:([^f])fe|([lr])f)$", "$1$2ves");
        inflect.plural("(hive)$", "$1s");
        inflect.plural("([^aeiouy]|qu)y$", "$1ies");
        inflect.plural("(x|ch|ss|sh)$", "$1es");
        inflect.plural("(matr|vert|ind)(?:ix|ex)$", "$1ices");
        inflect.plural("^(m|l)ouse$", "$1ice");
        inflect.plural("^(m|l)ice$", "$1ice");
        inflect.plural("^(ox)$", "$1en");
        inflect.plural("^(oxen)$", "$1");
        inflect.plural("(quiz)$", "$1zes");

        inflect.singular("s$", "");
        inflect.singular("(ss)$", "$1");
        inflect.singular("(n)ews$", "$1ews");
        inflect.singular("([ti])a$", "$1um");
        inflect.singular("((a)naly|(b)a|(d)iagno|(p)arenthe|(p)rogno|(s)ynop|(t)he)(sis|ses)$", "$1sis");
        inflect.singular("(^analy)(sis|ses)$", "$1sis");
        inflect.singular("([^f])ves$", "$1fe");
        inflect.singular("(hive)s$", "$1");
        inflect.singular("(tive)s$", "$1");
        inflect.singular("([lr])ves$", "$1f");
        inflect.singular("([^aeiouy]|qu)ies$", "$1y");
        inflect.singular("(s)eries$", "$1eries");
        inflect.singular("(m)ovies$", "$1ovie");
        inflect.singular("(x|ch|ss|sh)es$", "$1");
        inflect.singular("^(m|l)ice$", "$1ouse");
        inflect.singular("(bus)(es)?$", "$1");
        inflect.singular("(o)es$", "$1");
        inflect.singular("(shoe)s$", "$1");
        inflect.singular("(cris|test)(is|es)$", "$1is");
        inflect.singular("^(a)x[ie]s$", "$1xis");
        inflect.singular("(octop|vir)(us|i)$", "$1us");
        inflect.singular("(alias|status)(es)?$", "$1");
        inflect.singular("^(ox)en", "$1");
        inflect.singular("(vert|ind)ices$", "$1ex");
        inflect.singular("(matr)ices$", "$1ix");
        inflect.singular("(quiz)zes$", "$1");
        inflect.singular("(database)s$", "$1");

        inflect.irregular("person", "people");
        inflect.irregular("man", "men");
        inflect.irregular("child", "children");
        inflect.irregular("sex", "sexes");
        inflect.irregular("move", "moves");
        inflect.irregular("zombie", "zombies");

        inflect.uncountable("equipment", "information", "rice", "money", "species", "series", "fish", "sheep", "jeans", "police");
    }
}
