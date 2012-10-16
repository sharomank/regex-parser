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

    @Test
    public void patternUsername() throws Exception {
        String regex = "^[a-z0-9_-]{3,15}$";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("^", RegexType.Anchor),
                new RegexPart("[a-z0-9_-]", RegexType.CharacterGroup),
                new RegexPart("{3,15}", RegexType.QuantifierGroup),
                new RegexPart("$", RegexType.Anchor)
        );
        checkParse(regex, expected);
    }

    @Test
    public void patternPassword() throws Exception {
        String regex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("(", RegexType.Group),
                new RegexPart("(", RegexType.Group),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("=", RegexType.None),
                new RegexPart(".", RegexType.CharacterClass),
                new RegexPart("*", RegexType.Quantifier),
                new RegexPart("\\d", RegexType.CharacterClass),
                new RegexPart(")", RegexType.Group),
                new RegexPart("(", RegexType.Group),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("=", RegexType.None),
                new RegexPart(".", RegexType.CharacterClass),
                new RegexPart("*", RegexType.Quantifier),
                new RegexPart("[a-z]", RegexType.CharacterGroup),
                new RegexPart(")", RegexType.Group),
                new RegexPart("(", RegexType.Group),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("=", RegexType.None),
                new RegexPart(".", RegexType.CharacterClass),
                new RegexPart("*", RegexType.Quantifier),
                new RegexPart("[A-Z]", RegexType.CharacterGroup),
                new RegexPart(")", RegexType.Group),
                new RegexPart("(", RegexType.Group),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("=", RegexType.None),
                new RegexPart(".", RegexType.CharacterClass),
                new RegexPart("*", RegexType.Quantifier),
                new RegexPart("[@#$%]", RegexType.CharacterGroup),
                new RegexPart(")", RegexType.Group),
                new RegexPart(".", RegexType.CharacterClass),
                new RegexPart("{6,20}", RegexType.QuantifierGroup),
                new RegexPart(")", RegexType.Group)
        );
        checkParse(regex, expected);
    }

    @Test
    public void patternHexadecimalColorCode() throws Exception {
        String regex = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("^", RegexType.Anchor),
                new RegexPart("#", RegexType.None),
                new RegexPart("(", RegexType.Group),
                new RegexPart("[A-Fa-f0-9]", RegexType.CharacterGroup),
                new RegexPart("{6}", RegexType.QuantifierGroup),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("[A-Fa-f0-9]", RegexType.CharacterGroup),
                new RegexPart("{3}", RegexType.QuantifierGroup),
                new RegexPart(")", RegexType.Group),
                new RegexPart("$", RegexType.Anchor)
        );
        checkParse(regex, expected);
    }

    @Test
    public void patternMail() throws Exception {
        String regex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("^", RegexType.Anchor),
                new RegexPart("[_A-Za-z0-9-]", RegexType.CharacterGroup),
                new RegexPart("+", RegexType.Quantifier),
                new RegexPart("(", RegexType.Group),
                new RegexPart("\\.", RegexType.None),
                new RegexPart("[_A-Za-z0-9-]", RegexType.CharacterGroup),
                new RegexPart("+", RegexType.Quantifier),
                new RegexPart(")", RegexType.Group),
                new RegexPart("*", RegexType.Quantifier),
                new RegexPart("@", RegexType.None),
                new RegexPart("[A-Za-z0-9]", RegexType.CharacterGroup),
                new RegexPart("+", RegexType.Quantifier),
                new RegexPart("(", RegexType.Group),
                new RegexPart("\\.", RegexType.None),
                new RegexPart("[A-Za-z0-9]", RegexType.CharacterGroup),
                new RegexPart("+", RegexType.Quantifier),
                new RegexPart(")", RegexType.Group),
                new RegexPart("*", RegexType.Quantifier),
                new RegexPart("(", RegexType.Group),
                new RegexPart("\\.", RegexType.None),
                new RegexPart("[A-Za-z]", RegexType.CharacterGroup),
                new RegexPart("{2,}", RegexType.QuantifierGroup),
                new RegexPart(")", RegexType.Group),
                new RegexPart("$", RegexType.Anchor)
        );
        checkParse(regex, expected);
    }

    @Test
    public void patternImageFileExtension() throws Exception {
        String regex = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("(", RegexType.Group),
                new RegexPart("[^\\s]", RegexType.CharacterGroup),
                new RegexPart("+", RegexType.Quantifier),
                new RegexPart("(", RegexType.Group),
                new RegexPart("\\.", RegexType.None),
                new RegexPart("(", RegexType.Group),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("i", RegexType.None),
                new RegexPart(")", RegexType.Group),
                new RegexPart("(", RegexType.Group),
                new RegexPart("j", RegexType.None),
                new RegexPart("p", RegexType.None),
                new RegexPart("g", RegexType.None),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("p", RegexType.None),
                new RegexPart("n", RegexType.None),
                new RegexPart("g", RegexType.None),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("g", RegexType.None),
                new RegexPart("i", RegexType.None),
                new RegexPart("f", RegexType.None),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("b", RegexType.None),
                new RegexPart("m", RegexType.None),
                new RegexPart("p", RegexType.None),
                new RegexPart(")", RegexType.Group),
                new RegexPart(")", RegexType.Group),
                new RegexPart("$", RegexType.None),
                new RegexPart(")", RegexType.Group)
        );
        checkParse(regex, expected);
    }

    @Test
    public void patternIPAddress() throws Exception {
        String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("^", RegexType.Anchor),
                new RegexPart("(", RegexType.Group),
                new RegexPart("[01]", RegexType.CharacterGroup),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("\\d", RegexType.CharacterClass),
                new RegexPart("\\d", RegexType.CharacterClass),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("2", RegexType.None),
                new RegexPart("[0-4]", RegexType.CharacterGroup),
                new RegexPart("\\d", RegexType.CharacterClass),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("2", RegexType.None),
                new RegexPart("5", RegexType.None),
                new RegexPart("[0-5]", RegexType.CharacterGroup),
                new RegexPart(")", RegexType.Group),
                new RegexPart("\\.", RegexType.None),
                new RegexPart("(", RegexType.Group),
                new RegexPart("[01]", RegexType.CharacterGroup),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("\\d", RegexType.CharacterClass),
                new RegexPart("\\d", RegexType.CharacterClass),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("2", RegexType.None),
                new RegexPart("[0-4]", RegexType.CharacterGroup),
                new RegexPart("\\d", RegexType.CharacterClass),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("2", RegexType.None),
                new RegexPart("5", RegexType.None),
                new RegexPart("[0-5]", RegexType.CharacterGroup),
                new RegexPart(")", RegexType.Group),
                new RegexPart("\\.", RegexType.None),
                new RegexPart("(", RegexType.Group),
                new RegexPart("[01]", RegexType.CharacterGroup),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("\\d", RegexType.CharacterClass),
                new RegexPart("\\d", RegexType.CharacterClass),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("2", RegexType.None),
                new RegexPart("[0-4]", RegexType.CharacterGroup),
                new RegexPart("\\d", RegexType.CharacterClass),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("2", RegexType.None),
                new RegexPart("5", RegexType.None),
                new RegexPart("[0-5]", RegexType.CharacterGroup),
                new RegexPart(")", RegexType.Group),
                new RegexPart("\\.", RegexType.None),
                new RegexPart("(", RegexType.Group),
                new RegexPart("[01]", RegexType.CharacterGroup),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("\\d", RegexType.CharacterClass),
                new RegexPart("\\d", RegexType.CharacterClass),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("2", RegexType.None),
                new RegexPart("[0-4]", RegexType.CharacterGroup),
                new RegexPart("\\d", RegexType.CharacterClass),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("2", RegexType.None),
                new RegexPart("5", RegexType.None),
                new RegexPart("[0-5]", RegexType.CharacterGroup),
                new RegexPart(")", RegexType.Group),
                new RegexPart("$", RegexType.Anchor)
        );
        checkParse(regex, expected);
    }

    @Test
    public void patternTimeFormat12() throws Exception {
        String regex = "(1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("(", RegexType.Group),
                new RegexPart("1", RegexType.None),
                new RegexPart("[012]", RegexType.CharacterGroup),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("[1-9]", RegexType.CharacterGroup),
                new RegexPart(")", RegexType.Group),
                new RegexPart(":", RegexType.None),
                new RegexPart("[0-5]", RegexType.CharacterGroup),
                new RegexPart("[0-9]", RegexType.CharacterGroup),
                new RegexPart("(", RegexType.Group),
                new RegexPart("\\s", RegexType.CharacterClass),
                new RegexPart(")", RegexType.Group),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("(", RegexType.Group),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("i", RegexType.None),
                new RegexPart(")", RegexType.Group),
                new RegexPart("(", RegexType.Group),
                new RegexPart("a", RegexType.None),
                new RegexPart("m", RegexType.None),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("p", RegexType.None),
                new RegexPart("m", RegexType.None),
                new RegexPart(")", RegexType.Group)
        );
        checkParse(regex, expected);
    }

    @Test
    public void patternTimeFormat24() throws Exception {
        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("(", RegexType.Group),
                new RegexPart("[01]", RegexType.CharacterGroup),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("[0-9]", RegexType.CharacterGroup),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("2", RegexType.None),
                new RegexPart("[0-3]", RegexType.CharacterGroup),
                new RegexPart(")", RegexType.Group),
                new RegexPart(":", RegexType.None),
                new RegexPart("[0-5]", RegexType.CharacterGroup),
                new RegexPart("[0-9]", RegexType.CharacterGroup)
        );
        checkParse(regex, expected);
    }

    @Test
    public void patternDateFormat() throws Exception {
        String regex = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("(", RegexType.Group),
                new RegexPart("0", RegexType.None),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("[1-9]", RegexType.CharacterGroup),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("[12]", RegexType.CharacterGroup),
                new RegexPart("[0-9]", RegexType.CharacterGroup),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("3", RegexType.None),
                new RegexPart("[01]", RegexType.CharacterGroup),
                new RegexPart(")", RegexType.Group),
                new RegexPart("/", RegexType.None),
                new RegexPart("(", RegexType.Group),
                new RegexPart("0", RegexType.None),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("[1-9]", RegexType.CharacterGroup),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("1", RegexType.None),
                new RegexPart("[012]", RegexType.CharacterGroup),
                new RegexPart(")", RegexType.Group),
                new RegexPart("/", RegexType.None),
                new RegexPart("(", RegexType.Group),
                new RegexPart("(", RegexType.Group),
                new RegexPart("1", RegexType.None),
                new RegexPart("9", RegexType.None),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("2", RegexType.None),
                new RegexPart("0", RegexType.None),
                new RegexPart(")", RegexType.Group),
                new RegexPart("\\d", RegexType.CharacterClass),
                new RegexPart("\\d", RegexType.CharacterClass),
                new RegexPart(")", RegexType.Group)
        );
        checkParse(regex, expected);
    }

    @Test
    public void patternHtmlTag() throws Exception {
        String regex = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("<", RegexType.None),
                new RegexPart("(", RegexType.Group),
                new RegexPart("\"", RegexType.None),
                new RegexPart("[^\"]", RegexType.CharacterGroup),
                new RegexPart("*", RegexType.Quantifier),
                new RegexPart("\"", RegexType.None),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("'", RegexType.None),
                new RegexPart("[^']", RegexType.CharacterGroup),
                new RegexPart("*", RegexType.Quantifier),
                new RegexPart("'", RegexType.None),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("[^'\">]", RegexType.CharacterGroup),
                new RegexPart(")", RegexType.Group),
                new RegexPart("*", RegexType.Quantifier),
                new RegexPart(">", RegexType.None)
        );
        checkParse(regex, expected);
    }

    @Test
    public void patternHtmlATag() throws Exception {
        String regex = "(?i)<a([^>]+)>(.+?)</a>";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("(", RegexType.Group),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("i", RegexType.None),
                new RegexPart(")", RegexType.Group),
                new RegexPart("<", RegexType.None),
                new RegexPart("a", RegexType.None),
                new RegexPart("(", RegexType.Group),
                new RegexPart("[^>]", RegexType.CharacterGroup),
                new RegexPart("+", RegexType.Quantifier),
                new RegexPart(")", RegexType.Group),
                new RegexPart(">", RegexType.None),
                new RegexPart("(", RegexType.Group),
                new RegexPart(".", RegexType.CharacterClass),
                new RegexPart("+", RegexType.Quantifier),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart(")", RegexType.Group),
                new RegexPart("<", RegexType.None),
                new RegexPart("/", RegexType.None),
                new RegexPart("a", RegexType.None),
                new RegexPart(">", RegexType.None)
        );
        checkParse(regex, expected);
    }

    @Test
    public void patternExtractHtmlLink() throws Exception {
        String regex = "\\s*(?i)href\\s*=\\s*(\\\"([^\"]*\\\")|'[^']*'|([^'\">\\s]+))";
        List<RegexPart> expected = Arrays.asList(
                new RegexPart("\\s", RegexType.CharacterClass),
                new RegexPart("*", RegexType.Quantifier),
                new RegexPart("(", RegexType.Group),
                new RegexPart("?", RegexType.Quantifier),
                new RegexPart("i", RegexType.None),
                new RegexPart(")", RegexType.Group),
                new RegexPart("h", RegexType.None),
                new RegexPart("r", RegexType.None),
                new RegexPart("e", RegexType.None),
                new RegexPart("f", RegexType.None),
                new RegexPart("\\s", RegexType.CharacterClass),
                new RegexPart("*", RegexType.Quantifier),
                new RegexPart("=", RegexType.None),
                new RegexPart("\\s", RegexType.CharacterClass),
                new RegexPart("*", RegexType.Quantifier),
                new RegexPart("(", RegexType.Group),
                new RegexPart("\\\"", RegexType.None),
                new RegexPart("(", RegexType.Group),
                new RegexPart("[^\"]", RegexType.CharacterGroup),
                new RegexPart("*", RegexType.Quantifier),
                new RegexPart("\\\"", RegexType.None),
                new RegexPart(")", RegexType.Group),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("'", RegexType.None),
                new RegexPart("[^']", RegexType.CharacterGroup),
                new RegexPart("*", RegexType.Quantifier),
                new RegexPart("'", RegexType.None),
                new RegexPart("|", RegexType.Alternation),
                new RegexPart("(", RegexType.Group),
                new RegexPart("[^'\">\\s]", RegexType.CharacterGroup),
                new RegexPart("+", RegexType.Quantifier),
                new RegexPart(")", RegexType.Group),
                new RegexPart(")", RegexType.Group)
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
            throw new IllegalStateException("SIZE is different\n" +
                    "EXPECTED [" + expected.size()+ "]:\n" +
                    Arrays.toString(expected.toArray()) +
                    "\n" +
                    "ACTUAL [" + actual.size()+ "]:\n" +
                    Arrays.toString(actual.toArray()));
        }

        Iterator<RegexPart> expIterator = expected.iterator();
        Iterator<RegexPart> actIterator = actual.iterator();

        while (expIterator.hasNext()) {
            RegexPart ex = expIterator.next();
            RegexPart ac = actIterator.next();
            if (!ex.equals(ac)) {
                throw new IllegalStateException("PARTS is different\n" +
                        "EXPECTED:\n" +
                        Arrays.toString(expected.toArray()) +
                        "\n" +
                        "ACTUAL:\n" +
                        Arrays.toString(actual.toArray()));
            }
        }
    }
}
