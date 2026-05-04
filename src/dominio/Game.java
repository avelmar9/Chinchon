package dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Controla el flujo completo de una partida de Chinchón.
 * Gestiona las rondas, la puntuación de los jugadores, las eliminaciones
 * y determina el ganador al final de la partida.
 */
public class Game {

    /** Lista de todos los jugadores de la partida. */
    private List<Player> players;

    /** Número de barajas usadas en cada ronda. */
    private int numDecks;

    /** Límite de puntos para la eliminación de jugadores. */
    private int pointsLimit;

    /**
     * Construye una partida con los jugadores, número de barajas y límite de puntos indicados.
     *
     * @param players     lista de jugadores
     * @param numDecks    número de barajas (1 o 2)
     * @param pointsLimit límite de puntos para eliminación
     */
    public Game(List<Player> players, int numDecks, int pointsLimit) {
        this.players = players;
        this.numDecks = numDecks;
        this.pointsLimit = pointsLimit;
    }

    /**
     * Inicia y ejecuta la partida completa.
     * Juega rondas hasta que solo quede un jugador activo o alguien saque chinchón.
     * Al finalizar, muestra el ganador y su puntuación.
     */
    public void start() {
        int roundNumber = 1;
        List<Player> activePlayers;
        Round round;
        boolean chinchon = false;
        Player winner, closer;

        System.out.println("\n=== Comienza el Chinchón ===");
        System.out.printf("Límite de puntos:%s%n ", pointsLimit);

        while (!isOver() && !chinchon) {
            System.out.printf("%n===RONDA %d===%n", roundNumber);

            activePlayers = getActivePlayers();
            round = new Round(activePlayers, numDecks, roundNumber);
            closer = round.playRound();

            if (closer != null) {
                if (closer.hasChinchon() && roundNumber > 1) {
                    System.out.printf("%n %s gana la partida con CHINCHÓN!", closer.getName());
                    closer.getHand().showCards();
                    chinchon = true;
                } else {
                    scoreRound();
                    eliminateIfNeeded();
                    roundNumber++;
                }
            }
        }

        winner = getWinner();
        winner.getHand().showCards();
        System.out.printf("%n %s gana la partida con %s puntos", winner.getName(), winner.getScore());
    }

    /**
     * Calcula y suma los puntos de las cartas no combinadas de cada jugador activo
     * al final de una ronda. Aplica el bonus de -10 puntos al jugador con cierre perfecto
     * (7 cartas combinadas).
     */
    private void scoreRound() {
        List<List<Card>> combinations;
        List<Card> combinedCards;
        int pts;
        boolean perfectClose;

        System.out.println("\n=== Puntuación de la ronda ===");

        for (Player player : getActivePlayers()) {
            pts = 0;
            combinations = player.getLastCombination();

            combinedCards = new ArrayList<>();
            for (List<Card> group : combinations) {
                combinedCards.addAll(group);
            }

            for (Card card : player.getHand().getHand()) {
                if (!combinedCards.contains(card)) {
                    pts += card.getNumber().getValue();
                }
            }

            perfectClose = combinedCards.size() == 7 && pts == 0;
            if (perfectClose) {
                pts = -10;
                System.out.printf("%s cierre perfecto -> -10 puntos%n", player.getName());
                player.addScore(pts);
            } else {
                System.out.printf("%s -> %d puntos%n", player.getName(), pts);
                player.addScore(pts);
            }
        }

        printScoreboard();
    }

    /**
     * Comprueba qué jugadores han alcanzado o superado el límite de puntos
     * y los notifica como eliminados.
     */
    private void eliminateIfNeeded() {
        for (Player player : players) {
            if (player.isEliminated(pointsLimit)) {
                System.out.printf("%s ha sido eliminado con %d puntos.%n",
                        player.getName(), player.getScore());
            }
        }
    }

    /**
     * Indica si la partida ha terminado porque solo queda un jugador activo.
     *
     * @return {@code true} si quedan 1 o menos jugadores activos
     */
    private boolean isOver() {
        return getActivePlayers().size() <= 1;
    }

    /**
     * Devuelve la lista de jugadores activos (no eliminados).
     *
     * @return lista de jugadores no eliminados
     */
    private List<Player> getActivePlayers() {
        List<Player> active = new ArrayList<>();
        for (Player player : players) {
            if (!player.isEliminated(pointsLimit))
                active.add(player);
        }
        return active;
    }

    /**
     * Devuelve el jugador activo con menor puntuación acumulada.
     *
     * @return jugador ganador
     */
    private Player getWinner() {
        return getActivePlayers().stream()
                .min((a, b) -> Integer.compare(a.getScore(), b.getScore()))
                .orElse(players.get(0));
    }

    /**
     * Muestra el marcador actual con la puntuación de todos los jugadores.
     * Indica qué jugadores han sido eliminados.
     */
    private void printScoreboard() {
        System.out.println("\n--- Marcador ---");
        for (Player player : players) {
            System.out.printf("%s -> %d puntos%s%n", player.getName(), player.getScore(),
                    player.isEliminated(pointsLimit) ? " ELIMINADO" : "");
        }
    }
}