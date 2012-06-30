package com.sharomank.regex.parser.enums;

import java.util.Arrays;
import java.util.List;

/**
 * General chars of regular expression
 *
 * @author Roman Kurbangaliyev
 * @since 21.05.2012
 */
public enum RegexChar {
    Backslash('\\'),
    Or('|', RegexType.Alternation),
    Caret('^', RegexType.Anchor),
    Dollar('$', RegexType.Anchor),
    Start('A', RegexType.Anchor, true),
    EndLine('Z', RegexType.Anchor, true),
    End('z', RegexType.Anchor, true),
    PreviousMatch('G', RegexType.Anchor, true),
    WordBoundary('b', RegexType.Anchor, true),
    NonWordBoundary('B', RegexType.Anchor, true),
    GroupStart('(', RegexType.Group),
    GroupEnd(')', RegexType.Group),
    ZeroOrOne('?', RegexType.Quantifier),
    ZeroOrMore('*', RegexType.Quantifier),
    OneOrMore('+', RegexType.Quantifier),
    BracesStart('{', RegexType.QuantifierGroup),
    BracesEnd('}', RegexType.QuantifierGroup),
    Point('.', RegexType.CharacterClass),
    SquareBracketStart('[', RegexType.CharacterGroup),
    SquareBracketEnd(']', RegexType.CharacterGroup),
    Digit('d', RegexType.CharacterClass, true),
    NonDigit('D', RegexType.CharacterClass, true),
    Whitespace('s', RegexType.CharacterClass, true),
    NonWhitespace('S', RegexType.CharacterClass, true),
    Word('w', RegexType.CharacterClass, true),
    NonWord('W', RegexType.CharacterClass, true),
    Tab('t', RegexType.NonPrintable, true),
    CarriageReturn('r', RegexType.NonPrintable, true),
    LineFeed('n', RegexType.NonPrintable, true),
    Bell('a', RegexType.NonPrintable, true),
    Escape('e', RegexType.NonPrintable, true),
    FormFeed('f', RegexType.NonPrintable, true),
    VerticalTab('v', RegexType.NonPrintable, true);

    private final Character value;
    private final RegexType type;
    private final boolean workOnlyAfterBackslash;

    public static final List<Character> GROUPS = Arrays.asList(
            RegexChar.GroupStart.getValue(),
            RegexChar.GroupEnd.getValue()
    );

    public static final List<Character> QUANTIFIERS = Arrays.asList(
            RegexChar.ZeroOrOne.getValue(),
            RegexChar.ZeroOrMore.getValue(),
            RegexChar.OneOrMore.getValue()
    );

    public static final List<Character> ANCHOR_AFTER_BACKSLASH = Arrays.asList(
            PreviousMatch.getValue(),
            WordBoundary.getValue(),
            NonWordBoundary.getValue()
    );

    public static final List<Character> ANCHOR_END = Arrays.asList(
            End.getValue(),
            EndLine.getValue()
    );

    public static final List<Character> CHARACTER_CLASSES_AFTER_BACKSLASH = Arrays.asList(
            RegexChar.Digit.getValue(),
            RegexChar.NonDigit.getValue(),
            RegexChar.Whitespace.getValue(),
            RegexChar.NonWhitespace.getValue(),
            RegexChar.Word.getValue(),
            RegexChar.NonWord.getValue()
    );

    public static final List<Character> NON_PRINTABLES = Arrays.asList(
            Tab.getValue(),
            CarriageReturn.getValue(),
            LineFeed.getValue(),
            Bell.getValue(),
            Escape.getValue(),
            FormFeed.getValue(),
            VerticalTab.getValue()
    );

    private RegexChar(Character ch) {
        this(ch, null);
    }

    private RegexChar(Character ch, RegexType type) {
        this(ch, type, false);
    }

    private RegexChar(Character ch, RegexType type, boolean workOnlyAfterBackslash) {
        this.value = ch;
        this.type = type;
        this.workOnlyAfterBackslash = workOnlyAfterBackslash;
    }

    public Character getValue() {
        return value;
    }

    public RegexType getType() {
        return type;
    }

    public boolean isWorkOnlyAfterBackslash() {
        return workOnlyAfterBackslash;
    }

    @Override
    public String toString() {
        return "RegexChar{" +
                "value=" + value +
                ", type=" + getType() +
                ", workOnlyAfterBackslash=" + isWorkOnlyAfterBackslash() +
                '}';
    }
}