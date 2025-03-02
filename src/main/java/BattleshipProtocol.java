package main.java;

import main.java.Coordinate;
import main.java.GameBoard;
import main.java.Battleship;
import main.java.StandbyShip;

import java.util.*;

/**
 * Game Protocol
 * The Protocol will mostly receive message and act toward the
 * GameBoard directly. Client/Server are expected to detect changes
 * directly via GameBoard
 * Basically how the program respond to changes
 * @author Justin Li
 * @version 0.0.1
 */
public class BattleshipProtocol {
    public static enum Mode {CLIENT, SERVER};
    public static enum GameCommand {VERSION, WHAT, SHIPS, STANDBY_SHIPS, GRID, READY, MARK, TIME, RESTART, ERROR, OK, NEXT, NAME, STATUS, CURRENT_TIME};

    public static final String VERSION = "0.0.1";
    public static final String SEPARATOR = "#";
    private GameBoard gameBoard;

    /**
     * Default Constructor
     * @param g GameBoard reflect changes from received message
     */
    public BattleshipProtocol(GameBoard g) {
        if (g.getGameStatus() != GameBoard.GameStatus.INIT) {
            throw new IllegalArgumentException("GameBoard passed in when it is not in init stage");
        }
        gameBoard = g;
    }

    private String[] parseCommand(String command) {
        return command.split(SEPARATOR);
    }

