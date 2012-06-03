package com.sharomank.regex.parser;

import com.sharomank.regex.parser.enums.RegexTypes;
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
                new RegexPart("(", RegexTypes.Group),
                new RegexPart("t", RegexTypes.None),
                new RegexPart("e", RegexTypes.None),
                new RegexPart("s", RegexTypes.None),
                new RegexPart("t", RegexTypes.None),
                new RegexPart(")", RegexTypes.Group)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleCharacterGroup() throws Exception {
        String regex = "[test]";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("[test]", RegexTypes.CharacterGroup)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleCharacterClass() throws Exception {
        String regex = "\\d\\D\\s\\S\\w\\W.";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("\\d", RegexTypes.CharacterClass),
                new RegexPart("\\D", RegexTypes.CharacterClass),
                new RegexPart("\\s", RegexTypes.CharacterClass),
                new RegexPart("\\S", RegexTypes.CharacterClass),
                new RegexPart("\\w", RegexTypes.CharacterClass),
                new RegexPart("\\W", RegexTypes.CharacterClass),
                new RegexPart(".", RegexTypes.CharacterClass)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleQuantifier() throws Exception {
        String regex = "a+b?d*";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("a", RegexTypes.None),
                new RegexPart("+", RegexTypes.Quantifier),
                new RegexPart("b", RegexTypes.None),
                new RegexPart("?", RegexTypes.Quantifier),
                new RegexPart("d", RegexTypes.None),
                new RegexPart("*", RegexTypes.Quantifier)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleQuantifierGroup() throws Exception {
        String regex = "a{2}";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("a", RegexTypes.None),
                new RegexPart("{2}", RegexTypes.QuantifierGroup)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleAlternation() throws Exception {
        String regex = "a|b";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("a", RegexTypes.None),
                new RegexPart("|", RegexTypes.Alternation),
                new RegexPart("b", RegexTypes.None)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleBackslash() throws Exception {
        String regex = "\\\\\\\\";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("\\\\", RegexTypes.None),
                new RegexPart("\\\\", RegexTypes.None)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleAnchorWithoutBackslash() throws Exception {
        String regex = "^t$";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("^", RegexTypes.Anchor),
                new RegexPart("t", RegexTypes.None),
                new RegexPart("$", RegexTypes.Anchor)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleAnchorStartAndEnd() throws Exception {
        String regex = "\\At\\Z";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("\\A", RegexTypes.Anchor),
                new RegexPart("t", RegexTypes.None),
                new RegexPart("\\Z", RegexTypes.Anchor)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleAnchorStartAndEndLine() throws Exception {
        String regex = "\\At\\z";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("\\A", RegexTypes.Anchor),
                new RegexPart("t", RegexTypes.None),
                new RegexPart("\\z", RegexTypes.Anchor)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleAnchorOther() throws Exception {
        String regex = "\\G\\b\\B";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("\\G", RegexTypes.Anchor),
                new RegexPart("\\b", RegexTypes.Anchor),
                new RegexPart("\\B", RegexTypes.Anchor)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleNonPrintable() throws Exception {
        String regex = "\\a\\t\\e\\f\\v\\r\\n";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("\\a", RegexTypes.NonPrintable),
                new RegexPart("\\t", RegexTypes.NonPrintable),
                new RegexPart("\\e", RegexTypes.NonPrintable),
                new RegexPart("\\f", RegexTypes.NonPrintable),
                new RegexPart("\\v", RegexTypes.NonPrintable),
                new RegexPart("\\r", RegexTypes.NonPrintable),
                new RegexPart("\\n", RegexTypes.NonPrintable)
        );
        checkParse(regex, expected);
    }

    @Test
    public void simpleParseError() throws Exception {
        String regex = "parse|error\\";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("parse|error\\", RegexTypes.ParseError)
        );
        checkParse(regex, expected);
    }

    private void checkParse(String regex, List<RegexPart> expected) {
        List<RegexPart> actual = RegexParser.parse(regex);
        matchRegexParts(expected, actual);
    }

    private boolean matchRegexParts(List<RegexPart> expected, List<RegexPart> actual) {
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
        return true;
    }
}
