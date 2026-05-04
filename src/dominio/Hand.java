package dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa la mano de un jugador, es decir, el conjunto de cartas que tiene en su poder.
 * Permite añadir, eliminar y consultar las cartas, así como calcular la puntuación total.
 */
public class Hand {

    /** Lista de cartas en la mano. */
    private List<Card> hand = new ArrayList<>();

    /**
     * Devuelve la lista interna de cartas.
     * Permite acceso directo a la lista para operaciones de backup y restauración.
     *
     * @return lista de cartas de la mano
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Añade una carta a la mano.
     *
     * @param card carta a añadir
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Devuelve el número de cartas en la mano.
     *
     * @return número de cartas
     */
    public int numCards() {
        return hand.size();
    }

    /**
     * Elimina y devuelve la carta en la posición indicada.
     *
     * @param position índice base 0 de la carta a eliminar
     * @return carta eliminada
     */
    public Card removeCard(int position) {
        return hand.remove(position);
    }

    /**
     * Calcula la suma de los puntos de todas las cartas de la mano.
     *
     * @return total de puntos de la mano
     */
    public int totalPoints() {
        return hand.stream()
                .mapToInt(p -> p.getNumber().getValue())
                .sum();
    }

    /**
     * Muestra por consola las cartas de la mano numeradas desde 1.
     * Ejemplo de salida: {@code 1. 7 ⚔️}
     */
    public void showCards() {
        for (int i = 0; i < numCards(); i++) {
            System.out.printf("%d. %s%n", i + 1, hand.get(i));
        }
    }
}