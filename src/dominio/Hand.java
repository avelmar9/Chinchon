package dominio;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	private List<Card> hand = new ArrayList<>();

	
	public List<Card> getHand() {
		return hand;
	}

	public void addCard(Card carta) {
		hand.add(carta);
	}

	public int numCards() {
		return hand.size();
	}
	public Card removeCard(int position) {
        return hand.remove(position);
    }
	public int totalPoints() {
		return hand.stream()
				.mapToInt(p->p.getNumber().getValue())
				.sum();
	}
	public void showCards() {
		for(int i=0; i< numCards();i++) {
			System.out.printf("%d. %s%n", i+1, hand.get(i));
		}
	}
}