package com.sharomank.regex.parser;

import com.sharomank.regex.parser.enums.RegexTypes;

import java.text.MessageFormat;

/**
 * Struct class with result parser's
 *
 * @author Roman Kurbangaliyev
 * @since 21.05.2012
 */
public class RegexPart {
    private final String part;
    private final RegexTypes type;

    public RegexPart(String token, RegexTypes type) {
        this.part = token;
        this.type = type;
    }

    public String getPart() {
        return part;
    }

    public RegexTypes getType() {
        return type;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Regex part=''{0}'', type={1}", part, type.name());
    }
}