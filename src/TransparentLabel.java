import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class TransparentLabel extends JLabel {

    private Color textColor;
    private Font textFont;

    public TransparentLabel() {
        this(""); // Call the constructor with an empty string
        setOpaque(false); // Crucial for transparency
        textColor = Color.BLACK; // Default text color
        textFont = getFont(); // Use the default font
    }

    public TransparentLabel(String text) {
        super(text);
        setOpaque(false); // Crucial for transparency
        textColor = Color.BLACK; // Default text color
        textFont = getFont(); // Use the default font
    }

    public TransparentLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        setOpaque(false); // Crucial for transparency
        textColor = Color.BLACK; // Default text color
        textFont = getFont(); // Use the default font
    }


    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(0, 0, 0, 0)); // Set transparent background
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(textColor); // Set the text color
        g.setFont(textFont); // Set the font
        super.paintComponent(g); // Paint the label text
    }

    // Function to change the text
    public void setLabelText(String text) {
        setText(text);
        repaint(); // Important: Repaint the label to show the change
    }

    // Function to change the text color
    public void setTextColor(Color color) {
        textColor = color;
        repaint();
    }

    // Function to change the font
    public void setLabelFont(Font font) {
        textFont = font;
        repaint();
    }

    // Function to set horizontal alignment
    public void setLabelAlignment(int alignment) {
        setHorizontalAlignment(alignment);
        repaint();
    }

}