package dominio;

public abstract class Jugador  {
	protected String name;
	protected Mano hand;
	protected int points;

	public Jugador(String name) {
		this.name = name;
		hand = new Mano();
		points=0;
		
	}

	public String getName() {
		return name;
	}

	public Mano getMano() {
		return hand;
	}

	public int getPoints() {
		return points;
	}
	
}