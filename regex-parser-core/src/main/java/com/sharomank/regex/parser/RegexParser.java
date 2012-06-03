package com.sharomank.regex.parser;

import com.sharomank.regex.parser.enums.RegexChars;
import com.sharomank.regex.parser.enums.RegexTypes;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Regular expressions parser
 *
 * @author Roman Kurbangaliyev
 * @since 21.05.2012
 */
public class RegexParser {
    private static final List<RegexTypes> SECOND_LEVEL = Arrays.asList(
            RegexTypes.Alternation,
            //RegexTypes.Anchor,
            RegexTypes.Quantifier,
            RegexTypes.QuantifierGroup
    );

    private static final List<RegexTypes> THIRD_LEVEL = Arrays.asList(
            RegexTypes.Anchor,
            RegexTypes.CharacterClass,
            RegexTypes.CharacterGroup,
            RegexTypes.None,
            RegexTypes.NonPrintable
    );

    /**
     * Parse regular expression
     *
     * @param regexPattern regular expression
     * @return list of {@link RegexPart}
     */
    public static List<RegexPart> parse(String regexPattern) {
        if (regexPattern == null || regexPattern.trim().length() == 0) {
            return Collections.emptyList();
        } else {
            try {
                Pattern.compile(regexPattern);
            } catch (PatternSyntaxException e) {
                return Arrays.asList(new RegexPart(regexPattern, RegexTypes.ParseError));
            }
        }

        ParserHelper helper = new ParserHelper(regexPattern);
        parseNextToken(helper);
        if (helper.getToken() != null) {
            parseFirstLevel(helper);
        }
        return helper.getResult();
    }

    private static void parseFirstLevel(ParserHelper helper) {
        if (RegexTypes.Group.equals(helper.getCurrentType())) {
            helper.putCurrentRegexPart();
            parseNextToken(helper);
            parseSecondLevel(helper);
        } else {
            parseSecondLevel(helper);
        }
    }

    private static void parseSecondLevel(ParserHelper helper) {
        if (SECOND_LEVEL.contains(helper.getCurrentType())) {
            helper.putCurrentRegexPart();
            parseNextToken(helper);
            parseThirdLevel(helper);
        } else {
            parseThirdLevel(helper);
        }
    }

    private static void parseThirdLevel(ParserHelper helper) {
        if (THIRD_LEVEL.contains(helper.getCurrentType())) {
            helper.putCurrentRegexPart();
            parseNextToken(helper);
            parseFirstLevel(helper);
        } else if (!helper.isEnd()) {
            parseFirstLevel(helper);
        }
    }

    private static void parseNextToken(ParserHelper helper) {
        if (helper.isEnd()) {
            return;
        }

        char currentChar = helper.getCurrentChar();
        char previousChar = helper.getPreviousChar();
        helper.incrementCurrentIndex();

        if (helper.isStart() && RegexChars.Caret.getValue().equals(currentChar)) {
            helper.setCurrentType(RegexTypes.Anchor);
        } else if (RegexChars.Backslash.getValue().equals(previousChar) && !"\\\\".equals(helper.getPreviousToken())) {
            if (helper.getCurrentIndex() == 2 && RegexChars.Start.getValue().equals(currentChar)) {
                helper.setCurrentType(RegexTypes.Anchor);
            } else if (RegexChars.ANCHOR_AFTER_BACKSLASH.contains(currentChar)) {
                helper.setCurrentType(RegexTypes.Anchor);
            } else if (RegexChars.CHARACTER_CLASSES_AFTER_BACKSLASH.contains(currentChar)) {
                helper.setCurrentType(RegexTypes.CharacterClass);
            } else if (RegexChars.NON_PRINTABLES.contains(currentChar)) {
                helper.setCurrentType(RegexTypes.NonPrintable);
            } else if (helper.isEnd() && RegexChars.ANCHOR_END.contains(currentChar)) {
                helper.setCurrentType(RegexTypes.Anchor);
            } else {
                helper.setCurrentType(RegexTypes.None);
            }
        } else {
            if (helper.isEnd() && RegexChars.Dollar.getValue().equals(currentChar)) {
                helper.setCurrentType(RegexTypes.Anchor);
            } else if (RegexChars.GROUPS.contains(currentChar)) {
                helper.setCurrentType(RegexTypes.Group);
            } else if (RegexChars.SquareBracketStart.getValue().equals(currentChar)) {
                while (!RegexChars.SquareBracketEnd.getValue().equals(helper.getCurrentChar())) {
                    if (!helper.isEnd()) {
                        helper.incrementCurrentIndex();
                    }
                }
                helper.incrementCurrentIndex();
                helper.setCurrentType(RegexTypes.CharacterGroup);
            } else if (RegexChars.Point.getValue().equals(currentChar)) {
                helper.setCurrentType(RegexTypes.CharacterClass);
            } else if (RegexChars.BracesStart.getValue().equals(currentChar)) {
                while (!RegexChars.BracesEnd.getValue().equals(helper.getCurrentChar())) {
                    if (!helper.isEnd()) {
                        helper.incrementCurrentIndex();
                    }
                }
                helper.incrementCurrentIndex();
                helper.setCurrentType(RegexTypes.QuantifierGroup);
            } else if (RegexChars.QUANTIFIERS.contains(currentChar)) {
                helper.setCurrentType(RegexTypes.Quantifier);
            } else if (!helper.isEnd() && RegexChars.Backslash.getValue().equals(currentChar)) {
                parseNextToken(helper);
            } else if (RegexChars.Or.getValue().equals(currentChar)) {
                helper.setCurrentType(RegexTypes.Alternation);
            } else {
                helper.setCurrentType(RegexTypes.None);
            }
        }
    }
}