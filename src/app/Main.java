package app;

import dominio.ConsoleInput;
import dominio.Game;
import dominio.GameSetUp;
import dominio.GameSetUpBuilder;

/**
 * Punto de entrada de la aplicación del juego Chinchón.
 * Muestra el título del juego y lanza la configuración y ejecución de la partida.
 */
public class Main {

	public static void main(String[] args) {
		System.out.println("========================================================================");
		System.out.println();
		System.out.println("-------   |     |   *   |*    |   -------   |	  |   -------   |*    |");
		System.out.println("|         |     |   |   | *   |   |         |     |   |     |   | *   |");
		System.out.println("|         |-----|   |   |  *  |   |         |-----|   |     |   |  *  |");
		System.out.println("|         |     |   |   |   * |   |         |     |   |     |   |   * |");
		System.out.println("-------   |     |   |   |    *|   -------   |     |   -------   |    *|");
		System.out.println();
		System.out.println("========================================================================");
		
		ConsoleInput input = ConsoleInput.getInstance();
		Game chinchon= new GameSetUp(input).configure();
		chinchon.start();
		
		chinchon= new GameSetUpBuilder()
				.pointsLimit(100)
				.numDecks(1)
				.addAIPlayer("robot")
				.addAIPlayer("robot2")
				.build();
		chinchon.start();
		
		
		input.close();
	}

}
