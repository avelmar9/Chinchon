package dominio;

public class Carta {
	private Palo suit;
	private Valor value;

	public Carta(Palo suit, Valor value) {
		this.suit = suit;
		this.value = value;
	}

	public Palo getSuit() {
		return suit;
	}

	public Valor getPoints() {
		return value;
	}

	@Override
	public String toString() {
		return String.format("%d %s", value.getValue(), suit.getSymbol());
	}

}
