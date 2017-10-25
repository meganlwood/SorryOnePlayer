import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MainMenu extends JFrame{

	private static final long serialVersionUID = 1L;
	//private JPanel menu;
	private JButton startButton;
	
	public MainMenu() {
		super("Sorry!");
		initializeComponents();
		createGUI();
		addEvents();
		createMenuBar();
	}
	
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu helpMenu = new JMenu("Help") {
			private static final long serialVersionUID = 1L;
			private KeyStroke accelerator;
			public KeyStroke getAccelerator() {
				return accelerator;
			}
			public void setAccelerator(KeyStroke keyStroke) {
				KeyStroke oldAccelerator = accelerator;
				this.accelerator = keyStroke;
				repaint();
				revalidate();
				firePropertyChange("accelerator", oldAccelerator, accelerator);
			}
		};
		JMenu scoresMenu = new JMenu("Scores") {
			private KeyStroke accelerator;
			public KeyStroke getAccelerator() {
				return accelerator;
			}
			public void setAccelerator(KeyStroke keyStroke) {
				KeyStroke oldAccelerator = accelerator;
				this.accelerator = keyStroke;
				repaint();
				revalidate();
				firePropertyChange("accelerator", oldAccelerator, accelerator);
			}
		};
		menuBar.add(helpMenu);
		menuBar.add(scoresMenu);
		helpMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		scoresMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		HelpMenu hm = new HelpMenu();
		ScoreTable st = new ScoreTable();
		helpMenu.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent arg0) {
				//HelpMenu hm = new HelpMenu();
				hm.setVisible(true);
			}
			public void menuCanceled(MenuEvent e) {}
			public void menuDeselected(MenuEvent e) {}
			
		});
		scoresMenu.addMenuListener(new MenuListener() {
			public void menuSelected(MenuEvent arg0) {
				//ScoreTable st = new ScoreTable();
				st.setVisible(true);
			}
			public void menuCanceled(MenuEvent e) {}
			public void menuDeselected(MenuEvent e) {}
			
		});
		setJMenuBar(menuBar);
	}
	
	private void initializeComponents() {
		//menu = new JPanel();
		startButton = new JButton("Start");
	}
	
	private void createGUI() {
		Toolkit t = Toolkit.getDefaultToolkit();
		Image i = t.getImage("resources/images/panels/grey_panel.png");
		BackgroundPanel container = new BackgroundPanel(i);
		Image cursorImage = t.getImage("resources/images/cursors/cursorHand_blue.png");
		Point cursorHotSpot = new Point(0,0);
		Cursor customCursor = t.createCustomCursor(cursorImage, cursorHotSpot, "cursor");
		setCursor(customCursor);
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
		font = font.deriveFont(Font.PLAIN, 20);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		
		setSize(640, 480);
		container.setLayout(new GridLayout(2, 1));
		JPanel p1 = new JPanel();
		Image sorryImage = t.getImage("resources/images/sorry.png");
		p1.setOpaque(false);
		p1.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
		p1.add(new JLabel(new ImageIcon(sorryImage)));
		container.add(p1);
		JPanel p2 = new JPanel();
		p2.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
		p2.setOpaque(false);
		startButton.setFont(font);
		p2.add(startButton);
		container.add(p2);
		add(container);
		
		/*
		setSize(640, 480);
		container.setLayout(new GridLayout(2,1));
		//Toolkit k = Toolkit.getDefaultToolkit();
		Image sorryImage = t.getImage("resources/images/sorry.png");
		JLabel sorryImageLabel = new JLabel("");
		sorryImageLabel.setIcon(new BackgroundIcon(sorryImage));
		//BackgroundPanel bp = new BackgroundPanel(sorryImage);
		//bp.setLayout(new FlowLayout());
		//bp.setBorder(BorderFactory.createEmptyBorder(500,20,20,20));
		JPanel p1 = new JPanel();
		//p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		p1.add(sorryImageLabel);
		p1.setOpaque(false);
		//add(p1);
		sorryImageLabel.setOpaque(false);
		container.add(p1);
		startButton.setFont(font);
		startButton.setOpaque(false);
		JPanel p = new JPanel();
		p.setOpaque(false);
		p.add(startButton);
		container.add(p);
		add(container);
		*/
		
		//add(menu, BorderLayout.CENTER);
	}
	
	private void addEvents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainMenu f = this;
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SelectPlayers sp = new SelectPlayers();
				sp.setLocationRelativeTo(f);
				sp.setVisible(true);
				setVisible(false);
			}
			
		});
	}
	
	
}
