package tech.arauk.ark.activesupport.inflector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tech.arauk.ark.activesupport.annotations.Beta;
import tech.arauk.ark.activesupport.core_ext.StringUtils;

/**
 * A singleton instance of this class is yielded by
 * {@link tech.arauk.ark.activesupport.Inflector#inflections()},
 * which can then be used to specify additional inflection rules. If passed an
 * optional locale, rules for other languages can be specified. The
 * default locale is "en-US". Only rules for English are provided.
 * <pre>{@code
 * Inflections inflect = Inflector.inflections("en-US");
 * inflect.plural("^(ox)$", "$1$2en");
 * inflect.singular("^(ox)en", "$1");
 * inflect.irregular("octopus", "octopi");
 * inflect.uncountable("equipment");
 * }</pre>
 * <p/>
 * New rules are added at the top. So in the example above, the irregular rule
 * for octopus will now be the first of the pluralization and singularization
 * rules that is runs. This guarantees that your rules run before any of the
 * rules that may already have been loaded.
 *
 * @author Rodrigo Scomasson do Nascimento <rodrigo.sc.na@gmail.com>
 */
@Beta
public class Inflections {
    private static Map<String, Inflections> INSTANCE_HOLDER;
    private Map<String, String> mAcronyms;
    private String mAcronymRegex;
    private List<String[]> mHumans;
    private List<String[]> mPlurals;
    private List<String[]> mSingulars;
    private List<String> mUncountables;

    public Inflections() {
        clear();
    }

    public static Map<String, Inflections> getInstances() {
        if (INSTANCE_HOLDER == null) {
            INSTANCE_HOLDER = new HashMap<>();
        }

        return INSTANCE_HOLDER;
    }

    /**
     * Yields a singleton instance of
     * {@link tech.arauk.ark.activesupport.inflector.Inflections} so
     * you can specify additional inflector rules. If passed an optional
     * locale, rules for other languages can be specified. If not specified,
     * defaults to "en-US". Only rules for English are provided.
     * <pre>{@code
     * Inflections inflect = Inflector.inflections("en-US");
     * inflect.uncountable("atlas");
     * }</pre>
     *
     * @return A singleton instance of
     * {@link tech.arauk.ark.activesupport.inflector.Inflections} so
     * you can specify additional inflector rules..
     */
    public static Inflections getInstance() {
        return getInstance("en-US");
    }

    /**
     * Yields a singleton instance of
     * {@link tech.arauk.ark.activesupport.inflector.Inflections} so
     * you can specify additional inflector rules. If passed an optional
     * locale, rules for other languages can be specified. If not specified,
     * defaults to "en-US". Only rules for English are provided.
     * <pre>{@code
     * Inflections inflect = Inflector.inflections("en-US");
     * inflect.uncountable("atlas");
     * }</pre>
     *
     * @param locale The locale in which you want to retrieve the inflector
     *               rules.
     * @return A singleton instance of
     * {@link tech.arauk.ark.activesupport.inflector.Inflections} so
     * you can specify additional inflector rules..
     */
    public static Inflections getInstance(String locale) {
        Map<String, Inflections> instances = getInstances();

        instances.put(locale, instances.containsKey(locale) ? instances.get(locale) : new Inflections());

        return instances.get(locale);
    }

    public Map<String, String> getAcronyms() {
        return mAcronyms;
    }

    public String getAcronymRegex() {
        return mAcronymRegex;
    }

    public List<String[]> getHumans() {
        return mHumans;
    }

    public List<String[]> getPlurals() {
        return mPlurals;
    }

    public List<String[]> getSingulars() {
        return mSingulars;
    }

    public List<String> getUncountables() {
        return mUncountables;
    }

    public void acronym(String word) {
        mAcronyms.put(word.toLowerCase(), word);
        mAcronymRegex = StringUtils.join(mAcronyms.values(), "|");
    }

    public void plural(String rule, String replacement) {
        mUncountables.remove(rule);
        mUncountables.remove(replacement);
        mPlurals.add(0, new String[]{rule, replacement});
    }

    public void singular(String rule, String replacement) {
        mUncountables.remove(rule);
        mUncountables.remove(replacement);
        mSingulars.add(0, new String[]{rule, replacement});
    }

    public void irregular(String singular, String plural) {
        mUncountables.remove(singular);
        mUncountables.remove(plural);

        String s0 = singular.substring(0, 1);
        String sRest = singular.substring(1);

        String p0 = plural.substring(0, 1);
        String pRest = plural.substring(1);

        if (s0.toUpperCase().equals(p0.toUpperCase())) {
            plural("(" + s0 + ")" + sRest + "$", "$1" + pRest);
            plural("(" + p0 + ")" + pRest + "$", "$1" + pRest);

            singular("(" + s0 + ")" + sRest + "$", "$1" + sRest);
            singular("(" + p0 + ")" + pRest + "$", "$1" + sRest);
        } else {
            plural(s0.toUpperCase() + "(?i)" + sRest + "$", p0.toUpperCase() + pRest);
            plural(s0.toLowerCase() + "(?i)" + sRest + "$", p0.toLowerCase() + pRest);
            plural(p0.toUpperCase() + "(?i)" + pRest + "$", p0.toUpperCase() + pRest);
            plural(p0.toLowerCase() + "(?i)" + pRest + "$", p0.toLowerCase() + pRest);

            singular(s0.toUpperCase() + "(?i)" + sRest + "$", s0.toUpperCase() + sRest);
            singular(s0.toLowerCase() + "(?i)" + sRest + "$", s0.toLowerCase() + sRest);
            singular(p0.toUpperCase() + "(?i)" + pRest + "$", s0.toUpperCase() + sRest);
            singular(p0.toLowerCase() + "(?i)" + pRest + "$", s0.toLowerCase() + sRest);
        }
    }

    public void uncountable(String... words) {
        for (String word : words) {
            mUncountables.add(word.toLowerCase());
        }
    }

    public void human(String rule, String replacement) {
        mHumans.add(0, new String[]{rule, replacement});
    }

    public void clear() {
        mAcronyms = new HashMap<>();
        mAcronymRegex = "(?=a)b";
        mHumans = new ArrayList<>();
        mPlurals = new ArrayList<>();
        mSingulars = new ArrayList<>();
        mUncountables = new ArrayList<>();
    }

    public void clear(String scope) {
        if (scope == null) {
            clear();
        } else {
            switch (scope) {
                case "all":
                    clear();
                    break;
                case "acronyms":
                    mAcronyms = new HashMap<>();
                    mAcronymRegex = "(?=a)b";
                    break;
                case "humans":
                    mHumans = new ArrayList<>();
                    break;
                case "plurals":
                    mPlurals = new ArrayList<>();
                    break;
                case "singulars":
                    mSingulars = new ArrayList<>();
                    break;
                case "uncountables":
                    mUncountables = new ArrayList<>();
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }
}
