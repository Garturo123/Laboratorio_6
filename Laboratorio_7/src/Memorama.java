import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

public class Memorama extends JFrame {
    private JButton[][] cartas = new JButton[6][6];
    private ImageIcon[] iconos;
    private int[] valoresCartas;
    private int primeraCartaX = -1, primeraCartaY = -1;
    private int intentos = 0;
    private int paresEncontrados = 0;
    private final int MAX_INTENTOS = 10;
    private JLabel lblIntentos;
    private boolean bloqueo = false;

    public Memorama() {
        setTitle("Memorama");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel para las cartas
        JPanel panelCartas = new JPanel(new GridLayout(6, 6, 5, 5));
        panelCartas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Cargar imágenes desde carpeta externa
        cargarIconos();

        // Inicializar valores de las cartas
        valoresCartas = new int[36];
        inicializarValoresCartas();

        // Crear botones para las cartas
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                cartas[i][j] = new JButton();
                cartas[i][j].setPreferredSize(new Dimension(100, 100));
                cartas[i][j].addActionListener(new CartaListener(i, j));
                panelCartas.add(cartas[i][j]);
            }
        }

        // Panel inferior para información
        JPanel panelInferior = new JPanel();
        lblIntentos = new JLabel("Intentos: 0 / " + MAX_INTENTOS);
        panelInferior.add(lblIntentos);

        add(panelCartas, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarIconos() {
        // Cargar imágenes desde carpeta externa (ajusta la ruta según tu sistema)
        String rutaBase = "imagenes/";
        String[] nombresIconos = {
            "3M.png", "Balloon.png", "Barbs.png", "Barrel.png",
            "Berserker.png", "BombTower.png", "Exe.png", "Firecracker.png",
            "Furnace.png", "Golem.png", "Guards.png", "Hog.png",
            "Knight.png", "MP.png", "Prince.png", "Ram.png",
            "Skarmy.png", "Skellies.png"
        };

        iconos = new ImageIcon[18];
        for (int i = 0; i < nombresIconos.length; i++) {
            String rutaCompleta = rutaBase + nombresIconos[i];
            if (new File(rutaCompleta).exists()) {
                iconos[i] = new ImageIcon(rutaCompleta);
                // Escalar la imagen si es necesario
                Image img = iconos[i].getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                iconos[i] = new ImageIcon(img);
            } else {
                // Si no encuentra la imagen, usar un ícono por defecto
                System.err.println("No se encontró: " + rutaCompleta);
                iconos[i] = new ImageIcon(new ImageIcon("imagenes/default.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
            }
        }
    }

    private void inicializarValoresCartas() {
        // Asignar pares de valores (0-17, cada uno aparece 2 veces)
        for (int i = 0; i < 18; i++) {
            valoresCartas[2*i] = i;
            valoresCartas[2*i + 1] = i;
        }

        // Barajar los valores
        Random rand = new Random();
        for (int i = 0; i < 36; i++) {
            int j = rand.nextInt(36);
            int temp = valoresCartas[i];
            valoresCartas[i] = valoresCartas[j];
            valoresCartas[j] = temp;
        }
    }

    private void revelarCarta(int x, int y) {
        if (x >= 0 && x < 6 && y >= 0 && y < 6) {
            int indice = x * 6 + y;
            cartas[x][y].setIcon(iconos[valoresCartas[indice]]);
        }
    }

    private void ocultarCarta(int x, int y) {
        if (x >= 0 && x < 6 && y >= 0 && y < 6) {
            cartas[x][y].setIcon(null);
        }
    }

    private void verificarFinJuego() {
        if (paresEncontrados == 18) {
            JOptionPane.showMessageDialog(this, "¡Felicidades! Has encontrado todos los pares en " + intentos + " intentos.");
            reiniciarJuego();
        } else if (intentos >= MAX_INTENTOS) {
            JOptionPane.showMessageDialog(this, "¡Game Over! Has superado el máximo de intentos.");
            reiniciarJuego();
        }
    }

    private void reiniciarJuego() {
        intentos = 0;
        paresEncontrados = 0;
        lblIntentos.setText("Intentos: 0 / " + MAX_INTENTOS);
        inicializarValoresCartas();
        
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                cartas[i][j].setIcon(null);
                cartas[i][j].setEnabled(true);
            }
        }
        
        primeraCartaX = -1;
        primeraCartaY = -1;
        bloqueo = false;
    }

    private class CartaListener implements ActionListener {
        private int x, y;

        public CartaListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (bloqueo || cartas[x][y].getIcon() != null || !cartas[x][y].isEnabled()) {
                return;
            }

            revelarCarta(x, y);

            if (primeraCartaX == -1) {
                // Primera carta seleccionada
                primeraCartaX = x;
                primeraCartaY = y;
            } else {
                // Segunda carta seleccionada
                bloqueo = true;
                int indicePrimera = primeraCartaX * 6 + primeraCartaY;
                int indiceSegunda = x * 6 + y;

                if (valoresCartas[indicePrimera] == valoresCartas[indiceSegunda]) {
                    // Par encontrado
                    paresEncontrados++;
                    cartas[primeraCartaX][primeraCartaY].setEnabled(false);
                    cartas[x][y].setEnabled(false);
                    bloqueo = false;
                } else {
                    // No es par, ocultar después de un tiempo
                    Timer timer = new Timer(2000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ocultarCarta(primeraCartaX, primeraCartaY);
                            ocultarCarta(x, y);
                            bloqueo = false;
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }

                intentos++;
                lblIntentos.setText("Intentos: " + intentos + " / " + MAX_INTENTOS);
                primeraCartaX = -1;
                primeraCartaY = -1;
                verificarFinJuego();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Memorama().setVisible(true);
            }
        });
    }
}