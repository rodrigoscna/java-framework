package tech.arauk.ark.activesupport.inflector;

import tech.arauk.ark.activesupport.core_ext.StringUtils;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class for transliteration related inflections.
 *
 * @author Rodrigo Scomazzon do Nascimento <rodrigo.sc.na@gmail.com>
 */
public class Transliterator {
    private static final String sDefaultReplacementChar = "?";
    private static final String[][] sDefaultApproximations = new String[][] {
            {"À", "A"},  {"Á", "A"},  {"Â", "A"},  {"Ã", "A"},  {"Ä", "A"},
            {"Å", "A"},  {"Æ", "AE"}, {"Ç", "C"},  {"È", "E"},  {"É", "E"},
            {"Ê", "E"},  {"Ë", "E"},  {"Ì", "I"},  {"Í", "I"},  {"Î", "I"},
            {"Ï", "I"},  {"Ð", "D"},  {"Ñ", "N"},  {"Ò", "O"},  {"Ó", "O"},
            {"Ô", "O"},  {"Õ", "O"},  {"Ö", "O"},  {"×", "x"},  {"Ø", "O"},
            {"Ù", "U"},  {"Ú", "U"},  {"Û", "U"},  {"Ü", "U"},  {"Ý", "Y"},
            {"Þ", "Th"}, {"ß", "ss"}, {"à", "a"},  {"á", "a"},  {"â", "a"},
            {"ã", "a"},  {"ä", "a"},  {"å", "a"},  {"æ", "ae"}, {"ç", "c"},
            {"è", "e"},  {"é", "e"},  {"ê", "e"},  {"ë", "e"},  {"ì", "i"},
            {"í", "i"},  {"î", "i"},  {"ï", "i"},  {"ð", "d"},  {"ñ", "n"},
            {"ò", "o"},  {"ó", "o"},  {"ô", "o"},  {"õ", "o"},  {"ö", "o"},
            {"ø", "o"},  {"ù", "u"},  {"ú", "u"},  {"û", "u"},  {"ü", "u"},
            {"ý", "y"},  {"þ", "th"}, {"ÿ", "y"},  {"Ā", "A"},  {"ā", "a"},
            {"Ă", "A"},  {"ă", "a"},  {"Ą", "A"},  {"ą", "a"},  {"Ć", "C"},
            {"ć", "c"},  {"Ĉ", "C"},  {"ĉ", "c"},  {"Ċ", "C"},  {"ċ", "c"},
            {"Č", "C"},  {"č", "c"},  {"Ď", "D"},  {"ď", "d"},  {"Đ", "D"},
            {"đ", "d"},  {"Ē", "E"},  {"ē", "e"},  {"Ĕ", "E"},  {"ĕ", "e"},
            {"Ė", "E"},  {"ė", "e"},  {"Ę", "E"},  {"ę", "e"},  {"Ě", "E"},
            {"ě", "e"},  {"Ĝ", "G"},  {"ĝ", "g"},  {"Ğ", "G"},  {"ğ", "g"},
            {"Ġ", "G"},  {"ġ", "g"},  {"Ģ", "G"},  {"ģ", "g"},  {"Ĥ", "H"},
            {"ĥ", "h"},  {"Ħ", "H"},  {"ħ", "h"},  {"Ĩ", "I"},  {"ĩ", "i"},
            {"Ī", "I"},  {"ī", "i"},  {"Ĭ", "I"},  {"ĭ", "i"},  {"Į", "I"},
            {"į", "i"},  {"İ", "I"},  {"ı", "i"},  {"Ĳ", "IJ"}, {"ĳ", "ij"},
            {"Ĵ", "J"},  {"ĵ", "j"},  {"Ķ", "K"},  {"ķ", "k"},  {"ĸ", "k"},
            {"Ĺ", "L"},  {"ĺ", "l"},  {"Ļ", "L"},  {"ļ", "l"},  {"Ľ", "L"},
            {"ľ", "l"},  {"Ŀ", "L"},  {"ŀ", "l"},  {"Ł", "L"},  {"ł", "l"},
            {"Ń", "N"},  {"ń", "n"},  {"Ņ", "N"},  {"ņ", "n"},  {"Ň", "N"},
            {"ň", "n"},  {"ŉ", "'n"}, {"Ŋ", "NG"}, {"ŋ", "ng"}, {"Ō", "O"},
            {"ō", "o"},  {"Ŏ", "O"},  {"ŏ", "o"},  {"Ő", "O"},  {"ő", "o"},
            {"Œ", "OE"}, {"œ", "oe"}, {"Ŕ", "R"},  {"ŕ", "r"},  {"Ŗ", "R"},
            {"ŗ", "r"},  {"Ř", "R"},  {"ř", "r"},  {"Ś", "S"},  {"ś", "s"},
            {"Ŝ", "S"},  {"ŝ", "s"},  {"Ş", "S"},  {"ş", "s"},  {"Š", "S"},
            {"š", "s"},  {"Ţ", "T"},  {"ţ", "t"},  {"Ť", "T"},  {"ť", "t"},
            {"Ŧ", "T"},  {"ŧ", "t"},  {"Ũ", "U"},  {"ũ", "u"},  {"Ū", "U"},
            {"ū", "u"},  {"Ŭ", "U"},  {"ŭ", "u"},  {"Ů", "U"},  {"ů", "u"},
            {"Ű", "U"},  {"ű", "u"},  {"Ų", "U"},  {"ų", "u"},  {"Ŵ", "W"},
            {"ŵ", "w"},  {"Ŷ", "Y"},  {"ŷ", "y"},  {"Ÿ", "Y"},  {"Ź", "Z"},
            {"ź", "z"},  {"Ż", "Z"},  {"ż", "z"},  {"Ž", "Z"},  {"ž", "z"}
    };

