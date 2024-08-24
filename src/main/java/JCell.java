package main.java;

import main.java.UserSetting;
import main.java.Cell;
import main.java.GameBoard;

import javax.swing.*;
import java.awt.*;

/**
 * Custom Cell class with row/col information embed
 */
public class JCell extends JButton {
    private Cell cell;
    private GameBoard.PlayerTarget p;

    /**
     * Default constructor
     * @param c The cell in the GUI
     * @param p PlayerTarget
     */
    public JCell(Cell c, GameBoard.PlayerTarget p) {
        super();
        setOpaque(true);
        setBorderPainted(false);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cell = c;
        this.p = p;
    }

    /**
     * 
     * @return cell
     */
    public Cell getCell() {
        return cell;
    }

    /**
     * Update display of the Cell
     * based on PlayerTarget
     */
    public void updateDisplay() {
        setForeground(UserSetting.getMarkColor());
        switch(p) {
            case SELF -> {
                if (cell.hasShip()) {
                    setText("X");
                } else {
                    setText(" ");
                }
                if (cell.isDiscovered()) {
                    setBackground(UserSetting.getDiscoverColor());
                }
            }
            case ENEMY -> {
                if (cell.isDiscovered()) {
                    if (cell.hasShip()) {
                        setText("X");
                    } else {
                        setText(" ");
                    }
                    setBackground(UserSetting.getDiscoverColor());
                }
            }
        }
    }
}