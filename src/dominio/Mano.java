package dominio;

import java.util.ArrayList;
import java.util.List;

public class Mano {
	private List<Carta> hand = new ArrayList<>();

	public void addCarta(Carta carta) {
		hand.add(carta);
	}

	public int numCards() {
		return hand.size();
	}
	public int totalPoints() {
		return hand.stream()
				.mapToInt(p->p.getPoints().getValue())
				.sum();
	}
	public void showCards() {
		for(int i=0; i< numCards();i++) {
		System.out.printf("%s%n",hand.get(i));
		}
	}
}