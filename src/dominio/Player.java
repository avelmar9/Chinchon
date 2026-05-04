package dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta que representa un jugador del Chinchón.
 * Define el estado común (nombre, mano, puntuación, última combinación)
 * y el flujo de turno mediante el patrón Template Method.
 * <p>
 * Las subclases {@link HumanPlayer} y {@link AIPlayer} implementan
 * las fases concretas del turno: robar ({@link #takingPhase}) y cerrar ({@link #closingPhase}).
 * </p>
 */
public abstract class Player {

    /** Nombre del jugador. */
    protected String name;

    /** Mano del jugador con sus cartas actuales. */
    protected Hand hand;

    /** Puntuación acumulada del jugador a lo largo de la partida. */
    protected int score;

    /** Última combinación de grupos formada por el jugador al cerrar una ronda. */
    protected List<List<Card>> lastCombination = new ArrayList<>();
    
    /** Detecta si el jugador tiene chinchon */
    protected boolean chinchon = false;
    
    /** Validador de combinaciones de grupos de cartas*/
    protected final CombinationValidator validator = new CombinationValidator();

    /**
     * Construye un jugador con el nombre indicado.
     * Inicializa la mano vacía y la puntuación a cero.
     *
     * @param name nombre del jugador
     */
    public Player(String name) {
        this.name = name;
        hand = new Hand();
        score = 0;
    }

    /**
     * Devuelve el nombre del jugador.
     *
     * @return nombre del jugador
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve la mano del jugador.
     *
     * @return mano del jugador
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Devuelve la puntuación acumulada del jugador.
     *
     * @return puntuación del jugador
     */
    public int getScore() {
        return score;
    }

    /**
     * Añade puntos a la puntuación acumulada del jugador.
     *
     * @param points puntos a añadir
     */
    public void addScore(int points) {
        score += points;
    }

    /**
     * Indica si el jugador ha sido eliminado por alcanzar o superar el límite de puntos.
     *
     * @param pointsLimit límite de puntos de la partida
     * @return {@code true} si el jugador está eliminado
     */
    public boolean isEliminated(int pointsLimit) {
        return score >= pointsLimit;
    }

    /**
     * Limpia la mano del jugador y su última combinación.
     * Se llama al inicio de cada ronda para preparar al jugador.
     */
    public void resetHand() {
        hand.getHand().clear();
        lastCombination.clear();
        chinchon = false;
    }

    /**
     * Comprueba si la mano actual del jugador forma un chinchón.
     *
     * @return {@code true} si la mano es un chinchón
     */
    public boolean hasChinchon() {
    	
        return chinchon;
    }

    /**
     * Devuelve la última combinación de grupos formada por el jugador.
     * Se usa al final de la ronda para calcular las cartas no combinadas.
     *
     * @return lista de grupos de la última combinación
     */
    public List<List<Card>> getLastCombination() {
        return lastCombination;
    }

    /**
     * Ejecuta el turno del jugador siguiendo el patrón Template Method.
     * Primero ejecuta la fase de robo y, si no se cierra en ella, la fase de cierre o descarte.
     *
     * @param deck        mazo principal del que robar
     * @param discard     montón de descarte
     * @param roundNumber número de ronda actual
     * @return {@code true} si el jugador cierra la ronda o saca chinchón
     */
    public final boolean playTurn(Deck deck, DiscardDeck discard, int roundNumber) {
        System.out.printf("%nTurno de %s:%n", name);
        if (takingPhase(deck, discard, roundNumber)) {
            return true;
        }
        return closingPhase(discard, roundNumber);
    }

    /**
     * Fase de robo del turno. El jugador roba una carta del mazo o del descarte.
     * También puede detectar un chinchón antes de robar.
     *
     * @param deck        mazo principal
     * @param discard     montón de descarte
     * @param roundNumber número de ronda actual
     * @return {@code true} si el jugador tiene chinchón válido y gana la partida
     */
    protected abstract boolean takingPhase(Deck deck, DiscardDeck discard, int roundNumber);

    /**
     * Fase de cierre o descarte del turno. El jugador decide si intenta cerrar
     * formando combinaciones o simplemente descarta una carta.
     *
     * @param discard     montón de descarte
     * @param roundNumber número de ronda actual
     * @return {@code true} si el jugador cierra la ronda
     */
    protected abstract boolean closingPhase(DiscardDeck discard, int roundNumber);

    /**
     * Forma y devuelve los grupos de combinación del jugador al intentar cerrar.
     *
     * @param roundNumber número de ronda actual
     * @param discard     montón de descarte, usado para descartar la carta suelta al cerrar
     * @return lista de grupos combinados, o {@code null} si el cierre no es válido
     */
    protected abstract List<List<Card>> chooseCombination(int roundNumber, DiscardDeck discard);
}