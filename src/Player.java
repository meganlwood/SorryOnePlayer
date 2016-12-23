import java.awt.Color;

//import javax.swing.JLabel;

public class Player {
	
	private Color color;
	private boolean robot;
	//private boolean outOfStart;
	//private boolean allOutOfStart;
	private Pawn p1;
	private Pawn p2;
	private Pawn p3;
	private Pawn p4;
	
	
	public Player(Color c, boolean robot) {
		color = c;
		this.robot = robot;
		//outOfStart = false;
		p1 = new Pawn();
		p2 = new Pawn();
		p3 = new Pawn();
		p4 = new Pawn();
	}
	
	public int getSinglePawn() {
		int numPawns = 0;
		int p = -1;
		Pawn[] pawns = new Pawn[4];
		pawns[0] = p1;
		pawns[1] = p2;
		pawns[2] = p3;
		pawns[3] = p4;
		for (int i = 0; i < 4; i++) {
			if (pawns[i].isOutOfStart()) {
				numPawns++;
				p = i;
			}
		}
		if (numPawns == 1) {
			return p+1;
		}
		else return -1;
	}
	
	public boolean isInHomeArea(int[] coords) {
		if (color == Color.BLUE) {
			if (coords[0] == 13 && coords[1] < 6) return true;
		}
		else if (color == Color.YELLOW) {
			if (coords[1] == 2 && coords[0] < 6) return true; 
		}
		else if (color == Color.GREEN) {
			if (coords[0] == 2 && coords[1] > 9) return true;
		}
		else if (color == Color.RED) {
			if (coords[1] == 13 && coords[0] > 9) return true;
		}
		return false;
	}
	
	
	public int[] getPawnLocation(int p) {
		if (p == 1) return p1.getCoords();
		else if (p == 2) return p2.getCoords();
		else if (p == 3) return p3.getCoords();
		else if (p == 4) return p4.getCoords();
		return null;
	}
	
	public String toString() {
		if (color == Color.RED) return "Red Player";
		else if (color == Color.BLUE) return "Blue Player";
		else if (color == Color.GREEN) return "Green Player";
		else if (color == Color.YELLOW) return "Yellow Player";
		return null;
	}
	
	public int[] getPawn() {
		
		if (p1.isOutOfStart()) return p1.getCoords();
		else if (p2.isOutOfStart()) return p2.getCoords();
		else if (p3.isOutOfStart()) return p3.getCoords();
		else if (p4.isOutOfStart()) return p4.getCoords();
		else return new int[]{-1,-1};
		
	}
	
	public void movePawn(int[] previous, int[] newcoords) {
		if (p1.getX() == previous[0] && p1.getY() == previous[1]) {
			p1.place(newcoords);
		}
		else if (p2.getX() == previous[0] && p2.getY() == previous[1]) {
			p2.place(newcoords);
		}
		else if (p3.getX() == previous[0] && p3.getY() == previous[1]) {
			p3.place(newcoords);
		}
		else if (p4.getX() == previous[0] && p4.getY() == previous[1]) {
			p4.place(newcoords);
		}
	}
	
	public boolean isOutOfStart() {
		return (p1.isOutOfStart() || p2.isOutOfStart() || p3.isOutOfStart() || p4.isOutOfStart());
	}
	
	
	public boolean hasPawnAt(int x, int y) {
		if (p1.getX() == x && p1.getY() == y) return true;
		else if (p2.getX() == x && p2.getY() == y) return true;
		else if (p3.getX() == x && p3.getY() == y) return true;
		else if (p4.getX() == x && p4.getY() == y) return true;
		else return false;
	}
	
	public boolean allOutOfStart() {
		return (p1.isOutOfStart() && p2.isOutOfStart() && p3.isOutOfStart() && p4.isOutOfStart());
	}
	
	public int[] getStartCoords() {
		int[] coords = new int[2];
		if (color == Color.RED) {
			coords[1] = 11;
			coords[0] = 15;
		}
		else if (color == Color.BLUE) {
			coords[1] = 0;
			coords[0] = 11;
		}
		else if (color == Color.YELLOW) {
			coords[1] = 4;
			coords[0] = 0;
		}
		else if (color == Color.GREEN) {
			coords[1] = 15;
			coords[0] = 4;
		}
		return coords;
	}
	
	public int[] getStartLabelCoords() {
		int[] coords = getStartCoords();
		if (color == Color.RED) coords[0]--;
		else if (color == Color.BLUE) coords[1]++;
		else if (color == Color.YELLOW) coords[0]++;
		else if (color == Color.GREEN) coords[1]--;
		return coords;
	}
	
	public int[] getBeginHomeCoords() {
		int[] coords = new int[2];
		if (color == Color.RED) {
			coords[0] = 15;
			coords[1] = 13;
		}
		else if (color == Color.BLUE) {
			coords[0] = 13;
			coords[1] = 0;
		}
		else if (color == Color.YELLOW) {
			coords[0] = 0;
			coords[1] = 2;
		}
		else if (color == Color.GREEN) {
			coords[0] = 2;
			coords[1] = 15;
		}
		return coords;
	}
	
	public int[] getHomeCoords() {
		int[] coords = new int[2];
		if (color == Color.RED) {
			coords[0] = 9;
			coords[1] = 13;
		}
		else if (color == Color.BLUE) {
			coords[0] = 13;
			coords[1] = 6;
		}
		else if (color == Color.YELLOW) {
			coords[0] = 6;
			coords[1] = 2;
		}
		else if (color == Color.GREEN) {
			coords[0] = 2;
			coords[1] = 9;
		}
		return coords;
	}
	
	
	public boolean canMove(Card c) {
		if (!isOutOfStart() && c.getNumber() > 2) return false;
		else return true;
	}
	
	public Color getColor() {
		return color;
	}
	
	public boolean isRobot() {
		return robot;
	}
}
