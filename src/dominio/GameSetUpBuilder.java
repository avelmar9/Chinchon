package dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder para construir un objeto {@link Game} paso a paso.
 * Permite configurar el número de barajas, el límite de puntos
 * y añadir jugadores humanos o IA antes de construir la partida.
 */
public class GameSetUpBuilder {

    /** Lista de jugadores añadidos al builder. */
    private List<Player> players = new ArrayList<>();

    /** Número de barajas a usar en la partida. */
    private int numDecks;

    /** Límite de puntos para la eliminación de jugadores. */
    private int pointsLimit;

    /**
     * Establece el número de barajas.
     *
     * @param numDecks número de barajas (1 o 2)
     * @return este builder para encadenamiento
     */
    public GameSetUpBuilder numDecks(int numDecks) {
        this.numDecks = numDecks;
        return this;
    }

    /**
     * Establece el límite de puntos para la eliminación.
     *
     * @param pointsLimit límite de puntos (debe ser mayor que 0)
     * @return este builder para encadenamiento
     */
    public GameSetUpBuilder pointsLimit(int pointsLimit) {
        this.pointsLimit = pointsLimit;
        return this;
    }

    /**
     * Añade un jugador humano con el nombre indicado.
     *
     * @param name nombre del jugador humano
     * @return este builder para encadenamiento
     */
    public GameSetUpBuilder addHumanPlayer(String name, ConsoleInput input) {
        players.add(new HumanPlayer(name, input));
        return this;
    }

    /**
     * Añade un jugador IA con el nombre indicado.
     *
     * @param name nombre del jugador IA
     * @return este builder para encadenamiento
     */
    public GameSetUpBuilder addAIPlayer(String name) {
        players.add(new AIPlayer(name));
        return this;
    }

    /**
     * Construye y devuelve el objeto {@link Game} con la configuración establecida.
     * Valida que la configuración sea coherente antes de construir.
     *
     * @return partida configurada y lista para iniciar
     * @throws IllegalStateException si hay menos de 2 o más de 5 jugadores,
     *                               si el número de barajas no es 1 o 2,
     *                               o si el límite de puntos no es mayor que 0
     */
    public Game build() {
        if (players.size() < 2)
            throw new IllegalStateException("Se necesitan al menos 2 jugadores.");
        if (players.size() > 5)
            throw new IllegalStateException("El máximo de jugadores es 5.");
        if (numDecks < 1 || numDecks > 2)
            throw new IllegalStateException("El número de barajas debe ser 1 o 2.");
        if (pointsLimit <= 0)
            throw new IllegalStateException("El límite de puntos debe ser mayor que 0.");
        return new Game(players, numDecks, pointsLimit);
    }
}