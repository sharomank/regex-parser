package com.sharomank.regex.parser.enums;

/**
 * Regular expression types
 *
 * @author Roman Kurbangaliyev
 * @since 21.05.2012
 */
public enum RegexTypes {
    None,
    Alternation,
    Anchor,
    Group,
    CharacterClass,
    CharacterGroup,
    Quantifier,
    QuantifierGroup,
    NonPrintable,
    ParseError
}