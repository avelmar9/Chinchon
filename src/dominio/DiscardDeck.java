package dominio;

import java.util.ArrayDeque;
import java.util.Deque;


public class DiscardDeck {
	private Deque<Card> pile = new ArrayDeque<>();
	private Card topCard;

    public void push(Card card) { //añadir carta encima de la baraaja de descarte
        pile.push(card);
        topCard= card;
    }

    public Card peek() { //devuelve la carta de arriba
        if (pile.isEmpty()) throw new IllegalStateException("La baraja de descarte está vacía.");
        return topCard;
    }

    public Card pop() { //quita y guarda la carta de arriba
    	Card removed;
        if (pile.isEmpty()) throw new IllegalStateException("La baraja de descarte está vacía.");
        removed=pile.pop();
        topCard=pile.isEmpty() ? null:pile.peek();
        return removed;
    }
    public boolean isEmpty() {
        return pile.isEmpty();
    }

    public String getTopCardStr() {
        return topCard != null ? topCard.toString() : "El montón de descarte está vacío.";
    }
}
