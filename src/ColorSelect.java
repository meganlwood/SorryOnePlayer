import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class ColorSelect extends JFrame{

	private static final long serialVersionUID = 1L;
	private int numPlayers;
	private JButton red, blue, green, yellow, confirm;
	private Color color;
	private Image r0, r1, b0, b1, g0, g1, y0, y1;
	
	public ColorSelect(int n) {
		super("Sorry!");
		numPlayers = n;
		initializeComponents();
		createGUI();
		addEvents();
		createMenuBar();
	}
	
	private void initializeComponents() {
		Toolkit t = Toolkit.getDefaultToolkit();
		r0 = t.getImage("resources/images/buttons/red_button00.png");
		r1 = t.getImage("resources/images/buttons/red_button01.png");
		b0 = t.getImage("resources/images/buttons/blue_button00.png");
		b1 = t.getImage("resources/images/buttons/blue_button01.png");
		g0 = t.getImage("resources/images/buttons/green_button00.png");
		g1 = t.getImage("resources/images/buttons/green_button01.png");
		y0 = t.getImage("resources/images/buttons/yellow_button00.png");
		y1 = t.getImage("resources/images/buttons/yellow_button01.png");
		
		red = new JButton("Red");
		red.setIcon(new BackgroundIcon(r0));
		
		blue = new JButton("Blue");
		blue.setIcon(new BackgroundIcon(b0));
		green = new JButton("Green");
		green.setIcon(new BackgroundIcon(g0));
		yellow = new JButton("Yellow");
		yellow.setIcon(new BackgroundIcon(y0));
		confirm = new JButton("Confirm");
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
		Image i = t.getImage("resources/images/panels/grey_panel.png");
		Image cursorImage = t.getImage("resources/images/cursors/cursorHand_blue.png");
		Point cursorHotSpot = new Point(0,0);
		Cursor customCursor = t.createCustomCursor(cursorImage, cursorHotSpot, "cursor");
		setCursor(customCursor);
		BackgroundPanel container = new BackgroundPanel(i);
		
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
		red.setFont(font);
		green.setFont(font);
		blue.setFont(font);
		yellow.setFont(font);
		setSize(640, 480);
		confirm.setEnabled(false);
		container.setLayout(new GridLayout(3,1));
		
		JPanel p1 = new JPanel();
		JLabel colorLabel = new JLabel("Select your color");
		colorLabel.setFont(font);
		colorLabel.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
		p1.add(colorLabel);
		p1.setOpaque(false);
		container.add(p1);
		JPanel p2 = new JPanel(new GridLayout(2,2, 20, 20));
		p2.add(red);
		p2.add(blue);
		p2.add(green);
		p2.add(yellow);
		p2.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));
		p2.setOpaque(false);
		container.add(p2);
		JPanel p3 = new JPanel(new GridLayout(1,2));
		JLabel emptyLabel = new JLabel();
		emptyLabel.setOpaque(false);
		JPanel p32 = new JPanel();
		confirm.setFont(font);
		p32.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
		p32.setOpaque(false);
		p32.add(confirm);
		p3.setOpaque(false);
		p3.add(emptyLabel);
		p3.add(p32);
		container.add(p3);
		
		add(container);
		/*
		//setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));\
		setLayout(new BorderLayout());
		JPanel p1 = new JPanel();
		JLabel selectLabel = new JLabel("Select your color");
		selectLabel.setFont(new Font("Arial", Font.BOLD, 40));
		p1.add(selectLabel);
		add(p1, BorderLayout.NORTH);
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(2,2, 20, 20));
		p2.add(red);
		p2.add(blue);
		p2.add(green);
		p2.add(yellow);
		add(p2, BorderLayout.CENTER);
		JPanel p3 = new JPanel();
		p3.setLayout(new GridLayout(1,5));
		for (int i = 0; i < 4; i++) p3.add(new JLabel());
		p3.add(confirm);
		add(p3, BorderLayout.SOUTH);
		*/
		
	}
	
	private void addEvents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ColorSelect f = this;
		class JButtonActionListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				confirm.setEnabled(true);
				JButton button = (JButton) e.getSource();
				red.setIcon(new BackgroundIcon(r0));
				blue.setIcon(new BackgroundIcon(b0));
				green.setIcon(new BackgroundIcon(g0));
				yellow.setIcon(new BackgroundIcon(y0));
				if (button == red) {
					color = Color.RED;
					red.setIcon(new BackgroundIcon(r1));
				}
				else if (button == blue) {
					color = Color.BLUE;
					blue.setIcon(new BackgroundIcon(b1));
				}
				else if (button == green) {
					color = Color.GREEN;
					green.setIcon(new BackgroundIcon(g1));
				}
				else if (button == yellow) {
					color = Color.YELLOW;
					yellow.setIcon(new BackgroundIcon(y1));
				}
			}
		}
		JButtonActionListener al = new JButtonActionListener();
		red.addActionListener(al);
		blue.addActionListener(al);
		green.addActionListener(al);
		yellow.addActionListener(al);
		confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//open gameplay class and stuff
				Gameplay gp = new Gameplay(numPlayers, color);
				gp.setLocationRelativeTo(f);
				gp.setVisible(true);
				setVisible(false);
				
			}
			
		});
	}
}
