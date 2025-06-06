package laboratorio_7;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class Boton extends JButton implements ActionListener {
    
    //-------- Constructor con parametros para posicionar a los botones ------//
    public Boton( int pos_x, int pos_y, int ancho, int alto ){ 
        //Se coloca el boton en un lugar y se le da un tamanio
        setBounds(pos_x, pos_y, ancho, alto);
        
        //Se agrega el action listener en este caso la misma clase
        addActionListener( this );
    }
    //------------------------------------------------------------------------//
    
    //---------------------- Se asigna una el nombre del boton ---------------//
   public class Juego {
    private ImageIcon[] imagenes = new ImageIcon[36];  // 9 imágenes * 4 repeticiones = 36

    public Juego() {
        // Llenar el arreglo con 4 copias de cada imagen
        for (int i = 0; i < 4; i++) {
            imagenes[i] = new ImageIcon("src\\laboratorio_7\\Imagenes\\3M.png");
            imagenes[i + 4] = new ImageIcon("src\\laboratorio_7\\Imagenes\\Berserker.png");
            imagenes[i + 8] = new ImageIcon("src\\laboratorio_7\\Imagenes\\Firecracker.png");
            imagenes[i + 12] = new ImageIcon("src\\laboratorio_7\\Imagenes\\Golem.png");
            imagenes[i + 16] = new ImageIcon("src\\laboratorio_7\\Imagenes\\Hog.png");
            imagenes[i + 20] = new ImageIcon("src\\laboratorio_7\\Imagenes\\MP.png");
            imagenes[i + 24] = new ImageIcon("src\\laboratorio_7\\Imagenes\\Prince.png");
            imagenes[i + 28] = new ImageIcon("src\\laboratorio_7\\Imagenes\\Skarmy.png");
            imagenes[i + 32] = new ImageIcon("src\\laboratorio_7\\Imagenes\\Skellies.png");
        }

        // Mezclar el arreglo (Fisher–Yates)
        for (int i = 35; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            ImageIcon temp = imagenes[i];
            imagenes[i] = imagenes[j];
            imagenes[j] = temp;
        }
    }

    public void asignarImagenes(Boton[] botones) {
        for (int i = 0; i < botones.length; i++) {
            botones[i].setIcon(imagenes[i]);
        }
    }
}

    
   
    public void actionPerformed( ActionEvent e ){
        
        //Se asigna un color de letra color blanco
        setForeground(Color.WHITE);
    }
    //------------------------------------------------------------------------//
}
