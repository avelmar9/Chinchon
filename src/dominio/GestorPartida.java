package dominio;

public class GestorPartida {
	private int players;
	private int decks;
	private int pointsLimit;
	private String humanPlayer;
	private String aiPlayer;
	
	public GestorPartida(GestorPartidaBuilder gestorPartidaBuilder) {
		players=gestorPartidaBuilder.players;
		decks=gestorPartidaBuilder.decks;
		pointsLimit=gestorPartidaBuilder.pointsLimit;
		humanPlayer=gestorPartidaBuilder.humanPlayer;
		aiPlayer=gestorPartidaBuilder.aiPlayer;
	}

	@Override
	public String toString() {
		return String.format("-Número de jugadores: %d%n-Número de barajas:%d%n-Límite de puntuación: %d%n-Jugadores:%n");
	}

}
