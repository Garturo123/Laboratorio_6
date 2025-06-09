import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Juego extends JPanel {
    private Icon frontIcon;
    private Icon backIcon;
    private JLabel imageLabel;
    private boolean isFlipped;
    private boolean isMatched;

    public Juego(Icon frontIcon, Icon backIcon) {
        this.frontIcon = frontIcon;
        this.backIcon = backIcon;
        this.isFlipped = false;
        this.isMatched = false;
        
        initComponents();
        setupCard();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(80, 80));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(imageLabel, BorderLayout.CENTER);
        
        // Start with back icon
        imageLabel.setIcon(backIcon);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isMatched && !isFlipped) {
                    flip();
                    // Notify parent about the click
                    getParent().dispatchEvent(e);
                }
            }
        });
    }

    private void setupCard() {
        // Additional setup if needed
    }

    public void flip() {
        isFlipped = !isFlipped;
        imageLabel.setIcon(isFlipped ? frontIcon : backIcon);
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
        if (matched) {
            setBackground(Color.GREEN);
        }
    }

    public boolean matches(Juego other) {
        return this.frontIcon == other.frontIcon;
    }
}