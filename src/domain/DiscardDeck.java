package domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * Representa el montón de descarte del juego.
 * Funciona como una pila LIFO: la última carta descartada es la visible y la primera en poder robarse.
 * Mantiene una referencia a la carta visible ({@code topCard}) para mostrarla sin modificar la pila.
 */
public class DiscardDeck {

    /** Pila interna de cartas descartadas. */
    private Deque<Card> pile = new ArrayDeque<>();

    /** Carta visible en la cima del montón de descarte. */
    private Card topCard;

    /**
     * Añade una carta encima del montón de descarte.
     * La carta pasa a ser la nueva carta visible.
     *
     * @param card carta a descartar
     */
    public void push(Card card) {
        pile.push(card);
        topCard = card;
    }

    /**
     * Devuelve la carta visible sin eliminarla del montón.
     *
     * @return carta en la cima del montón
     * @throws IllegalStateException si el montón está vacío
     */
    public Card peek() {
        if (pile.isEmpty()) throw new IllegalStateException("La baraja de descarte está vacía.");
        return topCard;
    }

    /**
     * Elimina y devuelve la carta visible del montón.
     * Actualiza la carta visible a la siguiente carta de la pila.
     *
     * @return carta robada del descarte
     * @throws IllegalStateException si el montón está vacío
     */
    public Card pop() {
        Card removed;
        if (pile.isEmpty()) throw new IllegalStateException("La baraja de descarte está vacía.");
        removed = pile.pop();
        topCard = pile.isEmpty() ? null : pile.peek();
        return removed;
    }

    /**
     * Indica si el montón de descarte está vacío.
     *
     * @return {@code true} si no hay cartas, {@code false} en caso contrario
     */
    public boolean isEmpty() {
        return pile.isEmpty();
    }

    /**
     * Devuelve todas las cartas del montón como lista.
     * Se usa para recargar el mazo principal cuando se agota.
     *
     * @return lista con todas las cartas del montón de descarte
     */
    public ArrayList<Card> getDiscard() {
        return new ArrayList<>(pile);
    }

    /**
     * Vacía el montón de descarte.
     * Se usa al recargar el mazo con las cartas del descarte.
     */
    public void clear() {
        pile.clear();
    }

    /**
     * Devuelve una representación textual de la carta visible.
     * Si el montón está vacío, devuelve un mensaje informativo.
     *
     * @return cadena con la carta visible o mensaje de montón vacío
     */
    public String getTopCardStr() {
        return topCard != null ? topCard.toString() : "El montón de descarte está vacío.";
    }
}
