package dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa el mazo de cartas del juego.
 * Puede inicializarse con una o dos barajas españolas de 40 cartas cada una.
 * Las cartas se barajan automáticamente al inicializar cada baraja.
 */
public class Deck {

    /** Lista de cartas del mazo. */
    private final List<Card> cards;

    /**
     * Construye el mazo con el número de barajas indicado.
     * Cada baraja añade 40 cartas (4 palos × 10 valores) y las baraja.
     *
     * @param numDecks número de barajas a usar (1 o 2)
     */
    public Deck(int numDecks) {
        this.cards = new ArrayList<Card>();
        for (int d = 0; d < numDecks; d++) {
            initializeDeck();
        }
    }

    /**
     * Inicializa una baraja completa de 40 cartas y la baraja.
     * Se llama una vez por cada baraja configurada.
     */
    private void initializeDeck() {
        for (Suit tipo : Suit.values()) {
            for (Value valor : Value.values()) {
                cards.add(new Card(tipo, valor));
            }
        }
        shuffle();
    }

    /**
     * Roba la carta de la cima del mazo y la elimina.
     *
     * @return carta robada
     * @throws IllegalStateException si el mazo está vacío
     */
    public Card takeCard() {
        if (cards.isEmpty()) throw new IllegalStateException("El mazo está vacío.");
        return cards.removeLast();
    }

    /**
     * Añade una lista de cartas al mazo.
     * Se usa para recargar el mazo con las cartas del montón de descarte.
     *
     * @param cards lista de cartas a añadir
     */
    public void addCards(List<Card> cards) {
        this.cards.addAll(cards);
    }
    
    /**
     * Baraja aleatoriamente las cartas del mazo.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Indica si el mazo está vacío.
     *
     * @return {@code true} si no quedan cartas, {@code false} en caso contrario
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

}