package dominio;

import java.util.List;

public class AIPlayer extends Player{

	public AIPlayer(String name) {
		super(name);
		
	}


	@Override
	public boolean playTurn(Deck deck, DiscardDeck descarte, int roundNumber) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<List<Card>> chooseCombination(int roundNumber, DiscardDeck discard) {
		// TODO Auto-generated method stub
		return null;
	}



}
