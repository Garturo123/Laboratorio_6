import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MemoryGameMain {
    public static void main(String[] args) {
        // Crear el marco principal
        JFrame frame = new JFrame("Juego de Memoria");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new GridLayout(6, 6));
       
        String[] nombresIconos = {
            "3M.png", "3M.png", "Balloon.png", "Balloon.png","Barbs.png", "Barbs.png","Barrel.png","Barrel.png",
            "Berserker.png", "Berserker.png", "BombTower.png", "BombTower.png", "Exe.png", "Exe.png", "Firecracker.png", "Firecracker.png",
            "Furnace.png", "Furnace.png", "Golem.png", "Golem.png", "Guards.png", "Guards.png", "Hog.png", "Hog.png",
            "Knight.png", "Knight.png", "MP.png", "MP.png", "Prince.png", "Prince.png", "Ram.png", "Ram.png",
            "Skarmy.png", "Skarmy.png", "Skellies.png", "Skellies.png"
        };
        // Crear las cartas (8 pares)
        int[] values = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14, 15, 15, 16, 16, 17, 17, 18, 18};
        Card[] cards = new Card[36];
        
        // Mezclar los valores
        for (int i = 0; i < nombresIconos.length; i++) {

            int randomIndex = (int)(Math.random() * nombresIconos.length);
            String temp = nombresIconos[i];
            nombresIconos[i] = nombresIconos[randomIndex];
            nombresIconos[randomIndex] = temp;
        }
        
        // Crear el juego y las cartas
        MemoryGame game = new MemoryGame(cards);
        
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new Card(nombresIconos[i]);
            cards[i].addActionListener(e -> {
                game.cardClicked((Card)e.getSource());
                if (game.isGameOver()) {
                    JOptionPane.showMessageDialog(frame, "¡Felicidades! Has ganado el juego.");
                }
            });
            frame.add(cards[i]);
        }
        
        frame.setVisible(true);
    }
}