package dominio;

import app.Game;

public class GameSetUp {
	private final ConsoleInput input = ConsoleInput.getInstance();

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
				builder.addHumanPlayer(name);
			} else {
				builder.addAIPlayer(name);
			}

		}
		return builder.build();

	}

}
