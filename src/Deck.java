import java.util.Random;

public class Deck {
	
	Card[] deck;
	int placeInDeck;
	
	public Deck() {
		deck = new Card[44];
		fillDeck();
		placeInDeck = 0;
		shuffle();
		
	}
	
	public void shuffle() {
		Random r = new Random();
		for (int i = 0; i < 44; i++) {
			int randomPos = r.nextInt(44);
			Card temp = deck[i];
			deck[i] = deck[randomPos];
			deck[randomPos] = temp;
		}
		placeInDeck = 0;
	}
	
	public Card get() {
		Card c = deck[placeInDeck];
		placeInDeck++;
		if (placeInDeck == 44) shuffle();
		return c;
	}
	
	private void fillDeck() {
		int place = 0;
		for (int i = 0; i < 4; i++) {
			deck[place] = new Card(1, "Move a pawn from Start or move a pawn one space forward.");
			place++;
			deck[place] = new Card(2, "Move a pawn from Start or move a pawn two spaces forward. Drawing a two entitles the player to draw again at the end of their turn.");
			place++;
			deck[place] = new Card(3, "Move a pawn three spaces forward.");
			place++;
			deck[place] = new Card(4, "Move a pawn four spaces backwards.");
			place++;
			deck[place] = new Card(5, "Move a pawn five spaces foward.");
			place++;
			deck[place] = new Card(7, "Move one pawn seven spaces forward or split the seven spaces between two pawns.");
			place++;
			deck[place] = new Card(8, "Move a pawn eight spaces foward.");
			place++;
			deck[place] = new Card(10, "Move a pawn ten spaces forward or one space backward");
			place++;
			deck[place] = new Card(11, "Move a pawn eleven spaces forward, or switch places with an opposing pawn.");
			place++;
			deck[place] = new Card(12, "Move a pawn twelve spaces forward.");
			place++;
			deck[place] = new Card(0, "Move any one pawn from Start to a square occupied by any opponent, sending that pawn back to its own Start");
			place++;
		}
	}
}
