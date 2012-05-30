package com.sharomank.regex.parser.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Common chars of regular expression
 *
 * @author Roman Kurbangaliyev
 * @since 21.05.2012
 */
public enum RegexChars {
    Backslash('\\'),
    Or('|', RegexTypes.Alternation),
    Caret('^', RegexTypes.Anchor),
    Dollar('$', RegexTypes.Anchor),
    Start('A', RegexTypes.Anchor, true),
    EndLine('Z', RegexTypes.Anchor, true),
    End('z', RegexTypes.Anchor, true),
    PreviousMatch('G', RegexTypes.Anchor, true),
    WordBoundary('b', RegexTypes.Anchor, true),
    NonWordBoundary('B', RegexTypes.Anchor, true),
    GroupStart('(', RegexTypes.Group),
    GroupEnd(')', RegexTypes.Group),
    ZeroOrOne('?', RegexTypes.Quantifier),
    ZeroOrMore('*', RegexTypes.Quantifier),
    OneOrMore('+', RegexTypes.Quantifier),
    BracesStart('{', RegexTypes.Quantifier),
    BracesEnd('}', RegexTypes.Quantifier),
    Point('.', RegexTypes.CharacterClass),
    SquareBracketStart('[', RegexTypes.CharacterGroup),
    SquareBracketEnd(']', RegexTypes.CharacterGroup),
    Digit('d', RegexTypes.CharacterClass, true),
    NonDigit('D', RegexTypes.CharacterClass, true),
    Whitespace('s', RegexTypes.CharacterClass, true),
    NonWhitespace('S', RegexTypes.CharacterClass, true),
    Word('w', RegexTypes.CharacterClass, true),
    NonWord('W', RegexTypes.CharacterClass, true),
    Tab('t', RegexTypes.NonPrintable, true),
    CarriageReturn('r', RegexTypes.NonPrintable, true),
    LineFeed('n', RegexTypes.NonPrintable, true),
    Bell('a', RegexTypes.NonPrintable, true),
    Escape('e', RegexTypes.NonPrintable, true),
    FormFeed('f', RegexTypes.NonPrintable, true),
    VerticalTab('v', RegexTypes.NonPrintable, true);

    private final Character value;
    private final RegexTypes type;
    private final boolean workOnlyAfterBackslash;

    public static final List<Character> GROUPS = Arrays.asList(
            RegexChars.GroupStart.getValue(),
            RegexChars.GroupEnd.getValue()
    );

    public static final List<Character> QUANTIFIER_GROUP = Arrays.asList(
            RegexChars.BracesStart.getValue(),
            RegexChars.BracesEnd.getValue()
    );

    public static final List<Character> QUANTIFIERS = Arrays.asList(
            RegexChars.ZeroOrOne.getValue(),
            RegexChars.ZeroOrMore.getValue(),
            RegexChars.OneOrMore.getValue()
    );

    public static final List<Character> ANCHOR_AFTER_BACKSLASH = Arrays.asList(
            PreviousMatch.getValue(),
            WordBoundary.getValue(),
            NonWordBoundary.getValue()
    );

    public static final List<Character> CHARACTER_CLASSES_AFTER_BACKSLASH = Arrays.asList(
            RegexChars.Digit.getValue(),
            RegexChars.NonDigit.getValue(),
            RegexChars.Whitespace.getValue(),
            RegexChars.NonWhitespace.getValue(),
            RegexChars.Word.getValue(),
            RegexChars.NonWord.getValue()
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

    private RegexChars(Character ch) {
        this(ch, null);
    }

    private RegexChars(Character ch, RegexTypes type) {
        this(ch, type, false);
    }

    private RegexChars(Character ch, RegexTypes type, boolean workOnlyAfterBackslash) {
        this.value = ch;
        this.type = type;
        this.workOnlyAfterBackslash = workOnlyAfterBackslash;
    }

    public Character getValue() {
        return value;
    }

    public RegexTypes getType() {
        return type;
    }

    public boolean isWorkOnlyAfterBackslash() {
        return workOnlyAfterBackslash;
    }

    @Override
    public String toString() {
        return "RegexChars{" +
                "value=" + value +
                ", type=" + getType() +
                ", workOnlyAfterBackslash=" + isWorkOnlyAfterBackslash() +
                '}';
    }
}