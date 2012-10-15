package com.sharomank.regex.client;

import com.sharomank.regex.client.ui.ColorPane;
import com.sharomank.regex.parser.RegexParser;
import com.sharomank.regex.parser.RegexPart;
import com.sharomank.regex.parser.enums.RegexType;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Demo client class for demonstration how use {@link RegexParser}
 *
 * @author Roman Kurbangaliyev
 * @since 21.05.2012
 */
public class DemoClient {
    private static final String TITLE = "Regex Parser - Demo Client";

    private static final int COLOR_PANE_WIDTH = 800;
    private static final int COLOR_PANE_HEIGHT = 400;

    private static final ColorPane COLOR_PANE = new ColorPane();
    private static final Font CUSTOM_FONT = new Font(Font.MONOSPACED, Font.BOLD, 20);

    private static final Map<RegexType, Color> REGEX_COLOR_MAP;

    static {
        Color DARK_GREEN = new Color(0, 135, 20);
        Color BROWN = new Color(200, 80, 0);
        Map<RegexType, Color> colorMap = new HashMap<RegexType, Color>();
        colorMap.put(RegexType.None, Color.BLACK);
        colorMap.put(RegexType.Group, Color.BLUE);
        colorMap.put(RegexType.QuantifierGroup, Color.BLUE);
        colorMap.put(RegexType.Quantifier, Color.MAGENTA);
        colorMap.put(RegexType.Alternation, Color.MAGENTA);
        colorMap.put(RegexType.CharacterGroup, BROWN);
        colorMap.put(RegexType.CharacterClass, DARK_GREEN);
        colorMap.put(RegexType.Anchor, DARK_GREEN);
        colorMap.put(RegexType.ParseError, Color.RED);
        colorMap.put(RegexType.NonPrintable, Color.GRAY);
        REGEX_COLOR_MAP = Collections.unmodifiableMap(colorMap);
    }

    public static void main(String arg[]) {
        String defaultRegex = "^([a-zA-Z]+)|([0-9]{1,4})$";

        COLOR_PANE.setFont(CUSTOM_FONT);
        COLOR_PANE.getDocument().addDocumentListener(documentListener);
        COLOR_PANE.setText(defaultRegex);
        COLOR_PANE.setCaretPosition(defaultRegex.length());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(new JScrollPane(COLOR_PANE));
        frame.setSize(COLOR_PANE_WIDTH, COLOR_PANE_HEIGHT);
        int xPoint = screenSize.width / 2 - COLOR_PANE_WIDTH / 2;
        int yPoint = screenSize.height / 2 - COLOR_PANE_HEIGHT / 2;
        frame.setLocation(xPoint, yPoint);
        frame.setVisible(true);
    }

    private static Document parseRegex(String regex) {
        ColorPane pane = new ColorPane();
        pane.setFont(CUSTOM_FONT);
        java.util.List<RegexPart> result = RegexParser.parse(regex);
        System.out.println("regex  count = " + regex.length());
        System.out.println("result count = " + result.size());
        for (RegexPart rt : result) {
            System.out.println(rt);
            pane.append(REGEX_COLOR_MAP.get(rt.getType()), rt.getPart());
        }
        pane.getDocument().addDocumentListener(documentListener);
        return pane.getDocument();
    }

    private static final DocumentListener documentListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            parse(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            parse(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            parse(e);
        }

        private void parse(DocumentEvent e) {
            int caretPosition = COLOR_PANE.getCaretPosition();
            try {
                String text = e.getDocument().getText(0, e.getDocument().getLength());
                Document doc = parseRegex(text);
                COLOR_PANE.setDocument(doc);
                COLOR_PANE.setCaretPosition(Math.min(caretPosition, doc.getLength()));
                COLOR_PANE.invalidate();
            } catch (BadLocationException ex) {
                throw new IllegalStateException(ex.getMessage());
            }
        }
    };
}