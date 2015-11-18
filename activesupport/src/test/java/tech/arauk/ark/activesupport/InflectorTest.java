package tech.arauk.ark.activesupport;

import junit.framework.TestCase;

import java.util.List;

import tech.arauk.ark.activesupport.inflector.DefaultInflections;
import tech.arauk.ark.activesupport.inflector.Inflections;

public class InflectorTest extends TestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DefaultInflections.initializeDefaultInflections();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        DefaultInflections.getDefaultInflections().clear();
    }

    public void testPluralizePlurals() {
        assertEquals("plurals", Inflector.pluralize("plurals"));
        assertEquals("Plurals", Inflector.pluralize("Plurals"));
    }

    public void testPluralizeEmptyString() {
        assertEquals("", Inflector.pluralize(""));
    }

    public void testUncontabilityOf() {
        List<String> uncountables = Inflector.inflections().getUncountables();
        for (String uncountable : uncountables) {
            assertEquals(uncountable, Inflector.singularize(uncountable));
            assertEquals(uncountable, Inflector.pluralize(uncountable));
            assertEquals(Inflector.pluralize(uncountable), Inflector.singularize(uncountable));
        }
    }

    public void testUncountableWordIsNotGreedy() {
        String uncountableWord = "ors";
        String countableWord = "sponsor";

        Inflector.inflections().uncountable(uncountableWord);

        assertEquals(uncountableWord, Inflector.singularize(uncountableWord));
        assertEquals(uncountableWord, Inflector.pluralize(uncountableWord));
        assertEquals(Inflector.pluralize(uncountableWord), Inflector.singularize(uncountableWord));

        assertEquals("sponsor", Inflector.singularize(countableWord));
        assertEquals("sponsors", Inflector.pluralize(countableWord));
        assertEquals("sponsor", Inflector.singularize(Inflector.pluralize(countableWord)));
    }

    public void testPluralizeSingular() {
        for (int i = 0; i < InflectorTestCases.SINGULAR_TO_PLURAL.length; i++) {
            String singular = InflectorTestCases.SINGULAR_TO_PLURAL[i][0];
            String plural = InflectorTestCases.SINGULAR_TO_PLURAL[i][1];

            assertEquals(plural, Inflector.pluralize(singular));
            assertEquals(Inflector.capitalize(plural), Inflector.pluralize(Inflector.capitalize(singular)));
        }
    }

    public void testSingularizePlural() {
        for (int i = 0; i < InflectorTestCases.SINGULAR_TO_PLURAL.length; i++) {
            String singular = InflectorTestCases.SINGULAR_TO_PLURAL[i][0];
            String plural = InflectorTestCases.SINGULAR_TO_PLURAL[i][1];

            assertEquals(singular, Inflector.pluralize(plural));
            assertEquals(Inflector.capitalize(singular), Inflector.pluralize(Inflector.capitalize(plural)));
        }
    }

    public void testPluralizePlural() {
        for (int i = 0; i < InflectorTestCases.SINGULAR_TO_PLURAL.length; i++) {
            String plural = InflectorTestCases.SINGULAR_TO_PLURAL[i][1];

            assertEquals(plural, Inflector.pluralize(plural));
            assertEquals(Inflector.capitalize(plural), Inflector.pluralize(Inflector.capitalize(plural)));
        }
    }

    public void testSingularizeSingular() {
        for (int i = 0; i < InflectorTestCases.SINGULAR_TO_PLURAL.length; i++) {
            String singular = InflectorTestCases.SINGULAR_TO_PLURAL[i][0];

            assertEquals(singular, Inflector.singularize(singular));
            assertEquals(Inflector.capitalize(singular), Inflector.singularize(Inflector.capitalize(singular)));
        }
    }

    public void testOverwritePreviousInflectors() {
        assertEquals("series", Inflector.singularize("series"));
        Inflector.inflections().singular("series", "serie");
        assertEquals("serie", Inflector.singularize("series"));
    }

    public void testTitleizeMixtureToTitleCase() {
        for (int i = 0; i < InflectorTestCases.MIXTURE_TO_TITLE_CASE.length; i++) {
            String before = InflectorTestCases.MIXTURE_TO_TITLE_CASE[i][0];
            String titleized = InflectorTestCases.MIXTURE_TO_TITLE_CASE[i][1];

            assertEquals(titleized, Inflector.titleize(before));
        }
    }

    public void testCamelize() {
        for (int i = 0; i < InflectorTestCases.CAMEL_TO_UNDERSCORE.length; i++) {
            String camel = InflectorTestCases.CAMEL_TO_UNDERSCORE[i][0];
            String underscore = InflectorTestCases.CAMEL_TO_UNDERSCORE[i][1];

            assertEquals(camel, Inflector.camelize(underscore));
        }
    }

    public void testCamelizeWithLowerDowncasesTheFirstLetter() {
        assertEquals("capital", Inflector.camelize("Capital", false));
    }

    public void testCamelizeWithUnderscores() {
        assertEquals("CamelCase", Inflector.camelize("Camel_Case"));
    }

    public void testAcronyms() {
        Inflections inflect = Inflector.inflections();
        inflect.acronym("API");
        inflect.acronym("HTML");
        inflect.acronym("HTTP");
        inflect.acronym("RESTful");
        inflect.acronym("W3C");
        inflect.acronym("PhD");
        inflect.acronym("RoR");
        inflect.acronym("SSL");

        String[][] testCases = {
                //camelize,            underscore,           humanize,             titleize
                {"API",               "api",                "API",                "API"},
                {"APIController",     "api_controller",     "API controller",     "API Controller"},
                {"nokogiri.HTML",     "nokogiri/html",      "Nokogiri/HTML",      "Nokogiri/HTML"},
                {"HTTPAPI",           "http_api",           "HTTP API",           "HTTP API"},
                {"http.Get",          "http/get",           "HTTP/get",           "HTTP/Get"},
                {"SSLError",          "ssl_error",          "SSL error",          "SSL Error"},
                {"RESTful",           "restful",            "RESTful",            "RESTful"},
                {"RESTfulController", "restful_controller", "RESTful controller", "RESTful Controller"},
                {"nested.RESTful",    "nested/restful",     "Nested/RESTful",     "Nested/RESTful"},
                {"IHeartW3C",         "i_heart_w3c",        "I heart W3C",        "I Heart W3C"},
                {"PhDRequired",       "phd_required",       "PhD required",       "PhD Required"},
                {"IRoRU",             "i_ror_u",            "I RoR u",            "I RoR U"},
                {"RESTfulHTTPAPI",    "restful_http_api",   "RESTful HTTP API",   "RESTful HTTP API"},
                {"http.RESTful",      "http/restful",       "HTTP/RESTful",       "HTTP/RESTful"},
                {"http.RESTfulAPI",   "http/restful_api",   "HTTP/RESTful API",   "HTTP/RESTful API"},
                {"APIRESTful",        "api_restful",        "API RESTful",        "API RESTful"},

                //misdirection
                {"Capistrano",        "capistrano",         "Capistrano",         "Capistrano"},
                {"CapiController",    "capi_controller",    "Capi controller",    "Capi Controller"},
                {"HttpsApis",         "https_apis",         "Https apis",         "Https Apis"},
                {"Html5",             "html5",              "Html5",              "Html5"},
                {"Restfully",         "restfully",          "Restfully",          "Restfully"},
                {"RoRails",           "ro_rails",           "Ro rails",           "Ro Rails"}
        };

        for (String[] testCase : testCases) {
            String camel = testCase[0];
            String under = testCase[1];
            String human = testCase[2];
            String title = testCase[3];

            assertEquals(camel, Inflector.camelize(under));
            assertEquals(camel, Inflector.camelize(camel));
            assertEquals(under, Inflector.underscore(under));
            assertEquals(under, Inflector.underscore(camel));
            assertEquals(title, Inflector.titleize(under));
            assertEquals(title, Inflector.titleize(camel));
            assertEquals(human, Inflector.humanize(under));
        }
    }

    public void testAcronymOverride() {
        Inflections inflect = Inflector.inflections();
        inflect.acronym("API");
        inflect.acronym("LegacyApi");

        assertEquals("LegacyApi", Inflector.camelize("legacyapi"));
        assertEquals("LegacyAPI", Inflector.camelize("legacy_api"));
        assertEquals("SomeLegacyApi", Inflector.camelize("some_legacyapi"));
        assertEquals("Nonlegacyapi", Inflector.camelize("nonlegacyapi"));
    }

    public void testAcronymsCamelizeLower() {
        Inflections inflect = Inflector.inflections();
        inflect.acronym("API");
        inflect.acronym("HTML");

        assertEquals("htmlAPI", Inflector.camelize("html_api", false));
        assertEquals("htmlAPI", Inflector.camelize("htmlAPI", false));
        assertEquals("htmlAPI", Inflector.camelize("HTMLAPI", false));
    }

    public void testUnderscoreAcronymSequence() {
        Inflections inflect = Inflector.inflections();
        inflect.acronym("API");
        inflect.acronym("JSON");
        inflect.acronym("HTML");

        assertEquals("json_html_api", Inflector.underscore("JSONHTMLAPI"));
    }

    public void testUnderscore() {
        for (int i = 0; i < InflectorTestCases.CAMEL_TO_UNDERSCORE.length; i++) {
            String camel = InflectorTestCases.CAMEL_TO_UNDERSCORE[i][0];
            String underscore = InflectorTestCases.CAMEL_TO_UNDERSCORE[i][1];

            assertEquals(underscore, Inflector.underscore(camel));
        }

        for (int i = 0; i < InflectorTestCases.CAMEL_TO_UNDERSCORE_WITHOUT_REVERSE.length; i++) {
            String camel = InflectorTestCases.CAMEL_TO_UNDERSCORE_WITHOUT_REVERSE[i][0];
            String underscore = InflectorTestCases.CAMEL_TO_UNDERSCORE_WITHOUT_REVERSE[i][1];

            assertEquals(underscore, Inflector.underscore(camel));
        }
    }

    public void testCamelizeWithModule() {
        for (int i = 0; i < InflectorTestCases.CAMEL_WITH_PACKAGE_TO_UNDERSCORE_WITH_SLASH.length; i++) {
            String camel = InflectorTestCases.CAMEL_WITH_PACKAGE_TO_UNDERSCORE_WITH_SLASH[i][0];
            String underscore = InflectorTestCases.CAMEL_WITH_PACKAGE_TO_UNDERSCORE_WITH_SLASH[i][1];

            assertEquals(camel, Inflector.camelize(underscore));
        }
    }

    public void testUnderscoreWithSlashes() {
        for (int i = 0; i < InflectorTestCases.CAMEL_WITH_PACKAGE_TO_UNDERSCORE_WITH_SLASH.length; i++) {
            String camel = InflectorTestCases.CAMEL_WITH_PACKAGE_TO_UNDERSCORE_WITH_SLASH[i][0];
            String underscore = InflectorTestCases.CAMEL_WITH_PACKAGE_TO_UNDERSCORE_WITH_SLASH[i][1];

            assertEquals(underscore, Inflector.underscore(camel));
        }
    }

    public void testDemodulize() {
        assertEquals("Account", Inflector.demodulize("MyApplication::Billing::Account"));
        assertEquals("Account", Inflector.demodulize("Account"));
        assertEquals("Account", Inflector.demodulize("::Account"));
        assertEquals("", Inflector.demodulize(""));
    }

    public void testDeconstantize() {
        assertEquals("MyApplication::Billing", Inflector.deconstantize("MyApplication::Billing::Account"));
        assertEquals("::MyApplication::Billing", Inflector.deconstantize("::MyApplication::Billing::Account"));

        assertEquals("MyApplication", Inflector.deconstantize("MyApplication::Billing"));
        assertEquals("::MyApplication", Inflector.deconstantize("::MyApplication::Billing"));

        assertEquals("", Inflector.deconstantize("Account"));
        assertEquals("", Inflector.deconstantize("::Account"));
        assertEquals("", Inflector.deconstantize(""));
    }

    public void testForeignKey() {
        for (int i = 0; i < InflectorTestCases.CLASS_NAME_TO_FOREIGN_KEY_WITH_UNDERSCORE.length; i++) {
            String klass = InflectorTestCases.CLASS_NAME_TO_FOREIGN_KEY_WITH_UNDERSCORE[i][0];
            String foreignKey = InflectorTestCases.CLASS_NAME_TO_FOREIGN_KEY_WITH_UNDERSCORE[i][1];

            assertEquals(foreignKey, Inflector.foreignKey(klass));
        }

        for (int i = 0; i < InflectorTestCases.CLASS_NAME_TO_FOREIGN_KEY_WITHOUT_UNDERSCORE.length; i++) {
            String klass = InflectorTestCases.CLASS_NAME_TO_FOREIGN_KEY_WITHOUT_UNDERSCORE[i][0];
            String foreignKey = InflectorTestCases.CLASS_NAME_TO_FOREIGN_KEY_WITHOUT_UNDERSCORE[i][1];

            assertEquals(foreignKey, Inflector.foreignKey(klass, false));
        }
    }

    public void testTableize() {
        for (int i = 0; i < InflectorTestCases.CLASS_NAME_TO_TABLE_NAME.length; i++) {
            String className = InflectorTestCases.CLASS_NAME_TO_TABLE_NAME[i][0];
            String tableName = InflectorTestCases.CLASS_NAME_TO_TABLE_NAME[i][1];

            assertEquals(tableName, Inflector.tableize(className));
        }
    }

    public void testParameterize() {
        for (int i = 0; i < InflectorTestCases.STRING_TO_PARAMETERIZED.length; i++) {
            String someString = InflectorTestCases.STRING_TO_PARAMETERIZED[i][0];
            String parameterizedString = InflectorTestCases.STRING_TO_PARAMETERIZED[i][1];

            assertEquals(parameterizedString, Inflector.parameterize(someString));
        }
    }

    public void testParameterizeAndNormalize() {
        for (int i = 0; i < InflectorTestCases.STRING_TO_PARAMETERIZED_AND_NORMALIZED.length; i++) {
            String someString = InflectorTestCases.STRING_TO_PARAMETERIZED_AND_NORMALIZED[i][0];
            String parameterizedString = InflectorTestCases.STRING_TO_PARAMETERIZED_AND_NORMALIZED[i][1];

            assertEquals(parameterizedString, Inflector.parameterize(someString));
        }
    }

    public void testParameterizeWithCustomSeparator() {
        for (int i = 0; i < InflectorTestCases.STRING_TO_PARAMETERIZED_WITH_UNDERSCORE.length; i++) {
            String someString = InflectorTestCases.STRING_TO_PARAMETERIZED_WITH_UNDERSCORE[i][0];
            String parameterizedString = InflectorTestCases.STRING_TO_PARAMETERIZED_WITH_UNDERSCORE[i][1];

            assertEquals(parameterizedString, Inflector.parameterize(someString, "_"));
        }
    }

    public void testParameterizeWithMultiCharacterSeparator() {
        for (int i = 0; i < InflectorTestCases.STRING_TO_PARAMETERIZED.length; i++) {
            String someString = InflectorTestCases.STRING_TO_PARAMETERIZED[i][0];
            String parameterizedString = InflectorTestCases.STRING_TO_PARAMETERIZED[i][1];

            assertEquals(parameterizedString.replaceAll("-", "__sep__"), Inflector.parameterize(someString, "__sep__"));
        }
    }

    public void testClassify() {
        for (int i = 0; i < InflectorTestCases.CLASS_NAME_TO_TABLE_NAME.length; i++) {
            String className = InflectorTestCases.CLASS_NAME_TO_TABLE_NAME[i][0];
            String tableName = InflectorTestCases.CLASS_NAME_TO_TABLE_NAME[i][1];

            assertEquals(className, Inflector.classify(tableName));
            assertEquals(className, Inflector.classify("table_prefix." + tableName));
        }
    }

    public void testClassifyWithLeadingSchemaName() {
        assertEquals("FooBar", Inflector.classify("schema.foo_bar"));
    }

    public void testHumanize() {
        for (int i = 0; i < InflectorTestCases.UNDERSCORE_TO_HUMAN.length; i++) {
            String underscore = InflectorTestCases.UNDERSCORE_TO_HUMAN[i][0];
            String human = InflectorTestCases.UNDERSCORE_TO_HUMAN[i][1];

            assertEquals(human, Inflector.humanize(underscore));
        }
    }

    public void testHumanizeWithoutCapitalize() {
        for (int i = 0; i < InflectorTestCases.UNDERSCORE_TO_HUMAN_WITHOUT_CAPITALIZE.length; i++) {
            String underscore = InflectorTestCases.UNDERSCORE_TO_HUMAN_WITHOUT_CAPITALIZE[i][0];
            String human = InflectorTestCases.UNDERSCORE_TO_HUMAN_WITHOUT_CAPITALIZE[i][1];

            assertEquals(human, Inflector.humanize(underscore, false));
        }
    }

    public void testHumanizeByRule() {
        Inflections inflect = Inflector.inflections();
        inflect.human("_cnt$", "$1_count");
        inflect.human("^prefx_", "$1");

        assertEquals("Jargon count", Inflector.humanize("jargon_cnt"));
        assertEquals("Request", Inflector.humanize("prefx_request"));
    }

    public void testHumanizeByString() {
        Inflections inflect = Inflector.inflections();
        inflect.human("col_rpted_bugs", "Reported bugs");

        assertEquals("Reported bugs", Inflector.humanize("col_rpted_bugs"));
        assertEquals("Col rpted bugs", Inflector.humanize("COL_rpted_bugs"));
    }

    public void testConstantize() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void testSafeConstantize() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void testOrdinal() {
        for (int i = 0; i < InflectorTestCases.ORDINAL_NUMBERS.length; i++) {
            String number = InflectorTestCases.ORDINAL_NUMBERS[i][0];
            String ordinalized = InflectorTestCases.ORDINAL_NUMBERS[i][1];

            assertEquals(ordinalized, number + Inflector.ordinal(number));
        }
    }

    public void testOrdinalize() {
        for (int i = 0; i < InflectorTestCases.ORDINAL_NUMBERS.length; i++) {
            String number = InflectorTestCases.ORDINAL_NUMBERS[i][0];
            String ordinalized = InflectorTestCases.ORDINAL_NUMBERS[i][1];

            assertEquals(ordinalized, Inflector.ordinalize(number));
        }
    }

    public void testDasherize() {
        for (int i = 0; i < InflectorTestCases.UNDERSCORES_TO_DASHES.length; i++) {
            String underscored = InflectorTestCases.UNDERSCORES_TO_DASHES[i][0];
            String dasherized = InflectorTestCases.UNDERSCORES_TO_DASHES[i][1];

            assertEquals(dasherized, Inflector.dasherize(underscored));
        }
    }

    public void testUnderscoreAsReverseOfDasherize() {
        for (int i = 0; i < InflectorTestCases.UNDERSCORES_TO_DASHES.length; i++) {
            String underscored = InflectorTestCases.UNDERSCORES_TO_DASHES[i][0];

            assertEquals(underscored, Inflector.underscore(Inflector.dasherize(underscored)));
        }
    }

    public void testUnderscoreToLowerCamel() {
        for (int i = 0; i < InflectorTestCases.UNDERSCORE_TO_LOWER_CAMEL.length; i++) {
            String underscored = InflectorTestCases.UNDERSCORE_TO_LOWER_CAMEL[i][0];
            String lowerCamel = InflectorTestCases.UNDERSCORE_TO_LOWER_CAMEL[i][1];

            assertEquals(lowerCamel, Inflector.camelize(underscored, false));
        }
    }

    public void testInflectorLocality() {
        Inflections inflect = Inflector.inflections("es");
        inflect.plural("$", "s");
        inflect.plural("z$", "ces");

        inflect.singular("s$", "");
        inflect.singular("es$", "");

        inflect.irregular("el", "los");

        assertEquals("hijos", Inflector.pluralize("hijo", "es"));
        assertEquals("luces", Inflector.pluralize("luz", "es"));
        assertEquals("luzs", Inflector.pluralize("luz"));

        assertEquals("sociedad", Inflector.singularize("sociedades", "es"));
        assertEquals("sociedade", Inflector.singularize("sociedades"));

        assertEquals("los", Inflector.pluralize("el", "es"));
        assertEquals("els", Inflector.pluralize("el"));

        Inflector.inflections("es").clear();

        assertTrue(Inflector.inflections("es").getPlurals().isEmpty());
        assertTrue(Inflector.inflections("es").getSingulars().isEmpty());
        assertTrue(!Inflector.inflections().getPlurals().isEmpty());
        assertTrue(!Inflector.inflections().getSingulars().isEmpty());
    }

    public void testIrregularityBetweenSingularAndPlural() {
        for (int i = 0; i < InflectorTestCases.IRREGULARITIES.length; i++) {
            String singular = InflectorTestCases.IRREGULARITIES[i][0];
            String plural = InflectorTestCases.IRREGULARITIES[i][1];

            Inflections inflect = Inflector.inflections();
            inflect.irregular(singular, plural);

            assertEquals(singular, Inflector.singularize(plural));
            assertEquals(plural, Inflector.pluralize(singular));
        }
    }

    public void testPluralizeOfIrregularityPluralShouldBeTheSame() {
        for (int i = 0; i < InflectorTestCases.IRREGULARITIES.length; i++) {
            String singular = InflectorTestCases.IRREGULARITIES[i][0];
            String plural = InflectorTestCases.IRREGULARITIES[i][1];

            Inflections inflect = Inflector.inflections();
            inflect.irregular(singular, plural);

            assertEquals(plural, Inflector.pluralize(plural));
        }
    }

    public void testSingularizeOfIrregularitySingularShouldBeTheSame() {
        for (int i = 0; i < InflectorTestCases.IRREGULARITIES.length; i++) {
            String singular = InflectorTestCases.IRREGULARITIES[i][0];
            String plural = InflectorTestCases.IRREGULARITIES[i][1];

            Inflections inflect = Inflector.inflections();
            inflect.irregular(singular, plural);

            assertEquals(singular, Inflector.singularize(singular));
        }
    }

    public void testClearWithScope() {
        Inflections inflect = Inflector.inflections();
        inflect.acronym("HTTP");
        inflect.plural("(quiz)$", "$1zes");
        inflect.singular("(database)s$", "$1");
        inflect.uncountable("series");
        inflect.human("col_rpted_bugs", "Reported bugs");

        inflect.clear("acronyms");
        assertTrue(inflect.getAcronyms().isEmpty());
        assertEquals("(?=a)b", inflect.getAcronymRegex());

        inflect.clear("humans");
        assertTrue(inflect.getHumans().isEmpty());

        inflect.clear("plurals");
        assertTrue(inflect.getPlurals().isEmpty());

        inflect.clear("singulars");
        assertTrue(inflect.getSingulars().isEmpty());

        inflect.clear("uncountables");
        assertTrue(inflect.getUncountables().isEmpty());
    }

    public void testClearAll() {
        Inflections inflect = Inflector.inflections();
        inflect.acronym("HTTP");
        inflect.plural("(quiz)$", "$1zes");
        inflect.singular("(database)s$", "$1");
        inflect.uncountable("series");
        inflect.human("col_rpted_bugs", "Reported bugs");

        inflect.clear("all");

        assertTrue(inflect.getAcronyms().isEmpty());
        assertEquals("(?=a)b", inflect.getAcronymRegex());
        assertTrue(inflect.getHumans().isEmpty());
        assertTrue(inflect.getPlurals().isEmpty());
        assertTrue(inflect.getSingulars().isEmpty());
        assertTrue(inflect.getUncountables().isEmpty());
    }

    public void testClearWithNoArguments() {
        Inflections inflect = Inflector.inflections();
        inflect.acronym("HTTP");
        inflect.plural("(quiz)$", "$1zes");
        inflect.singular("(database)s$", "$1");
        inflect.uncountable("series");
        inflect.human("col_rpted_bugs", "Reported bugs");

        inflect.clear();

        assertTrue(inflect.getAcronyms().isEmpty());
        assertEquals("(?=a)b", inflect.getAcronymRegex());
        assertTrue(inflect.getHumans().isEmpty());
        assertTrue(inflect.getPlurals().isEmpty());
        assertTrue(inflect.getSingulars().isEmpty());
        assertTrue(inflect.getUncountables().isEmpty());
    }

    public void testInflectionsWithUncountableWords() {
        Inflections inflect = Inflector.inflections();
        inflect.uncountable("HTTP");

        assertEquals("HTTP", Inflector.pluralize("HTTP"));
    }
}
