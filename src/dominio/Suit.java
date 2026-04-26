package dominio;

public enum Suit {
	BASTOS("🦯"), COPAS("🍷"), OROS("🥇"), ESPADAS("⚔️");

	private final String symbol;

	Suit(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

}
