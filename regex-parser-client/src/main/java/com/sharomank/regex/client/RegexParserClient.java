package com.sharomank.regex.client;

import com.sharomank.regex.client.ui.ColorPane;
import com.sharomank.regex.parser.RegexParser;
import com.sharomank.regex.parser.RegexPart;
import com.sharomank.regex.parser.enums.RegexTypes;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import java.awt.*;

/**
 * Client class for demonstration how use {@link RegexParser}
 *
 * @author Roman Kurbangaliyev
 * @since 21.05.2012
 */
public class RegexParserClient {
    private static final Color DARK_GREEN = new Color(0, 135, 20);
    private static final Color BROWN = new Color(200, 80, 0);

    private static Font CUSTOM_FONT = new Font(Font.MONOSPACED, Font.BOLD, 20);
    private static final int COLOR_PANE_WIDTH = 800;
    private static final int COLOR_PANE_HEIGHT = 400;

    private static final ColorPane COLOR_PANE = new ColorPane();

    public static void main(String arg[]) {
        COLOR_PANE.setFont(CUSTOM_FONT);
        COLOR_PANE.getDocument().addDocumentListener(documentListener);
        String startRegex = "^([a-zA-Z]+)|([0-9]{1,4})$";
        COLOR_PANE.setText(startRegex);
        COLOR_PANE.setCaretPosition(startRegex.length());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Regex Parser Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
            pane.append(getColor(rt.getType()), rt.getPart());
        }
        pane.getDocument().addDocumentListener(documentListener);
        return pane.getDocument();
    }

    private static Color getColor(RegexTypes type) {
        switch (type) {
            case None:
                return Color.BLACK;
            case Group:
                return Color.BLUE;
            case QuantifierGroup:
                return Color.BLUE;
            case Quantifier:
                return Color.MAGENTA;
            case Alternation:
                return Color.MAGENTA;
            case CharacterGroup:
                return BROWN;
            case CharacterClass:
                return DARK_GREEN;
            case Anchor:
                return DARK_GREEN;
            case ParseError:
                return Color.RED;
            case NonPrintable:
                return Color.GRAY;
            default:
                throw new UnsupportedOperationException("Need select color for type=" + type.name());
        }
    }

    private static DocumentListener documentListener = new DocumentListener() {
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
                COLOR_PANE.setCaretPosition(Math.min(caretPosition, text.length()));
                COLOR_PANE.invalidate();
            } catch (BadLocationException ex) {
                throw new IllegalStateException(ex.getMessage());
            }
        }
    };
}