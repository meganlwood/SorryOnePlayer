import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

class SorryPanel extends JPanel {
	private Image sorryImage;
	SorryPanel(Image i) {
		sorryImage = i;
	}
	protected void paintComponent(Graphics g) {
		g.drawImage(sorryImage, 5 * this.getWidth()/16, 4 * this.getHeight()/16, 6 * this.getWidth()/16, 3 *this.getHeight()/16, null);
		repaint();
		validate();
	}
}