    public static String transliterate(String string) {
        return transliterate(string, sDefaultReplacementChar);
    }

    public static String transliterate(String string, String replacement) {
        Map<String, String> defaultApproximations = new HashMap<>();
        for (String[] defaultApproximation : sDefaultApproximations) {
            defaultApproximations.put(defaultApproximation[0], defaultApproximation[1]);
        }

        String transliterated = string;

        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("[^\\x00-\\x7f]");
        matcher = pattern.matcher(transliterated);

        while (matcher.find()) {
            String group = matcher.group();
            String groupReplacement;

            if (defaultApproximations.containsKey(group)) {
                groupReplacement = defaultApproximations.get(group);
            } else if (!StringUtils.isEmpty(replacement)) {
                groupReplacement = replacement;
            } else {
                groupReplacement = sDefaultReplacementChar;
            }

            transliterated = transliterated.replaceAll(group, groupReplacement);
        }

        return transliterated;
    }

    public static String parameterize(String string, String separator, boolean preserveCase) {
        String parameterized = string;

        parameterized = transliterate(parameterized);

        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("[^a-z0-9\\-_]+", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(parameterized);

        if (matcher.find()) {
            parameterized = matcher.replaceAll(separator);
        }

        if (!StringUtils.isEmpty(separator)) {
            Pattern duplicateSeparator;
            Pattern leadingTrailingSeparator;

            if ("-".equals(separator)) {
                duplicateSeparator = Pattern.compile("-{2,}");
                leadingTrailingSeparator = Pattern.compile("^-|-$", Pattern.CASE_INSENSITIVE);
            } else {
                String regexpSeparator = Pattern.quote(separator);
                duplicateSeparator = Pattern.compile("" + regexpSeparator + "{2,}");
                leadingTrailingSeparator = Pattern.compile("^" + regexpSeparator + "|" + regexpSeparator + "$", Pattern.CASE_INSENSITIVE);
            }

            // No more than one of the separator in a row.
            matcher = duplicateSeparator.matcher(parameterized);
            if (matcher.find()) {
                parameterized = matcher.replaceAll(separator);
            }

            // Remove leading/trailing separator.
            matcher = leadingTrailingSeparator.matcher(parameterized);
            if (matcher.find()) {
                parameterized = matcher.replaceAll("");
            }
        }

        if (!preserveCase) {
            parameterized = parameterized.toLowerCase();
        }

        return parameterized;
    }
}
