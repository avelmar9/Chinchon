package dominio;

import java.util.ArrayList;
import java.util.List;

import app.Game;

public class GameSetUpBuilder {
	private List<Player> players = new ArrayList<>();
	private int numDecks;
	private int pointsLimit;

	public GameSetUpBuilder numDecks(int numDecks) {
		this.numDecks = numDecks;
		return this;
	}

	public GameSetUpBuilder pointsLimit(int pointsLimit) {
		this.pointsLimit = pointsLimit;
		return this;
	}

	public GameSetUpBuilder addHumanPlayer(String name) {
		players.add(new HumanPlayer(name));
		return this;
	}

	public GameSetUpBuilder addAIPlayer(String name) {
		players.add(new AIPlayer(name));
		return this;
	}

	public Game build() {
		if (players.size() < 2)
			throw new IllegalStateException("Se necesitan al menos 2 jugadores.");
		if (players.size() > 5)
			throw new IllegalStateException("El máximo de jugadores es 5.");
		if (numDecks < 1 || numDecks > 2)
			throw new IllegalStateException("El número de barajas debe ser 1 o 2.");
		if (pointsLimit <= 0)
			throw new IllegalStateException("El límite de puntos debe ser mayor que 0.");
		return new Game(players, numDecks,pointsLimit);
	}
}
