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
        String regex = "[test]";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("[test]", RegexTypes.CharacterGroup)
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
