import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class MemoryGameMain {
    public static void main(String[] args) {
        // Crear el marco principal
        int intentos = 10;
        String resultado = "";
        JFrame frame = new JFrame("Juego de Memoria");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        
         JPanel botones = new JPanel();
        botones.setLayout(new GridLayout(6, 6));

        JLabel oportunidades = new JLabel("Intentos restantes: " + intentos);
        oportunidades.setHorizontalAlignment(SwingConstants.CENTER);
        
        String[] nombresIconos = {
            "3M.png", "3M.png", "Balloon.png", "Balloon.png","Barbs.png", "Barbs.png","Barrel.png","Barrel.png",
            "Berserker.png", "Berserker.png", "BombTower.png", "BombTower.png", "Exe.png", "Exe.png", "Firecracker.png", "Firecracker.png",
            "Furnace.png", "Furnace.png", "Golem.png", "Golem.png", "Guards.png", "Guards.png", "Hog.png", "Hog.png",
            "Knight.png", "Knight.png", "MP.png", "MP.png", "Prince.png", "Prince.png", "Ram.png", "Ram.png",
            "Skarmy.png", "Skarmy.png", "Skellies.png", "Skellies.png"
        };
        
       
        Card[] cards = new Card[36];
        
        // Mezclar los valores
        for (int i = 0; i < nombresIconos.length; i++) {

            int randomIndex = (int)(Math.random() * nombresIconos.length);
            String temp = nombresIconos[i];

            nombresIconos[i] = nombresIconos[randomIndex];
            nombresIconos[randomIndex] = temp;
        }
        
        
        
        frame.add(oportunidades, BorderLayout.NORTH);
        frame.add(botones, BorderLayout.CENTER);
        frame.setSize(600, 800);
        
        // Crear el juego y las cartas
        MemoryGame game = new MemoryGame(cards, intentos, oportunidades, frame, resultado);
        
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new Card(nombresIconos[i]);
            cards[i].addActionListener(e -> {
                game.cardClicked((Card)e.getSource());
                if (game.isGameOver()) {
                    if(resultado.equals(" ganastes "))
                        JOptionPane.showMessageDialog(frame, "¡Felicidades! Has ganado el juego.");
                    else
                        JOptionPane.showMessageDialog(frame, "¡Lo sentimos! Has perdido el juego.");
                }
            });
            
            botones.add(cards[i]);
        }
        
        
        frame.setVisible(true);
    }
    
}
class MemoryGame {
    private Card[] cards;
    private Card[] flippedCards = new Card[2]; // Usamos un array simple de tamaño fijo
    private int flippedCount = 0;
    private int intentos;
    private JLabel oportunidades;
    private Frame frame;
    private String resultado;
    public MemoryGame(Card[] cards, int intentos, JLabel oportunidades, Frame frame, String resultado) {
        this.cards = cards;
        this.intentos = intentos;
        this.oportunidades = oportunidades;
        this.frame = frame;
        this.resultado = resultado;
    }
    
    public void cardClicked(Card card) {
        // Si la carta ya está mostrada o ya hay 2 cartas mostradas, no hacer nada
        if (card.isFaceUp() || flippedCount >= 2) {
            return;
        }
        
        // Voltear la carta
        card.showFace();
        flippedCards[flippedCount] = card;
        flippedCount++;
        
        // Si tenemos 2 cartas volteadas, verificar si son pareja
        if (flippedCount == 2) {
            if (flippedCards[0].getValue() == flippedCards[1].getValue()) {
                // Pareja encontrada - reiniciar
                flippedCount = 0;
                flippedCards[0] = null;
                flippedCards[1] = null;
            } else {
                // No son pareja - voltear ambas cartas después de un breve retraso
               intentos--;
               oportunidades.setText("Intentos restantes: " + intentos);
                Timer timer = new Timer(2000, e -> {
                    flippedCards[0].hideFace();
                    flippedCards[1].hideFace();
                    flippedCount = 0;
                    flippedCards[0] = null;
                    flippedCards[1] = null;
                    
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }
    
    public boolean isGameOver() {
        if (intentos==0){
            flippedCount = 3;
            return true;
        }
            
        for (Card card : cards) {
            if (!card.isFaceUp()) {
                return false;
            }
        }
        resultado = " ganastes ";
        return true;
    }
}