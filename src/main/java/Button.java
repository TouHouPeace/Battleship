package main.java;
import javax.swing.*;

/**
 * This class generates the buttons used for the GUI
 */
public class Button {
    /**
     * Transparent button for color display
     * @param title Text on button
     * @return JButton ready for setBackground GUI
     */
    public static JButton transparentButton(String title) {
        JButton but = new JButton(title);
        but.setOpaque(true);
        but.setBorderPainted(false);
        return but;
    }
}