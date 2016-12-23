public class Pawn {
	private int x;
	private int y;
	
	public Pawn() {
		x = -1;
		y = -1;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isOutOfStart() {
		return (x != -1 && y != -1 && x != 100 && y != 100);
	}
	
	public void place(int[] coords) {
		x = coords[0];
		y = coords[1];
	}
	
	public int[] getCoords() {
		int [] coords = new int[2];
		coords[0] = x;
		coords[1] = y;
		return coords;
	}
}