    /**
     * Main processor
     * @param command String message from peer
     * @return String response if necessary, null if no response needed
     */
    public String process(String command) {
        if(isOK(command)){
			return null;
		}
        StringBuilder response = new StringBuilder("%s".formatted(GameCommand.OK));
        String[] commands = parseCommand(command);
        GameCommand keyword = GameCommand.valueOf(commands[0]);
        if (keyword == GameCommand.ERROR) {
            throw new RuntimeException("Received ERROR %s".formatted(commands[1]));
        }
        try {
            switch (gameBoard.getGameStatus()) {
                // Right now, player could only ask/set for grid size
                // setShip on their board, and request state ready
                case INIT -> {
                    switch (keyword) {
                        case WHAT -> {
                            GameCommand askFor = GameCommand.valueOf(commands[1]);
                            switch (askFor) {
                                case VERSION -> response = new StringBuilder("%s#%s".formatted(GameCommand.VERSION, VERSION));
                                case GRID -> response = new StringBuilder("%s#%s#%s".formatted(GameCommand.GRID, gameBoard.getRow(), gameBoard.getCol()));
                                case STANDBY_SHIPS -> {
                                    response = new StringBuilder("%s#%s".formatted(GameCommand.STANDBY_SHIPS, gameBoard.getShips().size()));
                                    for (StandbyShip s : gameBoard.getShips()) {
                                        response.append("#%s".formatted(s.toString()));
                                    }
                                }
                                case SHIPS -> {
                                    response = new StringBuilder("%s#%s".formatted(GameCommand.SHIPS, gameBoard.getBattleShip(GameBoard.PlayerTarget.SELF).values().size()));
                                    for (Map.Entry<UUID, Battleship> ps : gameBoard.getBattleShip(GameBoard.PlayerTarget.SELF).entrySet()) {
                                        response.append("#%s#%s".formatted(ps.getValue().toString(), ps.getValue().getHead()));
                                    }
                                }
                                case READY -> response = new StringBuilder("%s#%s".formatted(GameCommand.READY, gameBoard.getPlayerStatus(GameBoard.PlayerTarget.SELF)));
                                case TIME -> response = new StringBuilder("%s#%s".formatted(GameCommand.TIME, gameBoard.getTimeLimit()));
                                case NAME -> response = new StringBuilder("%s#%s".formatted(GameCommand.NAME, gameBoard.getSelfName()));
                                case STATUS -> response = new StringBuilder("%s#%s".formatted(GameCommand.STATUS, gameBoard.getGameStatus()));
                                default -> throw new RuntimeException("Unhandled WHAT Command %s".formatted(command));
                            }
                        }
                        case GRID -> {
                            int row = Integer.parseInt(commands[1]);
                            int col = Integer.parseInt(commands[2]);
                            gameBoard.setGameBoard(row, col);
                        }
                        case STANDBY_SHIPS -> {
                            int num = Integer.parseInt(commands[1]);
                            List<StandbyShip> ships = new ArrayList<>();
                            for (int i = 2; i < num + 2; i++) {
                                ships.add(StandbyShip.ValueOf(commands[i]));
                            }
                            gameBoard.setShips(ships);
                        }
                        case SHIPS -> {
                            int num = Integer.parseInt(commands[1]);
                            for (int i = 2; i < num * 2 + 2; i += 2) {
                                gameBoard.addShip(StandbyShip.ValueOf(commands[i]), Coordinate.valueOf(commands[i + 1]), GameBoard.PlayerTarget.ENEMY);
                            }

                        }
                        case NAME -> gameBoard.setEnemyName(commands[1]);
                        case READY -> gameBoard.setPlayerStatus(GameBoard.PlayerStatus.READY, GameBoard.PlayerTarget.ENEMY);
                        case TIME -> gameBoard.setTimeLimit(Integer.parseInt(commands[1]));
                    }
                }
                case ENEMY_TURN -> {
                    switch (keyword) {
                        case MARK -> {
                            // Mark null when timeout
                            if (!commands[1].equals("null")) {
                                gameBoard.mark(Coordinate.valueOf(commands[1]));
                                gameBoard.setGameStatus(GameBoard.GameStatus.SELF_TURN);
                            } else {
                                gameBoard.setGameStatus(GameBoard.GameStatus.SELF_WON);
                            }
                        }
                        case CURRENT_TIME -> {
                            gameBoard.setEnemyCurrentTime(Integer.parseInt(commands[1]));
                        }
                        default -> {
                            throw new RuntimeException("Undefined keyword in enemy turn: %s".formatted(keyword));
                        }
                    }
                }
                case SELF_WON, ENEMY_WON -> {
                    switch (keyword) {
                        case RESTART -> {
                            // Set state to unready
                            gameBoard.setPlayerStatus(GameBoard.PlayerStatus.NOT_READY, GameBoard.PlayerTarget.ENEMY);
                        }
                        case STATUS -> {
                            GameBoard.GameStatus s = GameBoard.GameStatus.valueOf(commands[1]);
                            if (gameBoard.getGameStatus() == s) {
                                throw new Exception("Conflict game result!");
                            }
                        }
                        default -> {
                            throw new RuntimeException("Undefined keyword in finished stage: %s".formatted(keyword));
                        }
                    }
                }
                default -> {
                    throw new RuntimeException("Unhandled command %s".formatted(command));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = new StringBuilder("%s#%s".formatted(GameCommand.ERROR, e.getMessage()));
        }
        return response.toString();
    }

    /**
     * Initialize all necessary part of the game when app started
     * @param c Connectable, should have connected to the peer
     */
    public void init(Connectable c) {
        c.send("%s#%s".formatted(GameCommand.WHAT, GameCommand.GRID));
        c.send(process(c.receive()));
        c.send("%s#%s".formatted(GameCommand.WHAT, GameCommand.STANDBY_SHIPS));
        c.send(process(c.receive()));
        c.send("%s#%s".formatted(GameCommand.WHAT, GameCommand.TIME));
        c.send(process(c.receive()));
        c.send("%s#%s".formatted(GameCommand.WHAT, GameCommand.NAME));
        c.send(process(c.receive()));
    }

    /**
     * Exchange ships info
     * @param c Connectable, should have connected to peer
     */
    public void sendShips(Connectable c) {
        c.send("%s#%s".formatted(GameCommand.WHAT, GameCommand.SHIPS));
    }

    /**
     * Send a mark ship request
     * @param c Coordinate, location of the cell
     * @param connection Connectable, should have already connected to the peer
     */
    public static void markShip(Coordinate c, Connectable connection) {
        connection.send("%s#%s".formatted(GameCommand.MARK, c.toString()));
    }

    /**
     * Check if string is valid OK response (echo)
     * @param s String message
     * @return boolean true if it is OK, false otherwise
     */
    public static boolean isOK (String s) {
        return s.equals(GameCommand.OK.toString());
    }
}