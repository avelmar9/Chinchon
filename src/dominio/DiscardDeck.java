package dominio;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;


public class DiscardDeck {
	private Deque<Card> pile = new ArrayDeque<>();
	private Card topCard;

    public void push(Card card) {
        pile.push(card);
        topCard= card;
    }

    public Card peek() {
        if (pile.isEmpty()) throw new IllegalStateException("La baraja de descarte está vacía.");
        return topCard;
    }

    public Card pop() {
    	Card removed;
        if (pile.isEmpty()) throw new IllegalStateException("La baraja de descarte está vacía.");
        removed=pile.pop();
        topCard=pile.isEmpty() ? null:pile.peek();
        return removed;
    }
    public boolean isEmpty() {
        return pile.isEmpty();
    }

    public ArrayList<Card> getDiscard() {
    	
    	ArrayList<Card> discard= new ArrayList<>();
    	for(Card card: pile) {
    		discard.add(card);
    	}
		return discard ;
	}

	public String getTopCardStr() {
        return topCard != null ? topCard.toString() : "El montón de descarte está vacío.";
    }
}
