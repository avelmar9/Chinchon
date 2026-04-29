package dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	private final List<Card> cards;
	
	public Deck(int numDecks) {
		this.cards = new ArrayList<Card>();
		
		 for (int d = 0; d < numDecks; d++) {
		        initializeDeck();
		    }
	}

	private void initializeDeck() {
		for (Suit tipo : Suit.values()) {
			for (Value valor : Value.values()) {
				cards.add(new Card(tipo, valor));
			}
		}
		shuffle();
	}
	public Card takeCard() {
	    if (cards.isEmpty()) throw new IllegalStateException("El mazo está vacío.");
	    return cards.remove(cards.size() - 1);
	}
	public void shuffle() {
		Collections.shuffle(cards);
	}
	public int size() {
        return cards.size();
    }
	public boolean isEmpty() {
	    return cards.isEmpty();
	}

	public List<Card> getCards() {
		return cards;
	}

}