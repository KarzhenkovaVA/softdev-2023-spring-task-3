package task3;

import javax.swing.*;
import java.awt.*;

public class App {

    private static GameBoard gameBoard;
    private static Geometry geometry = new Geometry(10, 16, 32);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::createGUI);
    }

    private static void createGUI() {
        var frame = new JFrame("Hexagonal Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        var controlPanel = new ControlPanel() {
            @Override
            void restartGame() {
                recreateGameBoard(frame);
            }

            @Override
            void editSettings() {
                showSettingsDialog(frame);
            }
        };

        frame.add(controlPanel, BorderLayout.PAGE_START);
        createGameBoard(frame);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private static void createGameBoard(JFrame frame) {
        gameBoard = new GameBoard(geometry);
        frame.add(gameBoard, BorderLayout.CENTER);
        frame.pack();
    }

    private static void recreateGameBoard(JFrame frame) {
        frame.remove(gameBoard);
        createGameBoard(frame);
    }

    private static void showSettingsDialog(JFrame frame) {
        var title = "Game parameters";
        var settingsPanel = new SettingsPanel();
        settingsPanel.rows.setValue(geometry.numOfRows);
        settingsPanel.columns.setValue(geometry.numOfCellsInRow);
        settingsPanel.bombs.setValue(geometry.numOfBombs);

        int res = JOptionPane.showConfirmDialog(
                null, settingsPanel, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (res != JOptionPane.OK_OPTION) return;
        var rows = settingsPanel.rows.value();
        var columns = settingsPanel.columns.value();
        var bombs = settingsPanel.bombs.value();

        if (rows <= 0 || columns <= 0 || bombs <= 0 || bombs >= rows * columns) {
            JOptionPane.showMessageDialog(null, "Incorrect input");
            return;
        }

        geometry = new Geometry(rows, columns, bombs);

        recreateGameBoard(frame);
    }
}
