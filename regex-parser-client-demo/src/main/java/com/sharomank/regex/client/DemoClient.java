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
import java.util.*;
import java.util.List;

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
        List<RegexPart> result = RegexParser.parse(regex);
        outputPatternUnitTestOnConsole(regex, result);
        for (RegexPart part : result) {
            pane.append(REGEX_COLOR_MAP.get(part.getType()), part.getPart());
        }
        pane.getDocument().addDocumentListener(documentListener);
        return pane.getDocument();
    }

    private static void outputPatternUnitTestOnConsole(final String regex, final List<RegexPart> result) {
        System.out.println("//Output code for unit test");
        System.out.println("@Test");
        System.out.println("public void patternTest() throws Exception {");
        System.out.println("\tString regex = \"" + convertRegexToString(regex) + "\";");
        System.out.println("\tList<RegexPart> expected = Arrays.asList(");
        for (int index = 0; index < result.size(); index++) {
            RegexPart part = result.get(index);
            String postfix = index < result.size()-1 ? "," : "";
            System.out.println("\t\t\tnew RegexPart(\""+ convertRegexToString(part.getPart()) + "\", RegexType." + part.getType().name() + ")" + postfix);
        }
        System.out.println("\t);");
        System.out.println("\tcheckParse(regex, expected);");
        System.out.println("}");
    }

    private static String convertRegexToString(String regex) {
        return regex.replace("\\","\\\\").replace("\"", "\\\"");
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