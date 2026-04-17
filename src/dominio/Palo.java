package dominio;

public enum Palo {
	BASTOS("🦯"), COPAS("🍷"), OROS("🥇"), ESPADAS("⚔️");

	private String symbol;

	Palo(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

}
