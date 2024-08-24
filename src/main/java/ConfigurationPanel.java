package main.java;
import main.java.Controller;
import main.java.Button;
import main.java.UserSetting;

import javax.swing.*;
import java.awt.*;

/**
 * Handles the config panel part of the UI
 */
public class ConfigurationPanel extends JFrame {
    /* Components */
    private JButton hiddenCellColor;
    private JButton discoverCellColor;
    private JButton markColor;
    private JButton gridSize;
    private JTextField timeLimit;

    /**
     * Controller Reference
     */
    private Controller control;

    public ConfigurationPanel(Controller control) {
        this.control = control;

        // Set layout
        setTitle("Configuration");
        setLayout(new GridLayout(4, 2));

        discoverCellColor = Button.transparentButton("Click");
        markColor = Button.transparentButton("Click");
        gridSize = Button.transparentButton("Click to view/set");
        gridSize = new JButton("Click");
        timeLimit = new JTextField();

        // Add all components.
        add(new JLabel("\tDiscover Cell Color:"));
        add(discoverCellColor);
        add(new JLabel("\tShip Mark Color:"));
        add(markColor);
        add(new JLabel("Set Grid Size"));
        add(gridSize);
        add(new JLabel("Time limit per round"));
        add(timeLimit);
        pack();

        setLocationRelativeTo(null);
        updateSettings();
        setActions();
    }

    /**
     * Update panel with latest settings
     */
    public void updateSettings() {
        discoverCellColor.setBackground(UserSetting.getDiscoverColor());
        markColor.setBackground(UserSetting.getMarkColor());
        timeLimit.setText(String.valueOf(UserSetting.getTimeLimit()));
    }

    /**
     * Bind action to Controller
     */
    private void setActions() {
        discoverCellColor.addActionListener(e -> control.setDiscoverColor());
        markColor.addActionListener(e -> control.setMarkColor());
        gridSize.addActionListener(e -> control.setGridSize());
        timeLimit.addActionListener(e -> control.setTimeLimit(timeLimit.getText()));
    }
}