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

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class SelectPlayers extends JFrame {

	private static final long serialVersionUID = 1L;
	private JRadioButton b1,b2,b3;
	private JButton confirm;
	private int numPlayers;
	
	public SelectPlayers() {
		super("Sorry!");
		initializeComponents();
		createGUI();
		addEvents();
		createMenuBar();
		numPlayers = 0;
	}
	
	private void initializeComponents() {
		b1 = new JRadioButton("2");
		b2 = new JRadioButton("3");
		b3 = new JRadioButton("4");
		ImageIcon off = new ImageIcon("resources/images/checkboxes/grey_circle.png");
		ImageIcon on = new ImageIcon("resources/images/checkboxes/grey_boxTick.png");
		//b1.setIcon(b0);
		b2.setIcon(off);
		b3.setIcon(off);
		b1.setIcon(off);
		
		ActionListener bgclicked = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b1.setIcon(off);
				b2.setIcon(off);
				b3.setIcon(off);
				JRadioButton b = (JRadioButton) e.getSource();
				b.setIcon(on);
			}
		};
		b1.addActionListener(bgclicked);
		b2.addActionListener(bgclicked);
		b3.addActionListener(bgclicked);
		ButtonGroup bg = new ButtonGroup();
		bg.add(b1);
		bg.add(b2);
		bg.add(b3);
		
		confirm = new JButton("Confirm");
		confirm.setEnabled(false);
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
	
	private void createGUI() {
		Toolkit t = Toolkit.getDefaultToolkit();
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
		font = font.deriveFont(Font.PLAIN, 25);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		
		setSize(640, 480);
		setLayout(new GridLayout(3,1));
		JPanel p1 = new JPanel();
		JLabel l = new JLabel("Select the number of players");
		l.setFont(font);
		l.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
		p1.add(l);
		JPanel p2 = new JPanel(new GridLayout(1,3));
		b1.setFont(font);
		b2.setFont(font);
		b3.setFont(font);
		JPanel p21 = new JPanel();
		JPanel p22 = new JPanel();
		JPanel p23 = new JPanel();
		p21.add(b1);
		p22.add(b2);
		p23.add(b3);
		p2.add(p21);
		p2.add(p22);
		p2.add(p23);
		JPanel p3 = new JPanel(new GridLayout(1,2));
		JPanel p31 = new JPanel();
		confirm.setFont(font);
		p31.add(confirm);
		p3.add(new JLabel());
		p3.add(p31);
		add(p1);
		add(p2);
		add(p3);
		
		
		/*
		setLayout(new GridLayout(4,1));
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		p2.setLayout(new GridLayout(1,3));
		p4.setLayout(new GridLayout(1,5));
		JLabel titleLabel = new JLabel("Select the number of players");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
		p1.add(titleLabel);
		JPanel p21 = new JPanel();
		JPanel p22 = new JPanel();
		JPanel p23 = new JPanel();
		p21.add(b1);
		p22.add(b2);
		p23.add(b3);
		p2.add(p21);
		p2.add(p22);
		p2.add(p23);
		for (int i = 0; i < 4; i++) p4.add(new Label(""));
		JPanel p45 = new JPanel();
		p45.setLayout(new BorderLayout());
		p45.add(confirm, BorderLayout.SOUTH);
		p4.add(p45);
		add(p1);
		add(p2);
		add(p3);
		add(p4);
		*/
	}
	
	private void addEvents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		class RadioButtonActionListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				confirm.setEnabled(true);
				JRadioButton button = (JRadioButton) e.getSource();
				if (button == b1) numPlayers = 2;
				else if (button == b2) numPlayers = 3;
				else if (button == b3) numPlayers = 4;
				
			}
		}
		RadioButtonActionListener actionListener = new RadioButtonActionListener();
		b1.addActionListener(actionListener);
		b2.addActionListener(actionListener);
		b3.addActionListener(actionListener);
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//bring up color select page
				//Sorry.manager.numPlayers = numPlayers;
				setVisible(false);
				ColorSelect cs = new ColorSelect(numPlayers);
				cs.setVisible(true);
				//setVisible(false);
			}
		});
		
	}
	
}
