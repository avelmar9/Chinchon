package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una ronda del juego del Chinchón.
 * Gestiona el reparto de cartas, el turno de cada jugador y la recarga del mazo
 * cuando se agota. Devuelve el jugador que cierra la ronda, o {@code null}
 * si el mazo se agota sin que nadie cierre.
 */
public class Round {

    /** Lista de jugadores activos en esta ronda. */
    private final List<Player> players;

    /** Mazo de cartas de la ronda. */
    private final Deck deck;

    /** Montón de descarte de la ronda. */
    private final DiscardDeck discard;

    /** Número de ronda actual dentro de la partida. */
    private final int roundNumber;

    /**
     * Construye una ronda con los jugadores, número de barajas y número de ronda indicados.
     *
     * @param players     lista de jugadores activos
     * @param numDecks    número de barajas a usar (1 o 2)
     * @param roundNumber número de ronda actual
     */
    public Round(List<Player> players, int numDecks, int roundNumber) {
        this.players = players;
        this.deck = new Deck(numDecks);
        this.discard = new DiscardDeck();
        this.roundNumber = roundNumber;
    }

    /**
     * Ejecuta la ronda completa.
     * Resetea y reparte las cartas, coloca la carta inicial de descarte
     * y gestiona los turnos de los jugadores en orden hasta que alguien cierre
     * o el mazo se agote.
     *
     * @return jugador que cierra la ronda, o {@code null} si el mazo se agota
     */
    public Player playRound() {
        boolean closed, roundOver = false;
        Player player, closer = null;

        for (Player p : players) {
            p.resetHand();
        }
        dealCards();
        takeInitialDiscardCard();

        for (int i = 0; !roundOver; i++) {
            if (i == players.size()) i = 0;
            player = players.get(i);

            if (deck.isEmpty()) {
                System.out.println("El mazo se ha agotado. Pasando el mazo de descarte al mazo principal...");
                refillDeck();

                if (discard.isEmpty()) {
                    takeInitialDiscardCard();
                }
            }

            closed = player.playTurn(deck, discard, roundNumber);

            if (closed) {
                closer = player;
                roundOver = true;
                closer.getHand().showCards();
            }
        }

        return closer;
    }

    /**
     * Reparte 7 cartas a cada jugador al inicio de la ronda.
     */
    private void dealCards() {
        for (int i = 0; i < 7; i++) {
            for (Player player : players) {
                player.getHand().addCard(deck.takeCard());
            }
        }
    }

    /**
     * Recarga el mazo principal con las cartas del montón de descarte
     * cuando el mazo se agota. Vacía el descarte y baraja las cartas recuperadas.
     */
    private void refillDeck() {
        ArrayList<Card> cards = discard.getDiscard();
        discard.clear();
        deck.addCards(cards);
        deck.shuffle();
        System.out.printf(" Mazo regenerado con %d cartas.", cards.size() );
    }

    /**
     * Roba la primera carta del mazo y la coloca boca arriba en el montón de descarte
     * para iniciar la ronda.
     */
    private void takeInitialDiscardCard() {
        discard.push(deck.takeCard());
        System.out.printf("Carta inicial del descarte: %s ", discard.getTopCardStr());
    }
}