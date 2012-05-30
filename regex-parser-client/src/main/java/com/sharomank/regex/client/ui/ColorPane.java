package com.sharomank.regex.client.ui;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

public class ColorPane extends JTextPane {
    public void append(Color color, String str) {
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        AttributeSet set = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);

        setCaretPosition(getDocument().getLength());
        setCharacterAttributes(set, false);
        replaceSelection(str);
    }
}