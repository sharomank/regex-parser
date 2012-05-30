package com.sharomank.regex.parser;

import com.sharomank.regex.parser.enums.RegexTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for parse regular expression
 *
 * @author Roman Kurbangaliyev
 * @since 21.05.2012
 */
public class ParserHelper {
    private final String expression;
    private int previousIndex;
    private int currentIndex;
    private RegexTypes currentType;
    private List<RegexPart> result;

    public ParserHelper(String expression) {
        this.expression = expression;
        this.result = new ArrayList<RegexPart>();
    }

    public String getExpression() {
        return expression;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public RegexTypes getCurrentType() {
        return currentType;
    }

    public void setCurrentType(RegexTypes currentType) {
        this.currentType = currentType;
    }

    public List<RegexPart> getResult() {
        return result;
    }

    public void putCurrentRegexPart() {
        RegexPart part = new RegexPart(getToken(), getCurrentType());
        result.add(part);
        setCurrentType(null);
        updatePreviousIndex();
    }

    /**
     * Is end of expression
     *
     * @return <code>true</code> - expression is end, <code>false</code> - expression isn't end.
     */
    public boolean isEnd() {
        return getCurrentIndex() >= getExpression().length();
    }

    public void incrementCurrentIndex() {
        currentIndex++;
    }

    public void updatePreviousIndex() {
        previousIndex = currentIndex;
    }

    public String getToken() {
        return expression.substring(previousIndex, currentIndex);
    }

    public char getCurrentChar() {
        return expression.charAt(currentIndex);
    }

    public char getPreviousChar() {
        char previousChar = ' ';
        if (currentIndex > 0) {
            previousChar = expression.charAt(currentIndex - 1);
        }
        return previousChar;
    }
}
