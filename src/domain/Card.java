package domain;

/**
 * Representa una carta de la baraja española.
 * Cada carta tiene un palo ({@link Suit}) y un valor ({@link Value}).
 */
public class Card {

    /** Palo de la carta. */
    private Suit suit;

    /** Valor de la carta. */
    private Value value;

    /**
     * Construye una carta con el palo y valor indicados.
     *
     * @param suit  palo de la carta
     * @param value valor de la carta
     */
    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
    }

    /**
     * Devuelve el palo de la carta.
     *
     * @return palo de la carta
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Devuelve el valor de la carta.
     *
     * @return valor de la carta
     */
    public Value getNumber() {
        return value;
    }

    /**
     * Devuelve una representación textual de la carta con su valor numérico y símbolo de palo.
     * Ejemplo: {@code "7 ⚔️"}, {@code "10 🍷"}.
     *
     * @return cadena con el valor y el símbolo del palo
     */
    @Override
    public String toString() {
        return String.format("%d %s", value.getValue(), suit.getSymbol());
    }
}