package main.java;

import main.java.Controller;
import main.java.GameBoard;

import javax.swing.*;

public class VisualActionPanel extends JPanel {
    /**
     * Controller Reference
     */
    private Controller controller;

    /* Components */
    private JButton readyButton;
    private JButton settingButton;
    private JToolBar toolBar;

    public VisualActionPanel(Controller controller) {
        super();
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        toolBar = new JToolBar("Actions", JToolBar.VERTICAL);

        add(toolBar);
        readyButton = new JButton(GameBoard.PlayerStatus.READY.toString());
        settingButton = new JButton("Settings");
        toolBar.add(readyButton);
        toolBar.add(settingButton);

        setActions();
    }

    /**
     * Bind GUI action to controller
     */
    public void setActions() {
        readyButton.addActionListener(e -> controller.readyButtonPressed());
        settingButton.addActionListener(e -> controller.openConfigurationDialog());
    }
}
