package domain;

/**
 * Representa los cuatro palos de la baraja española.
 * Cada palo tiene asociado un símbolo emoji para su representación visual.
 */
public enum Suit {

    /** Palo de bastos. */
	CLUBS("🦯"),

    /** Palo de copas. */
    CUPS("🍷"),

    /** Palo de oros. */
    GOLD("🥇"),

    /** Palo de espadas. */
    SWORDS("⚔️");

    /** Emoji del palo. */
    private final String symbol;

    /**
     * Constructor del enum.
     *
     * @param symbol emoji que representa el palo
     */
    Suit(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Devuelve el emoji del palo.
     *
     * @return símbolo del palo
     */
    public String getSymbol() {
        return symbol;
    }
}