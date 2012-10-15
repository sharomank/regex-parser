package com.sharomank.regex.parser;

import com.sharomank.regex.parser.enums.RegexType;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Roman Kurbangaliyev
 * @since 31.05.12
 */
public class RegexParserTest {

    @Test
    public void simpleGroup() throws Exception {
        String regex = "(test)";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("(", RegexType.Group),
                new RegexPart("t", RegexType.None),
                new RegexPart("e", RegexType.None),
                new RegexPart("s", RegexType.None),
                new RegexPart("t", RegexType.None),
                new RegexPart(")", RegexType.Group)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleGroupAfterQuantifierGroup() throws Exception {
        String regex = "(\\d{4})";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("(", RegexType.Group),
                new RegexPart("\\d", RegexType.CharacterClass),
                new RegexPart("{4}", RegexType.QuantifierGroup),
                new RegexPart(")", RegexType.Group)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleCharacterGroup() throws Exception {
        String regex = "[test]";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("[test]", RegexType.CharacterGroup)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleCharacterClass() throws Exception {
        String regex = "\\d\\D\\s\\S\\w\\W.";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("\\d", RegexType.CharacterClass),
                new RegexPart("\\D", RegexType.CharacterClass),
                new RegexPart("\\s", RegexType.CharacterClass),
                new RegexPart("\\S", RegexType.CharacterClass),
                new RegexPart("\\w", RegexType.CharacterClass),
                new RegexPart("\\W", RegexType.CharacterClass),
                new RegexPart(".", RegexType.CharacterClass)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleQuantifier() throws Exception {
        String regex = "a+b?d*";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("a", RegexType.None),
                new RegexPart("+", RegexType.Quantifier),
                new RegexPart("b", RegexType.None),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("d", RegexType.None),
                new RegexPart("*", RegexType.Quantifier)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleQuantifierGroup() throws Exception {
        String regex = "a{2}";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("a", RegexType.None),
                new RegexPart("{2}", RegexType.QuantifierGroup)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleAlternation() throws Exception {
        String regex = "a|b";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("a", RegexType.None),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("b", RegexType.None)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleBackslash() throws Exception {
        String regex = "\\\\\\\\";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("\\\\", RegexType.None),
                new RegexPart("\\\\", RegexType.None)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleAnchorWithoutBackslash() throws Exception {
        String regex = "^t$";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("^", RegexType.Anchor),
                new RegexPart("t", RegexType.None),
                new RegexPart("$", RegexType.Anchor)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleAnchorStartAndEnd() throws Exception {
        String regex = "\\At\\Z";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("\\A", RegexType.Anchor),
                new RegexPart("t", RegexType.None),
                new RegexPart("\\Z", RegexType.Anchor)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleAnchorStartAndEndLine() throws Exception {
        String regex = "\\At\\z";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("\\A", RegexType.Anchor),
                new RegexPart("t", RegexType.None),
                new RegexPart("\\z", RegexType.Anchor)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleAnchorOther() throws Exception {
        String regex = "\\G\\b\\B";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("\\G", RegexType.Anchor),
                new RegexPart("\\b", RegexType.Anchor),
                new RegexPart("\\B", RegexType.Anchor)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleNonPrintable() throws Exception {
        String regex = "\\a\\t\\e\\f\\v\\r\\n";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("\\a", RegexType.NonPrintable),
                new RegexPart("\\t", RegexType.NonPrintable),
                new RegexPart("\\e", RegexType.NonPrintable),
                new RegexPart("\\f", RegexType.NonPrintable),
                new RegexPart("\\v", RegexType.NonPrintable),
                new RegexPart("\\r", RegexType.NonPrintable),
                new RegexPart("\\n", RegexType.NonPrintable)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleParseError() throws Exception {
        String regex = "parse|error\\";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("parse|error\\", RegexType.ParseError)
        );
        checkParse(regex, expected);
    }

    private void checkParse(String regex, List<RegexPart> expected) {
        List<RegexPart> actual = RegexParser.parse(regex);
        matchRegexParts(expected, actual);
    }

    private void matchRegexParts(List<RegexPart> expected, List<RegexPart> actual) {
        if (expected == null || actual == null) {
            throw new IllegalArgumentException("list params cannot be null");
        }
        if (expected.size() != actual.size()) {
            throw new IllegalStateException("size is different");
        }

        Iterator<RegexPart> expIterator = expected.iterator();
        Iterator<RegexPart> actIterator = actual.iterator();

        while (expIterator.hasNext()) {
            RegexPart ex = expIterator.next();
            RegexPart ac = actIterator.next();
            if (!ex.equals(ac)) {
                throw new IllegalStateException("parts is different");
            }
        }
    }
}
