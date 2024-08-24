package main.java;

import javax.swing.*;
import java.awt.*;

public class ShipBoard extends JPanel {
    /* component */
    private JPanel enemyGrid;
    private JPanel selfGrid;
    private JCell[][] enemyCell;
    private JCell[][] selfCell;

    /**
     * Controller Reference
     */
    private Controller controller;

    public ShipBoard(Controller controller) {
        super();
        setLayout(new GridLayout(1, 3));
        this.controller = controller; // Fixed: Use controller instead of Controller
    }

    /**
     * GUI board initialization
     * @param row map row
     * @param col map col
     * @param pb PlayerBoard
     */
    public void init(int row, int col, PlayerBoard pb) {
        if (selfGrid == null && enemyGrid == null) {
            selfGrid = new JPanel(new GridLayout(row, col, 1, 1));
            enemyGrid = new JPanel(new GridLayout(row, col, 1, 1));
            selfGrid.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            enemyGrid.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            selfGrid.setBackground(Color.WHITE);
            enemyGrid.setBackground(Color.WHITE);
            JPanel indicator = new JPanel();
            indicator.setLayout(new FlowLayout(FlowLayout.CENTER));
            indicator.add(new JLabel("<HTML>←Your Board<br>Enemy Board →</HTML>"));
            add(selfGrid);
            add(indicator);
            add(enemyGrid);
        }
        reset(row, col, pb);
    }

    /**
     * Reset the map of the game
     * @param row int map row
     * @param col int map col
     * @param pb PlayerBoard with status
     */
    public void reset(int row, int col, PlayerBoard pb) {
        selfGrid.removeAll();
        enemyGrid.removeAll();
        selfCell = new JCell[row][col];
        enemyCell = new JCell[row][col];
        for (int i = 0; i < row; i ++) {
            for (int j = 0; j < col; j++) {
                Coordinate c = new Coordinate(i, j);
                selfCell[i][j] = new JCell(pb.getCell(c, GameBoard.PlayerTarget.SELF), GameBoard.PlayerTarget.SELF);
                selfCell[i][j].addActionListener(e -> controller.selfCellClicked(c)); // Fixed: controller instead of Controller
                enemyCell[i][j] = new JCell(pb.getCell(c, GameBoard.PlayerTarget.ENEMY), GameBoard.PlayerTarget.ENEMY);
                enemyCell[i][j].addActionListener(e -> controller.enemyCellClicked(c)); // Fixed: controller instead of Controller

                selfGrid.add(selfCell[i][j]);
                enemyGrid.add(enemyCell[i][j]);
            }
        }
        selfGrid.revalidate();
        enemyGrid.revalidate();
        selfGrid.repaint();
        enemyGrid.repaint();
    }

    /**
     * returns the JCell so that the controller has control over it
     * @param c Coordinate of the cell
     * @param p PlayerTarget, used to identify which cell to return
     * @return JCell Cell on the map
     */
    public JCell getJCell(Coordinate c, GameBoard.PlayerTarget p) {
        if (p == GameBoard.PlayerTarget.ENEMY) {
            return enemyCell[c.getX()][c.getY()];
        } else {
            return selfCell[c.getX()][c.getY()];
        }
    }

    /**
     * Update Grid display
     * @param p PlayerTarget decide which player's board to update
     */
    public void updateBoard(GameBoard.PlayerTarget p) {
        switch(p) {
            case SELF -> {
                for (JCell[] i : selfCell) {
                    for (JCell j : i) {
                        j.updateDisplay();
                    }
                }
            }
            case ENEMY -> {
                for (JCell[] i : enemyCell) {
                    for (JCell j : i) {
                        j.updateDisplay();
                    }
                }
            }
        }
    }
}
