package com.sharomank.regex.parser;

import com.sharomank.regex.parser.enums.RegexChar;
import com.sharomank.regex.parser.enums.RegexType;

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
    private static final List<RegexType> SECOND_LEVEL = Arrays.asList(
            RegexType.Alternation,
            RegexType.Quantifier,
            RegexType.QuantifierGroup
    );

    private static final List<RegexType> THIRD_LEVEL = Arrays.asList(
            RegexType.Anchor,
            RegexType.CharacterClass,
            RegexType.CharacterGroup,
            RegexType.None,
            RegexType.NonPrintable
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
                return Arrays.asList(new RegexPart(regexPattern, RegexType.ParseError));
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
        if (RegexType.Group.equals(helper.getCurrentType())) {
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
        } else if (RegexType.Group.equals(helper.getCurrentType())) {
            helper.putCurrentRegexPart();
        }
    }

    private static void parseNextToken(ParserHelper helper) {
        if (helper.isEnd()) {
            return;
        }

        char currentChar = helper.getCurrentChar();
        char previousChar = helper.getPreviousChar();
        helper.incrementCurrentIndex();

        if (helper.isStart() && RegexChar.Caret.getValue().equals(currentChar)) {
            helper.setCurrentType(RegexType.Anchor);
        } else if (RegexChar.Backslash.getValue().equals(previousChar) && (!"\\\\".equals(helper.getPreviousToken()) || helper.isSkipToken())) {
            if (helper.getCurrentIndex() == 2 && RegexChar.Start.getValue().equals(currentChar)) {
                helper.setCurrentType(RegexType.Anchor);
            } else if (RegexChar.ANCHOR_AFTER_BACKSLASH.contains(currentChar)) {
                helper.setCurrentType(RegexType.Anchor);
            } else if (RegexChar.CHARACTER_CLASSES_AFTER_BACKSLASH.contains(currentChar)) {
                helper.setCurrentType(RegexType.CharacterClass);
            } else if (RegexChar.NON_PRINTABLES.contains(currentChar)) {
                helper.setCurrentType(RegexType.NonPrintable);
            } else if (helper.isEnd() && RegexChar.ANCHOR_END.contains(currentChar)) {
                helper.setCurrentType(RegexType.Anchor);
            } else {
                helper.setCurrentType(RegexType.None);
            }
        } else {
            if (helper.isEnd() && RegexChar.Dollar.getValue().equals(currentChar)) {
                helper.setCurrentType(RegexType.Anchor);
            } else if (RegexChar.GROUPS.contains(currentChar)) {
                helper.setCurrentType(RegexType.Group);
            } else if (RegexChar.SquareBracketStart.getValue().equals(currentChar)) {
                while (!RegexChar.SquareBracketEnd.getValue().equals(helper.getCurrentChar())) {
                    if (!helper.isEnd()) {
                        helper.incrementCurrentIndex();
                    }
                }
                helper.incrementCurrentIndex();
                helper.setCurrentType(RegexType.CharacterGroup);
            } else if (RegexChar.Point.getValue().equals(currentChar)) {
                helper.setCurrentType(RegexType.CharacterClass);
            } else if (RegexChar.BracesStart.getValue().equals(currentChar)) {
                while (!RegexChar.BracesEnd.getValue().equals(helper.getCurrentChar())) {
                    if (!helper.isEnd()) {
                        helper.incrementCurrentIndex();
                    }
                }
                helper.incrementCurrentIndex();
                helper.setCurrentType(RegexType.QuantifierGroup);
            } else if (RegexChar.QUANTIFIERS.contains(currentChar)) {
                helper.setCurrentType(RegexType.Quantifier);
            } else if (RegexChar.Backslash.getValue().equals(currentChar)) {
                helper.skipToken();
                parseNextToken(helper);
            } else if (RegexChar.Or.getValue().equals(currentChar)) {
                helper.setCurrentType(RegexType.Alternation);
            } else {
                helper.setCurrentType(RegexType.None);
            }
        }
    }
}