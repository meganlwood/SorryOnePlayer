
public class Card {
	
	private int number;
	private String name;
	private String description;
	
	public Card(int n, String s) {
		number = n;
		description = s;
		if (number != 0) name = "" + number;
		else name= "Sorry!";
	}
	
	public String getInfo() {
		return description;
	}
	
	public int getNumber() {
		return number;
	}
}
