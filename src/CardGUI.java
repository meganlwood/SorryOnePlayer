import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class CardGUI extends JDialog {
	public CardGUI(Card c) {
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/kenvector_future.ttf"));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		font = font.deriveFont(Font.PLAIN, 30);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		setSize(200,300);
		setLocation(300,300);
		Toolkit t = Toolkit.getDefaultToolkit();
		Image bg = t.getImage("resources/images/cards/card_brown.png");
		BackgroundPanel p = new BackgroundPanel(bg);
		JLabel numberLabel;
		if (c.getNumber() != 0 ) numberLabel = new JLabel(c.getNumber() + "", SwingConstants.CENTER);
		else numberLabel = new JLabel("Sorry!", SwingConstants.CENTER);
		JTextArea infoLabel = new JTextArea(c.getInfo() + "");
		infoLabel.setEditable(false);
		infoLabel.setLineWrap(true);
		infoLabel.setWrapStyleWord(true);
		infoLabel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		p.setLayout(new GridLayout(3,1));
		numberLabel.setFont(font);
		font = font.deriveFont(Font.PLAIN, 15);
		infoLabel.setFont(font);
		p.add(numberLabel);
		infoLabel.setOpaque(false);
		p.add(infoLabel);
		p.add(new JLabel());
		add(p);
		
	}
}
