package dominio;

import java.util.ArrayList;
import java.util.List;

public class Baraja {
	private List<Carta> cards;
	
	public Baraja() {
		this.cards = new ArrayList<Carta>();
		inicializarMazo();
	}

	private void inicializarMazo() {
		for (Palo tipo : Palo.values()) {
			for (Valor valor : Valor.values()) {
				cards.add(new Carta(tipo, valor));
			}
		}
	}
}