package app;

import java.util.ArrayList;
import java.util.List;

import dominio.Card;
import dominio.CombinationValidator;
import dominio.Player;
import dominio.Round;

public class Game {

	private List<Player> players;
	private int numDecks;
	private int pointsLimit;

	public Game(List<Player> players, int numDecks, int pointsLimit) {
		this.players = players;
		this.numDecks = numDecks;
		this.pointsLimit = pointsLimit;
	}

	public void start() {
		int roundNumber = 1;
		List<Player> activePlayers;
		Round round;
		boolean chinchon;
		Player winner;
		System.out.println("\n=== Comienza el Chinchón ===");
		System.out.printf("Límite de puntos:%s%n ", pointsLimit);

		

		while (!isOver()) {
			
			System.out.printf("%n===RONDA %d===%n", roundNumber);
			

			 activePlayers = getActivePlayers();
			round = new Round(activePlayers, numDecks, roundNumber);
			 chinchon= round.play();

			if (chinchon) {
				winner = getChinchonWinner();
				System.out.printf("%n %s gana la partida con CHINCHÓN!", winner.getName());
				return;
			}

			scoreRound();
			eliminateIfNeeded();
			roundNumber++;
		}

		winner = getWinner();
		System.out.printf("%n %s gana la partida con %s puntos",  winner.getName(),  winner.getScore());
	}

	private void scoreRound() {
		List<List<Card>> combinations;
		List<Card> combinedCards;
		int pts = 0;
		boolean perfectClose;
		System.out.println("\n=== Puntuación de la ronda ===");

		for (Player player : getActivePlayers()) {
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
			} else {
				System.out.printf("%s -> %d puntos%n", player.getName(), pts);
			}

			player.addScore(Math.max(0, player.getScore() + pts) - player.getScore());
		}

		printScoreboard();
	}

	private void eliminateIfNeeded() {
		for (Player player : players) {
			if (player.isEliminated(pointsLimit)) {
				System.out.printf("%s ha sido eliminado con %d puntos.%n", player.getName(), player.getScore());
			}
		}
	}

	private boolean isOver() {
		return getActivePlayers().size() <= 1;
	}

	private List<Player> getActivePlayers() {
		List<Player> active = new ArrayList<>();
		for (Player player : players) {
			if (!player.isEliminated(pointsLimit))
				active.add(player);
		}
		return active;
	}

	private Player getChinchonWinner() {
		for (Player player : players) {
			if (new CombinationValidator().isChinchon(player.getHand().getHand())) {
				return player;
			}
		}
		return players.get(0);
	}

	private Player getWinner() {
		return getActivePlayers().stream().min((a, b) -> Integer.compare(a.getScore(), b.getScore()))
				.orElse(players.get(0));
	}

	private void printScoreboard() {
		System.out.println("\n--- Marcador ---");
		for (Player player : players) {
			System.out.printf("%s -> %d puntos%s%n", player.getName(), player.getScore(),
					player.isEliminated(pointsLimit) ? " ELIMINADO" : "");
		}
	}
}