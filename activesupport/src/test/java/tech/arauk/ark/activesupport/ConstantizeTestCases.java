package tech.arauk.ark.activesupport;

import tech.arauk.ark.activesupport.ace.*;
import tech.arauk.ark.activesupport.ace.base.*;

/**
 * A collection of test cases for the inflection methods.
 *
 * @author Rodrigo Scomasson do Nascimento <rodrigo.sc.na@gmail.com>
 */
public class ConstantizeTestCases {
    public static String CONSTANTIZE_BASE_PACKAGE = "tech.arauk.ark.activesupport.";

    public static Object[][] EXISTING_CONSTANTS = {
            { "ace.base.Case",      Case.class },
            { ".ace.base.Case",     Case.class },
            { "ace.base.Case$Dice", Case.Dice.class },
            { "ace.base.Case$Dice", Fase.Dice.class },
            { "ace.Gas$Case",       Gas.Case.class },
            { "ace.base.Case$Dice", Gas.Case.Dice.class }
    };

    public static String[] UNKNOWN_CONSTANTS = {
            "UnknownClass",
            "UnknownClass.Ace",
            "UnknownClass.Ace.Base",
            "An invalid string",
            "InvalidClass\n",
            "ace.ConstantizeTestCases",
            "ace.Base.ConstantizeTestCases",
            "ace.Gas.Base",
            "ace.Gas.ConstantizeTestCases",
            "",
            ".",
            "ace.gas"
    };
}
