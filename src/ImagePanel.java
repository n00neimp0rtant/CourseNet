import java.awt.*;
import javax.swing.*;

public class ImagePanel extends JPanel {
	Image image;

    public ImagePanel(Image image) { 
        this.image = image; 
    }

    public void paintComponent(Graphics g) { 
        super.paintComponent(g);  // Paint background

        // Draw image at its natural size first. 
        g.drawImage(image, 0, 0, this); //85x62 image

        // Now draw the image scaled. 
        //g.drawImage(image, 90, 0, 300, 62, this); 
    } 
} 