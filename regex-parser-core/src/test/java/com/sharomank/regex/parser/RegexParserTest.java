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
        List<RegexPart> expected = Arrays.asList(new RegexPart(regex, RegexTypes.CharacterGroup));
        List<RegexPart> actual = RegexParser.parse(regex);
        matchRegexParts(expected, actual);
    }

    @Test
    public void simpleQuantifierGroup() throws Exception {
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("z", RegexTypes.None),
                new RegexPart("{", RegexTypes.QuantifierGroup),
                new RegexPart("2", RegexTypes.None),
                new RegexPart("}", RegexTypes.QuantifierGroup)
        );
        List<RegexPart> actual = RegexParser.parse("z{2}");
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
