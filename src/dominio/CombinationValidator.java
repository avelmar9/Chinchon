
package dominio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CombinationValidator {

	public boolean isValidCombination(List<List<Card>> selectedCards) {
		if (selectedCards == null || selectedCards.isEmpty()) {
			System.out.println("La combinación no puede estar vacía");
			return false;
		}

		for (List<Card> group : selectedCards) {
			if (group.size() < 3 || (!areEquals(group) && !isStraight(group))) {
				System.out.println("Combinaciones no válidas");
				return false;
			}
		}
		return true;
	}

	private boolean areEquals(List<Card> cards) {
		Value firstNumber = cards.get(0).getNumber();
		for (Card c : cards) {
			if (c.getNumber() != firstNumber)
				return false;
		}
		return true;
	}

	private boolean isStraight(List<Card> cards) {
	    Suit firstSuit = cards.get(0).getSuit();
	    boolean normalStep,jumpStep;
	    for (Card c : cards) {
	        if (c.getSuit() != firstSuit) {
	            return false;
	        }
	    }

	    cards.sort(Comparator.comparing(Card::getNumber));
	   

	    for (int i = 0; i < cards.size() - 1; i++) {
	        Value current = cards.get(i).getNumber();
	        Value next = cards.get(i + 1).getNumber();

	        normalStep = next.getValue() == current.getValue() + 1;
	          jumpStep = current.getValue() == 7 && next.getValue() == 10;

	        if (!normalStep && !jumpStep) {
	            return false;
	        }
	    }
	    return true;
	}

	public boolean isChinchon(List<Card> cards, int roundNumber) {
		
		List<List<Card>> group;
		if(roundNumber==1) {
			System.out.println("Tienes chinchón pero no puedes usarlo en la primera ronda.");
			return false;
		}
		if (cards.size() != 7) {
			return false;
		}
			
		group = new ArrayList<>();
		group.add(cards);
		return isStraight(cards);
	}
}
