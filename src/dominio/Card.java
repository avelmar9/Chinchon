package dominio;

public class Card {
	private Suit suit;
	private Value value;

	public Card(Suit suit, Value value) {
		this.suit = suit;
		this.value = value;
	}

	public Suit getSuit() {
		return suit;
	}

	public Value getNumber() {
		return value;
	}

	@Override
	public String toString() {
		return String.format("%d %s", value.getValue(), suit.getSymbol());
	}

}
