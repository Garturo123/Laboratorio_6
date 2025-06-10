import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Card extends JButton {
    private String value;
    private boolean faceUp = false;
   
        
    public Card(String value) {
        this.value = "imagenes/" + value;
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
        ImageIcon imagen = new ImageIcon("imagenes/" +value);
        JLabel etiqueta = new JLabel(imagen);
    }
    
    public void hideFace() {
        faceUp = false;
        setText("");
    }
}