package app;

import dominio.GameSetUp;

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
		Game chinchon= new GameSetUp().configure();
		chinchon.start();
	}

}
