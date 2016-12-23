import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HelpMenu extends JFrame{
	public HelpMenu() {
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/kenvector_future_thin.ttf"));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		font = font.deriveFont(Font.PLAIN, 15);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		Image i = Toolkit.getDefaultToolkit().getImage("resources/images/cards/card_beige.png");
		BackgroundPanel container = new BackgroundPanel(i);
		setSize(300,300);
		//container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.setLayout(new GridLayout(1,1));
		JScrollPane scrollPane = new JScrollPane(container);
		add(scrollPane);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		//JLabel l = new JLabel("Sorry! Help Menu");
		//l.setFont(font);
		//l.setBorder(BorderFactory.createEmptyBorder(10,10,0,0));
		//scrollPane.add(new JLabel("Sorry! Help Menu"));
		//container.add(new JLabel("Sorry"));
		//container.add(l);
		JTextArea info = new JTextArea();
		info.setOpaque(false);
		info.setFont(font);
		info.setLineWrap(true);
		info.setWrapStyleWord(true);
		info.setEditable(false);
		info.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
		info.setText("\nSorry! Help Menu \n\nStarting the game: "
				+ "\n" + "  -Press start"
				+ "\n  -Select color\n  -Select number of players"
				+ "\n\nPlaying the game:"
				+ "\n  -Press the card button to draw a card"
				+ "\n  -Choose a pawn to move, or let the computer make the move for you");
		
		container.add(info);
		add(scrollPane);
		
	}
}
