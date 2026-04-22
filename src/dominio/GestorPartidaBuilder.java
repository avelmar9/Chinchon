package dominio;

public class GestorPartidaBuilder {
public int players;
public int decks;
public int pointsLimit;
public String humanPlayer;
public String aiPlayer;

public GestorPartidaBuilder( int players,int decks, int pointsLimit){
	
     this.players = players;
     this.decks = decks;
     this.pointsLimit=pointsLimit;
}

public GestorPartidaBuilder addHumanPlayer(String humanPlayer) {
	System.out.println("Introduce el nombre del jugador:");
	humanPlayer= ConsoleInput.getInstance().readString();
	
	return this;
}
public GestorPartidaBuilder addAIPlayer(String aiPlayer) {
	System.out.println("Introduce el nombre del jugador:");
	aiPlayer= ConsoleInput.getInstance().readString();
	
	return this;
}
public GestorPartida build() {
	
	return new GestorPartida(this);
}
}
