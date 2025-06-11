import javax.swing.ImageIcon;
import javax.swing.JButton;


public class Card extends JButton {
    private String value;
    private boolean faceUp = false;
   
        
    public Card(String value) {
        this.value = value;
        hideFace();
    }
    
    public String getValue() {
        return value;
    }
    
    public boolean isFaceUp() {
        return faceUp;
    }
    
    public void showFace() {
        faceUp = true;
        ImageIcon button = new ImageIcon("imagenes/" + value);
        setIcon(button);
    }
    
    public void hideFace() {
        faceUp = false;
        ImageIcon button = new ImageIcon("imagenes/back.png");
        setIcon(button);
    }
}