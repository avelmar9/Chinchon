package dominio;

/**
 * Gestiona la configuración inicial de la partida a través de la consola.
 * Pide al usuario el número de jugadores, el número de barajas, el límite de puntos
 * y los datos de cada jugador, y construye la partida usando {@link GameSetUpBuilder}.
 */
public class GameSetUp {

    /** Instancia singleton de entrada por consola. */
    private final ConsoleInput input;

    public GameSetUp(ConsoleInput input) {
    	
    		this.input = input;
    	}
    /**
     * Solicita al usuario la configuración completa de la partida y la construye.
     *
     * @return partida configurada y lista para iniciar
     */
    
  
    public Game configure() {
        GameSetUpBuilder builder;
        int numPlayers, numDecks, pointLimit;
        boolean isHuman;
        String name;

        System.out.print("Introduce el número de jugadores (2-5):");
        numPlayers = input.readIntInRange(2, 5);

        System.out.println("Número de barajas (1 o 2):");
        numDecks = input.readIntInRange(1, 2);

        System.out.print("Límite de puntos para eliminación:");
        pointLimit = input.readIntGreaterThan(0);

        builder = new GameSetUpBuilder()
                .numDecks(numDecks)
                .pointsLimit(pointLimit);

        for (int i = 1; i <= numPlayers; i++) {
            System.out.printf("El jugador %d :¿Es humano o máquina? ('h' , 'm'):", i);
            isHuman = input.readBooleanUsingChar('h', 'm');
            System.out.printf("Introduce el nombre del jugador %d:", i);
            name = input.readString();

            if (isHuman) {
                builder.addHumanPlayer(name, input);
            } else {
                builder.addAIPlayer(name);
            }
        }

        return builder.build();
    }

	
}
