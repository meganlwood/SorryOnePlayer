import java.awt.Color;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class Gameplay extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel[][] board;
	private int yellowHome, yellowStart, blueHome, blueStart, greenHome, greenStart, redHome, redStart;
	private JButton cardButton;
	private Deck deck;
	private Card currentCard;
	private Player user;
	private Player currentPlayer;
	private boolean choosingPawn;
	private JLabel[][] startingBoard;
	private boolean maySelectStart;
	private boolean goAgain;
	private boolean sorryChoosingPawn;
	private int sevenFirstMoveAmount;
	private boolean switchingPawns;
	private int[] switchingPawnsCoords;
	private boolean searchingForSafeSpaceCoords;
	private Image blueTile, greenTile, greyTile, redTile, yellowTile;
	private Image blueSlide, greenSlide, redSlide, yellowSlide;
	private Image bluePawn, greenPawn, redPawn, yellowPawn;
	private Image bluePanel, greenPanel, greyPanel, redPanel, yellowPanel;
	private Font font;
	private SorryPanel container;
	private Image highlightPanel;
	Player[] players;
	private boolean readyToMove;
	
	public Gameplay(int n, Color c) {
		super("Sorry!");
		choosingPawn = false;
		maySelectStart = false;
		goAgain = false;
		sevenFirstMoveAmount = -1;
		switchingPawns = false;
		readyToMove = false;
		initializePlayers(n, c);
		initializeComponents();
		createGUI();
		addEvents();
		//createMenuBar();
		if (c == Color.RED) highlightPanel = redPanel;
		else if (c == Color.BLUE) highlightPanel = bluePanel;
		else if (c == Color.GREEN) highlightPanel = greenPanel;
		else if (c == Color.YELLOW) highlightPanel = yellowPanel;
		
		
		go();
	}
	
	private void go() {
		currentPlayer = user;
	}
	
	private void initializePlayers(int n, Color c) {
		Color[] colors = new Color[4];
		players = new Player[n];
		colors[0] = Color.BLUE;
		colors[1] = Color.RED;
		colors[2] = Color.GREEN;
		colors[3] = Color.YELLOW;
		players[0] = new Player(c, false);
		if (n == 2) {
			if (c == Color.BLUE) players[1] = new Player(Color.GREEN, true);
			else if (c == Color.RED) players[1] = new Player(Color.YELLOW, true);
			else if (c == Color.GREEN) players[1] = new Player(Color.BLUE, true);
			else if (c == Color.YELLOW) players[1] = new Player(Color.RED, true);
		}
		else {
			int place = 1;
			int i = 0;
			while (i < 4 && place < n){
				if (c != colors[i]) {
					players[place] = new Player(colors[i], true);
					place++;
				}
				i++;
			}
		}
		
	}
	
	private void initializeComponents() {
		user = players[0];
		yellowHome = 0;
		blueHome = 0;
		greenHome = 0;
		redHome = 0;
		yellowStart = 4;
		greenStart = 4;
		blueStart = 4;
		redStart = 4;

		Toolkit t = Toolkit.getDefaultToolkit();
		blueTile = t.getImage("resources/images/tiles/blue_tile.png");
		greenTile = t.getImage("resources/images/tiles/green_tile.png");
		redTile = t.getImage("resources/images/tiles/red_tile.png");
		yellowTile = t.getImage("resources/images/tiles/yellow_tile.png");
		greyTile = t.getImage("resources/images/tiles/grey_tile.png");
		
		blueSlide = t.getImage("resources/images/sliders/blue_slide.png");
		greenSlide = t.getImage("resources/images/sliders/green_slide.png");
		redSlide = t.getImage("resources/images/sliders/red_slide.png");
		yellowSlide = t.getImage("resources/images/sliders/yellow_slide.png");
		
		bluePawn = t.getImage("resources/images/pawns/blue_pawn.png");
		greenPawn = t.getImage("resources/images/pawns/green_pawn.png");
		redPawn = t.getImage("resources/images/pawns/red_pawn.png");
		yellowPawn = t.getImage("resources/images/pawns/yellow_pawn.png");
		
		bluePanel = t.getImage("resources/images/panels/blue_panel.png");
		greenPanel = t.getImage("resources/images/panels/green_panel.png");
		redPanel = t.getImage("resources/images/panels/red_panel.png");
		yellowPanel = t.getImage("resources/images/panels/yellow_panel.png");
		
		Image sorryImage = t.getImage("resources/images/sorry.png");
		container = new SorryPanel(sorryImage);
		
		font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/kenvector_future.ttf"));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		font = font.deriveFont(Font.PLAIN, 8);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		
		board = new JLabel[16][16];
		
		cardButton = new JButton();
		cardButton.setIcon(new BackgroundIcon(t.getImage("resources/images/cards/cardBack_red.png")));
		deck = new Deck();
		
		
	}
	
	private int getUserStart() {
		if (user.getColor() == Color.BLUE) return blueStart;
		else if (user.getColor() == Color.RED) return redStart;
		else if (user.getColor() == Color.GREEN) return greenStart;
		else if (user.getColor() == Color.YELLOW) return yellowStart;
		else return 0;
	}
	
	private int getUserHome() {
		if (user.getColor() == Color.BLUE) return blueHome;
		else if (user.getColor() == Color.RED) return redHome;
		else if (user.getColor() == Color.GREEN) return greenHome;
		else if (user.getColor() == Color.YELLOW) return yellowHome;
		else return 0;
	}
	
	private int getPlayerStart(Color c) {
		if (c == Color.BLUE) return blueStart;
		else if (c == Color.RED) return redStart;
		else if (c == Color.GREEN) return greenStart;
		else if (c == Color.YELLOW) return yellowStart;
		else return 0;
	}
	
	private int getPlayerHome(Color c) {
		if (c == Color.BLUE) return blueHome;
		else if (c == Color.RED) return redHome;
		else if (c == Color.GREEN) return greenHome;
		else if (c == Color.YELLOW) return yellowHome;
		else return 0;
	}
	
	private void updateStart(int plusOrMinus, Player p) {
		Color c = p.getColor();
		JLabel l;
		if (c == Color.RED) {
			l = board[13][11];
			if (plusOrMinus == 0) redStart--;
			else redStart++;
			l.setText("" + redStart);
		}
		else if (c == Color.BLUE) {
			l = board[11][2];
			if (plusOrMinus == 0) blueStart--;
			else blueStart++;
			l.setText("" + blueStart);
		}
		else if (c == Color.GREEN) {
			l = board[4][13];
			if (plusOrMinus == 0) greenStart--;
			else greenStart++;
			l.setText("" + greenStart);
		}
		else if (c == Color.YELLOW) {
			l = board[2][4];
			if (plusOrMinus == 0) yellowStart--;
			else yellowStart++;
			l.setText("" + yellowStart);
		}
	}
	
	private void updateHome(Player p) {
		Color c = p.getColor();
		JLabel l;
		if (c == Color.RED) {
			l = board[8][13];
			redHome++;
			l.setText("" + redHome);
		}
		else if (c == Color.BLUE) {
			l = board[13][7];
			blueHome++;
			l.setText("" + blueHome);
		}
		else if (c == Color.YELLOW) {
			l = board[7][2];
			yellowHome++;
			l.setText("" + yellowHome);
		}
		else if (c == Color.GREEN) {
			l = board[2][8];
			greenHome++;
			l.setText("" + greenHome);
		}
	}
	
	private void moveBots() {
		for (int i = 1; i < players.length; i++) {
			currentPlayer = players[i];
			botMove(players[i]);
		}
		currentPlayer = user;
	}
	
	private boolean checkForWin() {
		//if (blueHome == 1) return true;
		if (yellowHome == 4 || blueHome ==4 || greenHome == 4 || redHome ==4) return true; 
		else return false;
	}
	
	private int computeScore() {
		int score = 0;
		score += getUserHome() * 5;
		for (Player p : players) {
			if (p.isRobot()) {
				score += (4 - getPlayerHome(p.getColor())) * 3;
				score += getPlayerStart(p.getColor());
			}
		}
		return score;
	}
	
	private void botMove(Player bot) {
		if (checkForWin()) {
			if (yellowHome == 4) JOptionPane.showMessageDialog(cardButton, "Yellow player wins!");
			else if (blueHome == 4) JOptionPane.showMessageDialog(cardButton, "Blue player wins!");
			else if (greenHome == 4) JOptionPane.showMessageDialog(cardButton, "Green player wins!");
			else if (redHome == 4) JOptionPane.showMessageDialog(cardButton, "Red player wins!");
			cardButton.setEnabled(false);
			int score = computeScore();
			//System.out.println("Your score: " + score);
			ScoreGUI sg = new ScoreGUI(score);
			sg.setVisible(true);
			sg.setModal(true);
			MainMenu m = new MainMenu();
			m.setVisible(true);
			setVisible(false);
		}
		else {
			Card c = deck.get();
			System.out.println(bot + " pulled: " + c.getInfo());
			if (c.getNumber() == 0) {
				Player player = null;
				int x = -2;
				int y = -2;
				for (Player p : players) {
					if (p != bot && p.isOutOfStart() && !bot.allOutOfStart()) {
						for (int i = 0; i < 16; i++) {
							for (int j = 0; j < 16; j++) {
								if (p.hasPawnAt(i, j) && !p.isInHomeArea(new int[]{i,j})) {
									player = p;
									x = i;
									y = j;
								}
							}
						}
					}
				}
				if (player != null) {
					if (player == user) JOptionPane.showMessageDialog(cardButton, bot + " pulled a Sorry and sent your pawn back to start!");
					moveSorry(new int[]{x,y}, player, bot);
				}
			}
			else if (c.getNumber() == 1 || c.getNumber() == 2 && !bot.hasPawnAt(bot.getStartCoords()[0], bot.getStartCoords()[1])) {
				int startSpace = 0;
				if (bot.getColor() == Color.BLUE) startSpace = blueStart;
				else if (bot.getColor() == Color.RED) startSpace = redStart;
				else if (bot.getColor() == Color.GREEN) startSpace = greenStart;
				else if (bot.getColor() == Color.YELLOW) startSpace = yellowStart;
				if (startSpace > 0) {
					movePawn(bot.getPawn(), bot.getStartCoords());
					updateStart(0, bot);
				}
				//else System.out.println("Bot can't move");
			}
			else if (bot.isOutOfStart() && c.getNumber() != 1 && c.getNumber() != 2) {
				int[] finalCoords = null;
				int[][] coords = new int[4][2];
				int index = 0;
				for (int i = 1; i < 5; i++) {
					int[] pawnloc = currentPlayer.getPawnLocation(i);
					if (pawnloc[0] != -1 && pawnloc[0] != 100) {
						if (c.getNumber() != 4) coords[index] = getMoveCoordinates(pawnloc, c.getNumber());
						else coords[index] = getMoveCoordinates(pawnloc, -4);
						index++;
					}
					//if (coords != null) break;
				}
				for (int[] c1 : coords) {
					for (Player p : players) {
						if (p != bot && p.hasPawnAt(c1[0], c1[1])) finalCoords = c1;
					}
				}
				//int[] finalcoords = getMostProgressedPawn(coords);
				if (finalCoords != null) movePawn(currentPlayer.getPawn(), finalCoords);
				else if (coords[0] != null) movePawn(currentPlayer.getPawn(), coords[0]);
				//else System.out.println("Bot was unable to make a move");
	
			}
			
			//else {
				//System.out.println("Bot can't move");
			//}
		}
		
		
	}
	
	/*
	private int[] getMostProgressedPawn(int[][] coords) {
		if (coords.length == 1) return coords[0];
		else {
			int[] mostProgressedCoords = currentPlayer.getStartCoords();
			if (currentPlayer.getColor() == Color.GREEN) {
				for (int i = 0; i < coords.length; i++) {
					if (mostProgressedCoords[1] == 15 && coords[i][1] == 15 && coords[i][0] < mostProgressedCoords[0]) {
						mostProgressedCoords = coords[i];
					}
					else 
				}
			}
			else if (currentPlayer.getColor() == Color.RED) {
				
			}
			else if (currentPlayer.getColor() == Color.BLUE) {
				
			}
			else if (currentPlayer.getColor() == Color.YELLOW){
				
			}
		}
	}
	*/
	
	private int[] findSafetySpaceCoords(int[] curr, int n) {
		n = -n;
		searchingForSafeSpaceCoords = true;
		int[] newcoords = new int[]{curr[0], curr[1]};
		int[] beginHC = currentPlayer.getBeginHomeCoords();
		int awayFromBeginHC;
		if (currentPlayer.getColor() == Color.BLUE) {
			awayFromBeginHC = newcoords[1];
			if (n > awayFromBeginHC) {
				newcoords = getMoveCoordinates(beginHC, -(n - awayFromBeginHC));
			}
			else {
				newcoords[1] = newcoords[1] - n;
			}
		}
		else if (currentPlayer.getColor() == Color.RED) {
			awayFromBeginHC = 15 - newcoords[0];
			if (n > awayFromBeginHC) {
				newcoords = getMoveCoordinates(beginHC, -(n - awayFromBeginHC));
			}
			else {
				newcoords[0] += n;
			}
		}
		else if (currentPlayer.getColor() == Color.GREEN) {
			awayFromBeginHC = 15 - newcoords[1];
			if (n > awayFromBeginHC) {
				newcoords = getMoveCoordinates(beginHC, -(n - awayFromBeginHC));
			}
			else {
				newcoords[1] += n;
			}
		}
		else if (currentPlayer.getColor() == Color.YELLOW){
			awayFromBeginHC = newcoords[1];
			if (n > awayFromBeginHC) {
				newcoords = getMoveCoordinates(beginHC, -(n - awayFromBeginHC));
			}
			else {
				newcoords[0] -= n;
			}
		}
		searchingForSafeSpaceCoords = false;
		return newcoords;
	}

	private int[] getMoveCoordinates(int[] currentCoordinates, int num) {
		if (currentCoordinates[0] == 100 || currentCoordinates[0] == -1) {
			//System.out.println("FAILURE: called getmovecoordinates when it shouldn't have. currentcoords: " + currentCoordinates[0] + "Num: " + num);
		}
		//System.out.println("in getMoveCoordinates for " + currentPlayer + " going " + num);
		//System.out.println("Current coordinates are: " + currentCoordinates[0] + ", " + currentCoordinates[1]);
		if (num < 0 && !searchingForSafeSpaceCoords && currentPlayer.isInHomeArea(currentCoordinates)) {
			return findSafetySpaceCoords(currentCoordinates, num);
		}
		
		int direction = -1;
		//north
		if (currentCoordinates[0] == 0) {
			direction = 0;
		}
		//east
		else if (currentCoordinates[1] == 15) {
			direction = 1;
		}
		//south
		else if (currentCoordinates[0] == 15) {
			direction = 2;
		}
		//west
		else if (currentCoordinates[1] == 0) {
			direction = 3;
		}
		else {
			//we're in home
		}
		int[] newcoords = advance(currentCoordinates, num, direction, currentCoordinates);
		if (newcoords == null) return null;
		newcoords = checkForSlide(newcoords);
		return newcoords;
		//System.out.println("Calling movePawn with curr: " + currentCoordinates[0] + ", " + currentCoordinates[1] + " and new: " + newcoords[0] + ", " + newcoords[1]);
		//movePawn(currentCoordinates, newcoords);
		
	}
	
	private void sendHome(int x, int y, Player p) {
		int[] previous = {x, y};
		int[] newcoords = {-1, -1};
		p.movePawn(previous, newcoords);
		updateStart(1, p);
		board[x][y].setIcon(startingBoard[x][y].getIcon());
		/*
		board[x][y].setText(startingBoard[x][y].getText());
		board[x][y].setForeground(Color.BLACK);
		board[x][y].setFont(new Font("Arial", Font.PLAIN, 12));
		*/
	}
	
	private int[] checkForSlide(int[] c) {
		if (c[0] == 0 && c[1] == 1) {
			if (currentPlayer.getColor() != Color.YELLOW) {
				c[1] = 5;
				for (int i = 1; i < 5; i++) {
					for (Player p : players) {
						if (p != currentPlayer && p.hasPawnAt(0, i)) {
							sendHome(0, i, p);
							JOptionPane.showMessageDialog(cardButton, p + " was sent back to start!");
						}
					}
				}
			}
			
		}
		else if (c[0] == 0 && c[1] == 9) {
			if (currentPlayer.getColor() != Color.YELLOW) {
				c[1] = 14;
				for (int i = 9; i < 14; i++) {
					for (Player p : players) {
						if (p != currentPlayer && p.hasPawnAt(0, i)) {
							sendHome(0, i, p);
							JOptionPane.showMessageDialog(cardButton, p + " was sent back to start!");
						}
					}
				}
			}
			
		}
		else if (c[0] == 1 && c[1] == 15) {
			if (currentPlayer.getColor() != Color.GREEN) {
				c[0] = 5;
				for (int i = 1; i < 5; i++) {
					for (Player p : players) {
						if (p != currentPlayer && p.hasPawnAt(i, 15)) {
							sendHome(i, 15, p);
							JOptionPane.showMessageDialog(cardButton, p + " was sent back to start!");
						}
					}
				}
			}
			
		}
		else if (c[0] == 9 &&c[1] == 15) {
			if (currentPlayer.getColor() != Color.GREEN) {
				c[0] = 14;
				for (int i = 9; i < 14; i++) {
					for (Player p : players) {
						if (p != currentPlayer && p.hasPawnAt(i, 15)) {
							sendHome(i, 15, p);
							JOptionPane.showMessageDialog(cardButton, p + " was sent back to start!");
						}
					}
				}
			}
			
		}
		else if (c[0] == 15 && c[1] == 14) {
			if (currentPlayer.getColor() != Color.RED) {
				c[1] = 10;
				for (int i = 14; i > 10; i--) {
					for (Player p : players) {
						if (p != currentPlayer && p.hasPawnAt(15, i)) {
							sendHome(15, i, p);
							JOptionPane.showMessageDialog(cardButton, p + " was sent back to start!");
						}
					}
				}
			}
			
		}
		else if (c[0] == 15 && c[1] == 6) {
			if (currentPlayer.getColor() != Color.RED) {
				c[1] = 1;
				for (int i = 6; i > 1; i--) {
					for (Player p : players) {
						if (p != currentPlayer && p.hasPawnAt(15, i)) {
							sendHome(15, i, p);
							JOptionPane.showMessageDialog(cardButton, p + " was sent back to start!");
						}
					}
				}
			}
		}
		else if (c[0] == 14 && c[1] == 0) {
			if (currentPlayer.getColor() != Color.BLUE) {
				c[0] = 10;
				for (int i = 14; i > 10; i--) {
					for (Player p : players) {
						if (p != currentPlayer && p.hasPawnAt(i, 0)) {
							sendHome(i, 0, p);
							JOptionPane.showMessageDialog(cardButton, p + " was sent back to start!");
						}
					}
				}
			}
		}
		else if (c[0] == 6 && c[1] == 0) {
			if (currentPlayer.getColor() != Color.BLUE) {
				c[0] = 1;
				for (int i = 6; i > 1; i--) {
					for (Player p : players) {
						if (p != currentPlayer && p.hasPawnAt(i, 0)) {
							sendHome(i, 0, p);
							JOptionPane.showMessageDialog(cardButton, p + " was sent back to start!");
						}
					}
				}
			}
		}
		return c;
	}
	
	private int[] advance(int[] startingCoords, int n, int dir, int[] coords) {

		if (currentPlayer.hasPawnAt(coords[0], coords[1]) && !(coords[0] == startingCoords[0] && coords[1] == startingCoords[1])) {
			//System.out.println("WE SHOULD BE DONE HERE");
			return null;
		}
		
		if (coords[0] == currentPlayer.getHomeCoords()[0] && coords[1] == currentPlayer.getHomeCoords()[1]) return new int[]{100,100};
		
		if (currentPlayer.isInHomeArea(coords) && n >0) {
			int[] ncoords = {coords[0], coords[1]};
			if (currentPlayer.getColor() == Color.BLUE) ncoords[1] += 1;
			else if (currentPlayer.getColor() == Color.YELLOW) ncoords[0] += 1;
			else if (currentPlayer.getColor() == Color.GREEN) ncoords[1] -= 1;
			else if (currentPlayer.getColor() == Color.RED) ncoords[0] -= 1;
			return advance(startingCoords, n -1, dir, ncoords);
		}
		
		
		int x = 1;
		if (n < 0) x = -1;
		if (n == 0) {
			return coords;
		}
		else if (x == 1 && coords[0] == 0 && coords[1] == 0 && dir != 0) return advance(startingCoords, n, 0, coords);
		else if (x == 1 && coords[0] == 0 && coords[1] == 15 && dir != 1) return advance(startingCoords, n, 1, coords);
		else if (x == 1 && coords[0] == 15 && coords[1] == 15 && dir != 2) return advance(startingCoords, n, 2, coords);
		else if (x == 1 && coords[0] == 15 && coords[1] == 0 && dir != 3) return advance(startingCoords, n, 3, coords);
		
		else if (x == -1 && coords[0] == 0 && coords[1] == 0 && dir != 3) return advance(startingCoords, n, 3, coords);
		else if (x == -1 && coords[0] == 0 && coords[1] == 15 && dir != 0) return advance(startingCoords, n, 0, coords);
		else if (x == -1 && coords[0] == 15 && coords[1] == 0 && dir != 2) return advance(startingCoords, n, 2, coords);
		else if (x == -1 && coords[0] == 15 && coords[1] == 15 && dir != 1) return advance(startingCoords, n, 1, coords);

		else {
			int[] newcoords = new int[2];
			if (dir == 0) {
				newcoords[0] = coords[0];
				newcoords[1] = coords[1] + x;
				return advance(startingCoords, n - x, dir, newcoords);
			}
			else if (dir == 1) {
				newcoords[0] = coords[0] + x;
				newcoords[1] = coords[1];
				return advance(startingCoords, n - x, dir, newcoords);
			}
			else if (dir == 2) {
				newcoords[0] = coords[0];
				newcoords[1] = coords[1] -x;
				return advance(startingCoords, n - x, dir, newcoords);
			}
			else if (dir == 3) {
				newcoords[0] = coords[0] -x;
				newcoords[1] = coords[1];
				return advance(startingCoords, n - x, dir, newcoords);
			}
		}
		//System.out.println("FAILURE in advance");
		//System.out.println("Failure coords are " + coords[0] + ", " + coords[1]);
		//System.out.println("Direction is: " + dir);
		//System.out.println("Starting coords were: " + startingCoords[0] + ", " + startingCoords[1]);
		return coords;
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
		setSize(640,480);
		container.setLayout(new GridLayout(16,16));
		add(container);
		setUpBoard();
	}
	
	private Image getPawnImage(Color c) {
		if (c == Color.RED) return redPawn;
		else if (c == Color.BLUE) return bluePawn;
		else if (c == Color.GREEN) return greenPawn;
		else if (c == Color.YELLOW) return yellowPawn;
		return null;
	}
	
	private void moveSorry(int[] coords, Player p1, Player p2) {
		//p2 is the player coming out
		//p1 is the player being kicked off
		int[] start = {-1, -1};
		p1.movePawn(coords, start);
		p2.movePawn(start, coords);
		updateStart(1, p1);
		updateStart(0, p2);
		/*
		board[coords[0]][coords[1]].setText("\u25A0");
		board[coords[0]][coords[1]].setForeground(p2.getColor());
		board[coords[0]][coords[1]].setFont(new Font("Arial", Font.PLAIN, 25));	
		*/
		JLabel position = board[coords[0]][coords[1]];
		Image bg = ((BackgroundIcon) position.getIcon()).getBackground();
		position.setIcon(new BackgroundIcon(bg, getPawnImage(p2.getColor())));
	}
	
	
	private void movePawn(int[] previous, int [] coords) {
	
		
		if (coords[0] == 100 || coords[0] == -1) {
			/*
			board[previous[0]][previous[1]].setText(startingBoard[previous[0]][previous[1]].getText());
			board[previous[0]][previous[1]].setForeground(Color.BLACK);
			board[previous[0]][previous[1]].setFont(new Font("Arial", Font.PLAIN, 12));
			*/
			board[previous[0]][previous[1]].setIcon(startingBoard[previous[0]][previous[1]].getIcon());
			currentPlayer.movePawn(previous, coords);
			if (coords[0] == 100) {
				JOptionPane.showMessageDialog(cardButton, currentPlayer + " made it home!");
				updateHome(currentPlayer);
			}
		}
		else {
			if (previous[0] != -1 && previous[1] != -1) {
				/*
				board[previous[0]][previous[1]].setText(startingBoard[previous[0]][previous[1]].getText());
				board[previous[0]][previous[1]].setForeground(Color.BLACK);
				board[previous[0]][previous[1]].setFont(new Font("Arial", Font.PLAIN, 12));
				*/
				board[previous[0]][previous[1]].setIcon(startingBoard[previous[0]][previous[1]].getIcon());
			}
			int x = coords[0];
			int y = coords[1];
			boolean invalid = false;
			for (Player p : players) {
				if (p.hasPawnAt(x, y) && !(previous[0] == coords[0] && previous[1] == coords[1])) {
					if (p == currentPlayer) {
						if (currentPlayer == user) JOptionPane.showMessageDialog(cardButton, "Invalid move!");
						invalid = true;
					}
					else {
						if (p == user) JOptionPane.showMessageDialog(cardButton, "Your pawn was sent back to start by " + currentPlayer);
						int[] start = new int[2];
						start[0] = -1;
						start[1] = -1;
						p.movePawn(coords, start);
						updateStart(1, p);
					}
				}
			}
			//puts the pawn from the previous on those coords 
			if (!invalid) {
				currentPlayer.movePawn(previous, coords);
				/*
				board[coords[0]][coords[1]].setText("\u25A0");
				board[coords[0]][coords[1]].setForeground(currentPlayer.getColor());
				board[coords[0]][coords[1]].setFont(new Font("Arial", Font.PLAIN, 25));	
				*/
				JLabel position = board[coords[0]][coords[1]];
				Image bg = ((BackgroundIcon) position.getIcon()).getBackground();
				position.setIcon(new BackgroundIcon(bg, getPawnImage(currentPlayer.getColor())));
			}
			else {
				//System.out.println("user chose an invalid move. allowing them to choose again");
				if (user.getSinglePawn() != -1 && !user.hasPawnAt(user.getStartCoords()[0], user.getStartCoords()[1])) JOptionPane.showMessageDialog(cardButton, "No valid moves!");
				else {
					JOptionPane.showMessageDialog(cardButton, "Select again!");
					choosingPawn = true;
				}
				if (previous[0] != -1) {
					/*
					board[previous[0]][previous[1]].setText("\u25A0");
					board[previous[0]][previous[1]].setForeground(currentPlayer.getColor());
					board[previous[0]][previous[1]].setFont(new Font("Arial", Font.PLAIN, 25));	
					*/
					JLabel position = board[previous[0]][previous[1]];
					Image bg = ((BackgroundIcon) position.getIcon()).getBackground();
					position.setIcon(new BackgroundIcon(bg, getPawnImage(currentPlayer.getColor())));
				}
				
			}
		}
		
		

		
	}
	
	private boolean hasAMove() {
		for (int i = 1; i < 5; i++) {
			int[] prev = currentPlayer.getPawnLocation(i);
			if (prev[0] == -1 && (currentCard.getNumber() == 1 || currentCard.getNumber() == 2) && !currentPlayer.hasPawnAt(currentPlayer.getStartCoords()[0], currentPlayer.getStartCoords()[1])) return true;
			if (prev[0] != -1 && prev[0] != 100) {
				//special cases for 0-always true bc handled later, 4-backwards, 7-check all possibilities, 10-forward 10 and back 1
				if (currentCard.getNumber() == 0) return true;
				else if (currentCard.getNumber() == 4 && getMoveCoordinates(prev, -4) != null) return true;
				else if (currentCard.getNumber() == 7) {
					for (int j = 0; j < 8; j++) {
						for (int k = 1; k < 5; k++) {
							if (k != i) {
								if (getMoveCoordinates(prev, j) != null && getMoveCoordinates(currentPlayer.getPawnLocation(k), 7-j) != null) return true;
							}
						}
					}
				}
				else if (currentCard.getNumber() == 10) {
					if (getMoveCoordinates(prev, 10) != null || getMoveCoordinates(prev, -1) != null) return true;
				}
				else if (getMoveCoordinates(prev, currentCard.getNumber()) != null) return true;
				
			}
			else if (prev[0] == -1 && currentCard.getNumber() == 0) return true;
		}
		return false;
	}
	
	private void addEvents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("User went");
				goAgain = false;
				currentCard = deck.get();
				//System.out.println("User pulled: " + currentCard.getInfo());
				if (currentCard.getNumber() == 2) goAgain = true;
				//JOptionPane.showMessageDialog(cardButton, currentCard.getInfo());
				CardGUI cg = new CardGUI(currentCard);
				cg.setModal(true);
				cg.setVisible(true);
				
				//System.out.println("Has a move: " + hasAMove());
				if (user.canMove(currentCard) && hasAMove()) {
					if (!user.isOutOfStart() && currentCard.getNumber() != 0) {
						JOptionPane.showMessageDialog(cardButton, "Your pawn comes out of home!");
						int[] coords = new int[2];
						coords[0] = -1;
						coords[1] = -1;
						movePawn(coords, currentPlayer.getStartCoords());
						updateStart(0, user);
						//if (!goAgain) moveBots();
						//else JOptionPane.showMessageDialog(cardButton, "Draw again!");
					}
					
					else if (currentCard.getNumber() == 0) {
						//Sorry
						boolean canMove = false;
						for (int i = 1; i < players.length; i++) {
							if (players[i].isOutOfStart()) {
								for (int j = 1; j < 5; j++) {
									int[] coords = players[i].getPawnLocation(j);
									if (!players[i].isInHomeArea(coords) && coords[0] != -1 && coords[0] != 100) canMove = true;
								}
							}
						}
						if (canMove && getUserStart() > 0) {
							sorryChoosingPawn = true;
							cardButton.setEnabled(false);
							JOptionPane.showMessageDialog(cardButton, "Choose another player's pawn to send back to start!");
						}
						else {
							JOptionPane.showMessageDialog(cardButton, "Unable to move!");
							//moveBots();
						}	
					}
					else if (currentCard.getNumber() == 7 && user.getSinglePawn() == -1) {
						/*
						 * prompt first pawn and amount to move
						 * move first pawn
						 * prompt second pawn and move automatically
						 */
						//if (hasAMove()) {
							JOptionPane.showMessageDialog(cardButton, "Select first pawn to move!");
							choosingPawn = true;
							cardButton.setEnabled(false);
						//}
						
					}
					else if (!user.allOutOfStart() && (currentCard.getNumber() == 2 || currentCard.getNumber() == 1)) {
						//System.out.println("User start: " + getUserStart());
						//User start
						if (getUserStart() > 0) {
							JOptionPane.showMessageDialog(cardButton, "Select pawn to move or select Start to move a pawn out of start");
							maySelectStart = true;
						}
						else JOptionPane.showMessageDialog(cardButton, "No pawns left in Start! Select pawn to move");
						choosingPawn = true;
						cardButton.setEnabled(false);
					}
					else if (user.getSinglePawn() != -1) {
						int[] coordinates = null;
						if (currentCard.getNumber() == 4) coordinates = getMoveCoordinates(user.getPawnLocation(user.getSinglePawn()), -4);
						else if (currentCard.getNumber() == 10){
							Object[] options = {"Forward 10", "Backwards 1"};
							int n = JOptionPane.showOptionDialog(cardButton, "Would you like to move forwards 10 or backwards 1?", "Card 10", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
							if (n == 0) coordinates = getMoveCoordinates(user.getPawnLocation(user.getSinglePawn()), 10);
							else coordinates = getMoveCoordinates(user.getPawnLocation(user.getSinglePawn()), -1);
						}
						else if (currentCard.getNumber() == 11) {
							boolean otherPlayers = false;
							for (int i = 1; i < players.length; i++) if (players[i].isOutOfStart()) otherPlayers = true;
							if (otherPlayers) {
								Object[] options = {"Switch with another player", "Move forward 11"};
								int n = -1;
								if (!user.isInHomeArea(user.getPawnLocation(user.getSinglePawn()))) n = JOptionPane.showOptionDialog(cardButton, "Would you like to switch places with another player or move forward 11?", "Card 11", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
								if (n == 1 || n == -1) {
									coordinates = getMoveCoordinates(user.getPawnLocation(user.getSinglePawn()), 11);
								}
								else {
									JOptionPane.showMessageDialog(cardButton, "Choose the pawn you'd like to switch with");
									choosingPawn = true;
									switchingPawns = true;
									switchingPawnsCoords = user.getPawnLocation(user.getSinglePawn());
								}
							}
							else coordinates = getMoveCoordinates(user.getPawnLocation(user.getSinglePawn()), 11);
							
						}
						else coordinates = getMoveCoordinates(user.getPawnLocation(user.getSinglePawn()), currentCard.getNumber());
						
						if (coordinates != null) movePawn(user.getPawnLocation(user.getSinglePawn()), coordinates);
						else if (choosingPawn == false) JOptionPane.showMessageDialog(cardButton, "Unable to move!");
						//moveBots();
					}
					else {
						choosingPawn = true;
						cardButton.setEnabled(false);
						JOptionPane.showMessageDialog(cardButton, "Select pawn to move");
						//System.out.println("WAITING FOR USER TO CHOOSE PAWN");
					}
					if (!choosingPawn && !sorryChoosingPawn) {
						if (!goAgain) moveBots();
						else JOptionPane.showMessageDialog(cardButton, "Draw again!");
					}
					
					
				}
				else {
					JOptionPane.showMessageDialog(cardButton, "Unable to move!");
					if (!goAgain && !sorryChoosingPawn) moveBots();
					else if (goAgain) JOptionPane.showMessageDialog(cardButton, "Draw again!");
				}
				
			}
			
		});
		class mouseAdapter implements MouseListener {
			public void mouseClicked(MouseEvent e) {
				int[] clickedCoords = new int[2];
				if (choosingPawn || sorryChoosingPawn || switchingPawns) {
					cardButton.setEnabled(true);
					JLabel selectedPawn = (JLabel) e.getSource();
					int x = -1;
					int y = -1;
					for (int i = 0; i < 16; i++) {
						for (int j = 0; j < 16; j++) {
							if (selectedPawn == board[i][j]) {
								x = i;
								y = j;
								clickedCoords[0] = x;
								clickedCoords[1] = y;
							}
						}
					}
					if (switchingPawns) {
						switchingPawns = false;
						choosingPawn = false;
						boolean found = false;
						for (int i = 1; i < players.length; i++) {
							if (players[i].hasPawnAt(x, y)) {
								switchPawns(switchingPawnsCoords, clickedCoords, user, players[i]);
								found = true;
							}
						}
						if (!found) {
							JOptionPane.showMessageDialog(cardButton, "Invalid pawn selection!");
							switchingPawns = true;
						}
					}
					else if (choosingPawn) {
						if (user.getStartLabelCoords()[0] == x && user.getStartLabelCoords()[1] == y  && maySelectStart) {
							//move out of start
							maySelectStart = false;
							choosingPawn = false;
							JOptionPane.showMessageDialog(cardButton, "Your pawn comes out of home!");
							int[] coords = new int[2];
							coords[0] = -1;
							coords[1] = -1;
							if (!user.hasPawnAt(user.getStartCoords()[0], user.getStartCoords()[1])) {
								movePawn(coords, currentPlayer.getStartCoords());
								updateStart(0, user);
							}
							else {
								JOptionPane.showMessageDialog(cardButton, "Cannot come out of start! Choose pawn to move");
								choosingPawn = true;
								cardButton.setEnabled(false);
							}
							if (!goAgain) moveBots();
							else if (!choosingPawn) JOptionPane.showMessageDialog(cardButton, "Draw again!");
						}
						else if (user.hasPawnAt(x, y)) {
							//System.out.println("USER HAS CHOSEN PAWN");
							choosingPawn = false;
							if (currentCard.getNumber() == 10) {
								Object[] options = {"Forward 10", "Backwards 1"};
								int n = JOptionPane.showOptionDialog(cardButton, "Would you like to move forwards 10 or backwards 1?", "Card 10", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
								int [] coordinates ;
								if (n == 0) coordinates = getMoveCoordinates(clickedCoords, 10);
								else coordinates = getMoveCoordinates(clickedCoords, -1);
								if (coordinates != null) movePawn(clickedCoords, coordinates);
								else {
									JOptionPane.showMessageDialog(cardButton, "Invalid! Choose again");
									choosingPawn = true;
								}
							}
							else if (currentCard.getNumber() == 11) {
								boolean otherPlayers = false;
								for (int i = 1; i < players.length; i++) if (players[i].isOutOfStart()) otherPlayers = true;
								if (otherPlayers) {
									Object[] options = {"Switch with another player", "Move forward 11"};
									int n = JOptionPane.showOptionDialog(cardButton, "Would you like to switch places with another player or move forward 11?", "Card 11", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
									if (n == 1) {
										int[] coords = getMoveCoordinates(clickedCoords, 11);
										if (coords != null) movePawn(clickedCoords, coords);
										else {
											JOptionPane.showMessageDialog(cardButton, "Invalid! Choose again");
											choosingPawn = true;
										}
									}
									else {
										JOptionPane.showMessageDialog(cardButton, "Choose the pawn you'd like to switch with!");
										choosingPawn = true;
										switchingPawns = true;
										switchingPawnsCoords = clickedCoords;
									}
								}
								else {
									int[] coords = getMoveCoordinates(clickedCoords, 11);
									if (coords != null) movePawn(clickedCoords, coords);
									else {
										JOptionPane.showMessageDialog(cardButton, "Invalid! select again");
										choosingPawn = true;
										cardButton.setEnabled(false);
									}
								}
								
							}
							else if (currentCard.getNumber() == 7) {
								//SEVEN
								if (sevenFirstMoveAmount == -1) {
									//prompt first pawn
									Object[] sevens = new Object[8];
									for (int i = 0; i < 8; i++) sevens[i] = i;
									sevenFirstMoveAmount = (int) JOptionPane.showInputDialog(cardButton, "How much would you like to move this pawn?", "Moving pawn", JOptionPane.QUESTION_MESSAGE, null, sevens, sevens[0]);
									int[] coords = getMoveCoordinates(clickedCoords, sevenFirstMoveAmount);
									//System.out.println("First pawn can move if not null: " + coords);
									boolean validMove = false;
									for (int i = 1; i < 5; i++) {
										int[] pawncoords = currentPlayer.getPawnLocation(i);
										//System.out.println("Pawncoords: " + pawncoords[0] + ", " + pawncoords[1]);
										if (pawncoords[0] != -1 && pawncoords[0] != 100 && !(clickedCoords[0] == pawncoords[0] && clickedCoords[1] == pawncoords[1])) {
											if (pawncoords[0] != -1 && pawncoords[0] != 100) {
												if (getMoveCoordinates(pawncoords, 7 - sevenFirstMoveAmount) != null) {
													//System.out.println("And another pawn can also move");
													validMove = true;
												}
											}
										}
									}
									if (sevenFirstMoveAmount != 7) {
										if (coords != null && validMove) {
											movePawn(clickedCoords, coords);
											JOptionPane.showMessageDialog(cardButton, "Choose second pawn to move!");
											choosingPawn = true;
										}
										else {
											JOptionPane.showMessageDialog(cardButton, "Invalid move selection!");
											sevenFirstMoveAmount = -1;
											choosingPawn = true;
										}
									}
									else {
										movePawn(clickedCoords, coords);
										choosingPawn = false;
									}
									
								}
								else {
									int[] coords = getMoveCoordinates(clickedCoords, 7 - sevenFirstMoveAmount);
									movePawn(clickedCoords, coords);
									//startAdvance(clickedCoords, 7 - sevenFirstMoveAmount);
									sevenFirstMoveAmount = -1;
								}
								//moveBots();
							}
							else if (currentCard.getNumber() == 4) {
								int[] coords = getMoveCoordinates(clickedCoords, -4);
								if (coords != null) movePawn(clickedCoords, coords);
								else {
									JOptionPane.showMessageDialog(cardButton, "Invalid pawn selection! Choose again");
									choosingPawn = true;
								}
 								//startAdvance(clickedCoords, -4);
								//moveBots();
							}
							else {
								int[] coords = getMoveCoordinates(clickedCoords, currentCard.getNumber());
								if (coords != null) {
									//System.out.println("Chosen coordinates are valid. if they aren't getmovecoordinates is wrong");
									movePawn(clickedCoords, coords);
								}
								else {
									JOptionPane.showMessageDialog(cardButton, "Invalid pawn selection! Choose again!");
									choosingPawn = true;
									cardButton.setEnabled(false);
								}
								//startAdvance(clickedCoords, currentCard.getNumber());
							}
							if (!goAgain && !choosingPawn && !sorryChoosingPawn && !switchingPawns) moveBots();
							else if (goAgain && !choosingPawn) JOptionPane.showMessageDialog(cardButton, "Draw again!");
						}
						else {
							JOptionPane.showMessageDialog(cardButton, "Invalid pawn selection!");
						}
					}
					else if (sorryChoosingPawn) {
						sorryChoosingPawn = false;
						for (int i = 1; i < players.length; i++) {
							if (players[i].hasPawnAt(x, y)) {
								//sorryChoosingPawn = false;
								if (players[i].isInHomeArea(new int[]{x,y})) sorryChoosingPawn = true;
								else {
									int[] start = new int[2];
									start[0] = -1;
									start[1] = -1;
									moveSorry(clickedCoords, players[i], currentPlayer);
									moveBots();
									break;
								}
								
							}
						}	
					}
				}
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {
				JLabel hoveringLabel = (JLabel) e.getSource();
				int x = 0,y = 0; 
				for (int i = 0; i < 16; i++) {
					for (int j = 0; j < 16; j++) {
						if (board[i][j] == hoveringLabel) {
							x = i;
							y = j;
						}
					}
				}
				if (currentPlayer.hasPawnAt(x, y)) {
					hoveringLabel.setIcon(new BackgroundIcon(highlightPanel, getPawnImage(currentPlayer.getColor())));
				}
			}
			public void mouseExited(MouseEvent e) {
				JLabel exitedLabel = (JLabel) e.getSource();
				int x = 0; 
				int y = 0;
				for (int i = 0; i < 16; i++) {
					for (int j = 0; j < 16; j++) {
						if (board[i][j] == exitedLabel) {
							x = i;
							y = j;
						}
					}
				}
				exitedLabel.setIcon(startingBoard[x][y].getIcon());
				for (Player p : players) {
					if (p.hasPawnAt(x, y)) {
						exitedLabel.setIcon(new BackgroundIcon(((BackgroundIcon) startingBoard[x][y].getIcon()).getBackground(), getPawnImage(p.getColor())));
					}
				}
			}
			
		}
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				board[i][j].addMouseListener(new mouseAdapter());
			}
		}
	}
	
	
	private void switchPawns(int[] coords1, int[] coords2, Player p1, Player p2) {
		board[coords1[0]][coords1[1]].setForeground(p2.getColor());
		board[coords2[0]][coords2[1]].setForeground(p1.getColor());
		p1.movePawn(coords1, coords2);
		p2.movePawn(coords2, coords1);
	}
	
	private void setUpBoard() {
		
		//Border blueborder = BorderFactory.createLineBorder(Color.BLUE, 2);
		//Border greenborder = BorderFactory.createLineBorder(Color.GREEN, 2);
		//Border yellowborder = BorderFactory.createLineBorder(Color.YELLOW, 2);
		//Border redborder = BorderFactory.createLineBorder(Color.RED, 2);
		//Border blackborder = BorderFactory.createLineBorder(Color.BLACK, 2);
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				board[i][j] = new JLabel("", SwingConstants.CENTER);
			}
		}
		//row 1
		//board[0][0].setBorder(blackborder);
		board[0][0].setIcon(new BackgroundIcon(greyTile));
		for (int j = 1; j <= 4; j++) {
			//board[0][j].setBorder(yellowborder);
			//board[0][j].setText(">");
			board[0][j].setIcon(new BackgroundIcon(yellowTile, yellowSlide));
		}
		for (int j = 5; j <= 8; j++) board[0][j].setIcon(new BackgroundIcon(greyTile));
		for (int j = 9; j <= 13; j++) {
			board[0][j].setIcon(new BackgroundIcon(yellowTile, yellowSlide));
		}
		for (int j = 14; j <= 15; j++) board[0][j].setIcon(new BackgroundIcon(greyTile));
		
		//col 1
		for (int i = 0; i <= 1; i++) board[i][0].setIcon(new BackgroundIcon(greyTile));
		for (int i = 2; i <= 6; i++) {
			board[i][0].setIcon(new BackgroundIcon(blueTile, blueSlide));
			//board[i][0].setText("^");
		}
		for (int i = 7; i <= 10; i++) board[i][0].setIcon(new BackgroundIcon(greyTile));
		for (int i = 11; i <= 14; i++) {
			board[i][0].setIcon(new BackgroundIcon(blueTile, blueSlide));
			//board[i][0].setText("^");
		}
		
		//bottom row
		for (int j = 0; j <= 1; j++) board[15][j].setIcon(new BackgroundIcon(greyTile));
		for (int j = 2; j <= 6; j++) {
			board[15][j].setIcon(new BackgroundIcon(redTile, redSlide));
			//board[15][j].setText("<");
		}
		for (int j = 7; j <= 10; j++) board[15][j].setIcon(new BackgroundIcon(greyTile));
		for (int j = 11; j <= 14; j++) {
			board[15][j].setIcon(new BackgroundIcon(redTile, redSlide));
			//board[15][j].setText("<");
			
		}
		
		//right col
		for (int i = 1; i <= 4; i++) {
			board[i][15].setIcon(new BackgroundIcon(greenTile, greenSlide));
			//board[i][15].setText("v");
		}
		for (int i = 5; i <= 8; i++) board[i][15].setIcon(new BackgroundIcon(greyTile));
		for (int i = 9; i <= 13; i++) {
			board[i][15].setIcon(new BackgroundIcon(greenTile, greenSlide));
			//board[i][15].setText("v");
		}
		for (int i = 14; i <= 15; i++) board[i][15].setIcon(new BackgroundIcon(greyTile));
		
		//starts
		board[1][4].setIcon(new BackgroundIcon(yellowPanel));
		board[1][4].setText("Start");
		board[1][4].setFont(font);
		board[2][4].setText("" + yellowStart);
		
		
		board[4][14].setIcon(new BackgroundIcon(greenPanel));
		board[4][14].setText("Start");
		board[4][14].setFont(font);
		board[4][13].setText("" + greenStart);
		
		board[14][11].setIcon(new BackgroundIcon(redPanel));
		board[14][11].setText("Start");
		board[14][11].setFont(font);
		board[13][11].setText("" + redStart);
		
		board[11][1].setIcon(new BackgroundIcon(bluePanel));
		board[11][1].setText("Start");
		board[11][1].setFont(font);
		board[11][2].setText("" + blueStart);
		
		//homes
		for (int i = 1; i <= 6; i++) board[i][2].setIcon(new BackgroundIcon(yellowTile));
		board[6][2].setText("Home");
		board[6][2].setFont(font);
		board[6][2].setIcon(new BackgroundIcon(yellowPanel));
		board[7][2].setText("" + yellowHome);
		
		for (int j = 9; j < 15; j++) board[2][j].setIcon(new BackgroundIcon(greenTile));
		board[2][9].setText("Home");
		board[2][9].setFont(font);
		board[2][9].setIcon(new BackgroundIcon(greenPanel));
		board[2][8].setText("" + greenHome);
		
		for (int i = 9; i < 15; i++) board[i][13].setIcon(new BackgroundIcon(redTile));
		board[9][13].setText("Home");
		board[9][13].setFont(font);
		board[9][13].setIcon(new BackgroundIcon(redPanel));
		board[8][13].setText("" + redHome);
		
		for (int j = 1; j <= 6; j++) board[13][j].setIcon(new BackgroundIcon(blueTile));
		board[13][6].setText("Home");
		board[13][6].setFont(font);
		board[13][6].setIcon(new BackgroundIcon(bluePanel));
		board[13][7].setText("" + blueHome);
		
		startingBoard = new JLabel[16][16];
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j< 16; j++) {
				startingBoard[i][j] = new JLabel(board[i][j].getIcon());
			}
		}
		
		
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (i == 7 && j == 8) {
					container.add(cardButton);
				}
				else if (i == 7 && j == 7) {
					JLabel l = new JLabel("Cards:");
					l.setFont(font);
					container.add(l);
				}
				else container.add(board[i][j]);
			}
		}
		add(container);
	}
}
