import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;

public class BackgroundIcon implements Icon{
	private Image bg, icon;
	public BackgroundIcon(Image background) {
		bg = background;
		icon = null;
	}
	
	public BackgroundIcon(Image background, Image icon) {
		bg = background;
		this.icon = icon;
	}
	
	public Image getBackground() {
		return bg;
	}
	
	@Override
	public int getIconHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getIconWidth() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		// TODO Auto-generated method stub
		c.repaint();
		c.validate();
		g.drawImage(bg, 0, 0, c.getWidth(), c.getHeight(), null);
		if (icon != null) g.drawImage(icon, c.getWidth()/3, c.getHeight()/3, 2*c.getWidth()/5, 2*c.getHeight()/5, null);
	}
}
