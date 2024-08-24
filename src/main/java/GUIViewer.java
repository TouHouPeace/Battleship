package main.java;

import main.java.ConfigurationPanel;
import main.java.JCell;
import main.java.ShipBoard;
import main.java.StatusPanel;
import main.java.TopMenuBar;
import main.java.VisualActionPanel;

import javax.swing.*;
import java.awt.*;

public class GUIViewer extends JFrame {
    /**
     * Reference of the controller, for notification purpose
     */
    Controller controller;

    /**
     * Top Menu Bar section
     */
    TopMenuBar topMenuBar;

    /**
     * Left side quick tool bar
     */
    VisualActionPanel visualActionPanel;

    /**
     * Main ship section
     */
    ShipBoard shipBoard;

    /**
     * Lower bar of the status
     */
    StatusPanel statusPanel;

    /**
     * User Configuration panel
     */
    ConfigurationPanel configurationPanel;

    /**
     * Main layout panel
     */
    private JPanel mainPanel;

    public GUIViewer(Controller guiController) {
        // Creating instance of JFrame
        super("BattleShip");
        this.controller = guiController;
        setSize(1600, 1000);
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        // Init Layout
        init();
    }

    /**
     * Initialization subcomponents and set it to the corresponded location of the layout
     */
    private void init() {
        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // Set their locations
        setFrame();
        setCenter();
        setPageLeft();
        setPageEnd();

        configurationPanel = new ConfigurationPanel(controller);
    }


    /* The method below are layout settings */
    private void setPageLeft() {
        visualActionPanel = new VisualActionPanel(controller);
        mainPanel.add(visualActionPanel, BorderLayout.LINE_START);
    }

    private void setPageEnd() {
        statusPanel = new StatusPanel();
        mainPanel.add(statusPanel, BorderLayout.PAGE_END);
    }

    private void setFrame() {
        topMenuBar = new TopMenuBar(controller);
        setJMenuBar(topMenuBar);
    }

    private void setCenter() {
        shipBoard = new ShipBoard(controller);
        mainPanel.add(shipBoard, BorderLayout.CENTER);
    }
}