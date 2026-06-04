package UI;

import GameCode.Board;
import GameCode.Game;

import javax.swing.*;
import java.awt.*;

public class LayeredSquare {
    Board.square sq;
    public static void main(String[] args) {
        // Create a JFrame (the main window for the application).
        JFrame frame = new JFrame("JLayeredPane Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(100, 100);

        // Create a JLayeredPane to manage the layering of components.
        JLayeredPane layeredPane = new JLayeredPane();
        frame.add(layeredPane); // Add the JLayeredPane to the JFrame.


        // Create three colored panels to add to the layered pane.
        JPanel panel1 = new JPanel();
        panel1.setBounds(0,0,100,100);
        panel1.setOpaque(true);
        panel1.setBackground(Color.BLACK);
        JPanel panel2 = new JPanel();
        panel2.setBounds(0,0,100,100);
        panel2.setOpaque(false);
        JLabel coodinate = new JLabel("a5");
        coodinate.setForeground(Color.LIGHT_GRAY);
        panel1.add(coodinate);
        panel1.setLayout(new FlowLayout(FlowLayout.RIGHT, 1, 38));
        ImageIcon piece = new ImageIcon(new ImageIcon("ChessPieces/whitePawn.png").getImage().getScaledInstance(30, 45, Image.SCALE_DEFAULT));
        JLabel pieceIcon = new JLabel(piece);
        panel2.add(pieceIcon);

        // Add the panels to the layered pane with different layer values.
        // The layers determine the stacking order of the panels.
        layeredPane.add(panel1, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(panel2, JLayeredPane.PALETTE_LAYER);

        frame.setVisible(true); // Make the JFrame visible.
    }

    private static JPanel createColoredPanel(Color color, int x, int y, int width, int height) {
        // Create a colored JPanel with specified color and position.
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setBounds(x, y, width, height);
        panel.setOpaque(false);
        return panel;
    }
}
