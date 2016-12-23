import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
	private Image bg;
	public BackgroundPanel(Image i) {
		bg = i;
	}
	
	protected void paintComponent(Graphics g) {
		//super.paintComponent(g);
		g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), null);
		//g.drawImage(bg, 0, 0, width, height, observer)
		repaint();
		validate();
	}
}
