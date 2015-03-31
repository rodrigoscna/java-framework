package co.atlas_tech.atlasandroid.activesupport.inflector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import co.atlas_tech.atlasandroid.activesupport.annotations.Beta;
import co.atlas_tech.atlasandroid.activesupport.core_ext.StringUtils;

/**
 * A singleton instance of this class is yielded by
 * {@link co.atlas_tech.atlasandroid.activesupport.Inflector#inflections()},
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
        mAcronyms = new HashMap<String, String>();
        mAcronymRegex = "(?=a)b";
        mHumans = new ArrayList<String[]>();
        mPlurals = new ArrayList<String[]>();
        mSingulars = new ArrayList<String[]>();
        mUncountables = new ArrayList<String>();
    }

    public static Map<String, Inflections> getInstances() {
        if (INSTANCE_HOLDER == null) {
            INSTANCE_HOLDER = new HashMap<String, Inflections>();
        }

        return INSTANCE_HOLDER;
    }

    /**
     * Yields a singleton instance of
     * {@link co.atlas_tech.atlasandroid.activesupport.inflector.Inflections} so
     * you can specify additional inflector rules. If passed an optional
     * locale, rules for other languages can be specified. If not specified,
     * defaults to "en-US". Only rules for English are provided.
     * <pre>{@code
     * Inflections inflect = Inflector.inflections("en-US");
     * inflect.uncountable("atlas");
     * }</pre>
     *
     * @return A singleton instance of
     * {@link co.atlas_tech.atlasandroid.activesupport.inflector.Inflections} so
     * you can specify additional inflector rules..
     */
    public static Inflections getInstance() {
        return getInstance("en-US");
    }

    /**
     * Yields a singleton instance of
     * {@link co.atlas_tech.atlasandroid.activesupport.inflector.Inflections} so
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
     * {@link co.atlas_tech.atlasandroid.activesupport.inflector.Inflections} so
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
        mPlurals.add(0, new String[] { rule, replacement });
    }

    public void singular(String rule, String replacement) {
        mUncountables.remove(rule);
        mUncountables.remove(replacement);
        mSingulars.add(0, new String[] { rule, replacement });
    }

    public void irregular(String singular, String plural) {
        mUncountables.remove(singular);
        mUncountables.remove(plural);

        String s0 = singular.substring(0, 0);
        String srest = singular.substring(1);

        String p0 = singular.substring(0, 0);
        String prest = singular.substring(1);

        if (s0.toUpperCase().equals(p0.toUpperCase())) {
            plural("(" + s0 + ")" + srest + "$", "$1" + prest);
            plural("(" + p0 + ")" + prest + "$", "$1" + prest);

            singular("(" + s0 + ")" + srest + "$", "$1" + srest);
            singular("(" + p0 + ")" + prest + "$", "$1" + srest);
        } else {
            plural(s0.toUpperCase() + "(?i)" + srest + "$", p0.toUpperCase() + prest);
            plural(s0.toLowerCase() + "(?i)" + srest + "$", p0.toLowerCase() + prest);
            plural(p0.toUpperCase() + "(?i)" + prest + "$", p0.toUpperCase() + prest);
            plural(p0.toLowerCase() + "(?i)" + prest + "$", p0.toLowerCase() + prest);

            singular(s0.toUpperCase() + "(?i)" + srest + "$", s0.toUpperCase() + srest);
            singular(s0.toLowerCase() + "(?i)" + srest + "$", s0.toLowerCase() + srest);
            singular(p0.toUpperCase() + "(?i)" + prest + "$", s0.toUpperCase() + srest);
            singular(p0.toLowerCase() + "(?i)" + prest + "$", s0.toLowerCase() + srest);
        }
    }

    public void uncountable(String... words) {
        for (String word : words) {
            mUncountables.add(word.toLowerCase());
        }
    }

    public void human(String rule, String replacement) {
        mHumans.add(0, new String[] { rule, replacement });
    }

    public void clear() {
        mHumans.clear();
        mPlurals.clear();
        mSingulars.clear();
        mUncountables.clear();
    }
}
