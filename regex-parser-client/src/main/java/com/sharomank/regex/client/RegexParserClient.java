package com.sharomank.regex.client;

import com.sharomank.regex.client.ui.ColorPane;
import com.sharomank.regex.parser.RegexParser;
import com.sharomank.regex.parser.RegexPart;
import com.sharomank.regex.parser.enums.RegexTypes;

import javax.swing.*;
import java.awt.*;

/**
 * Client class for demo
 */
public class RegexParserClient {
    private static final Color DARK_GREEN = new Color(0, 135, 20);
    private static final Color BROWN = new Color(200, 80, 0);

    private static Font CUSTOM_FONT = new Font(Font.MONOSPACED, Font.BOLD, 20);
    private static final int COLOR_PANE_WIDTH = 800;
    private static final int COLOR_PANE_HEIGHT = 400;

    public static void main(String arg[]) {
        String regex;
        regex = "\\A(\\w34\\d|234\\D234.\\t\\n\\r\\e\\v\\a\\f)\\W*@[a-z|A-Z_]+?\\.[a-zA-Z]{2,3}\\z";                              //email
//        regex = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";                              //email
//        regex = "^\\d{1,5}(\\.\\d{1,2})?$";                                           //number
//        regex = "^(\\d{4}[- ]){3}\\d{4}|\\d{16}$";                                    //credit card
//        regex = "^(([0-9])|([0-1][0-9])|([2][0-3])):(([0-9])|([0-5][0-9]))$";      //time
        System.out.println("Regex: " + regex);
        java.util.List<RegexPart> result = new RegexParser().parse(regex);
        System.out.println("regex  count = " + regex.length());
        System.out.println("result count = " + result.size());

        ColorPane pane = new ColorPane();
        pane.setFont(CUSTOM_FONT);
        for (RegexPart rt : result) {
            pane.append(getColor(rt.getType()), rt.getPart());
        }
        pane.setEditable(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("RegexParserClient");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new JScrollPane(pane));
        frame.setSize(COLOR_PANE_WIDTH, COLOR_PANE_HEIGHT);
        int xPoint = screenSize.width/2 - COLOR_PANE_WIDTH /2;
        int yPoint = screenSize.height/2 - COLOR_PANE_HEIGHT/2;
        frame.setLocation(xPoint, yPoint);
        frame.setVisible(true);
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
}