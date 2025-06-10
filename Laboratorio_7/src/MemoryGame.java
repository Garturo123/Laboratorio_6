import javax.swing.Timer;

public class MemoryGame {
    private Card[] cards;
    private Card[] flippedCards = new Card[2]; // Usamos un array simple de tamaño fijo
    private int flippedCount = 0;
    
    public MemoryGame(Card[] cards) {
        this.cards = cards;
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
                Timer timer = new Timer(1000, e -> {
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
        for (Card card : cards) {
            if (!card.isFaceUp()) {
                return false;
            }
        }
        return true;
    }
}