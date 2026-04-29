package dominio;

import java.util.List;

public class AIPlayer extends Player{

	public AIPlayer(String name) {
		super(name);
		
	}


	@Override
	public List<List<Card>> chooseCombination(int roundNumber, DiscardDeck discard) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean takingPhase(Deck deck, DiscardDeck discard, int roundNumber) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean closingPhase(DiscardDeck discard, int roundNumber) {
		// TODO Auto-generated method stub
		return false;
	}



}
