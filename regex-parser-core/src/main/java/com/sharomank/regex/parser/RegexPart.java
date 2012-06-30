package com.sharomank.regex.parser;

import com.sharomank.regex.parser.enums.RegexType;

import java.text.MessageFormat;

/**
 * Class for store result parsing
 *
 * @author Roman Kurbangaliyev
 * @since 21.05.2012
 */
public class RegexPart {
    private final String part;
    private final RegexType type;

    public RegexPart(String token, RegexType type) {
        if (token == null || type == null) {
            throw new IllegalArgumentException();
        }

        this.part = token;
        this.type = type;
    }

    public String getPart() {
        return part;
    }

    public RegexType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegexPart regexPart = (RegexPart) o;
        if (!part.equals(regexPart.part)) {
            return false;
        } else if (type != regexPart.type) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = part.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Regex part=''{0}'', type={1}", part, type.name());
    }
}