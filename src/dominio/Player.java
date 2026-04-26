package dominio;

import java.util.ArrayList;
import java.util.List;

public abstract class Player  {
	protected String name;
	protected Hand hand;
	protected int score;
	protected List<List<Card>> lastCombination = new ArrayList<>();

	public Player(String name) {
		this.name = name;
		hand = new Hand();
		score=0;
		
	}

	public String getName() {
		return name;
	}

	public Hand getHand() {
		return hand;
	}

	public int getScore() {
		return score;
	}
	 public void addScore(int points) {
	        score += points;
	    }
	 
	public boolean isEliminated(int pointsLimit) {
		
	   return score>=pointsLimit;
		
		}
	public List<List<Card>> getLastCombination() {
	    return lastCombination;
	}
	public abstract boolean playTurn(Deck deck, DiscardDeck descarte,int roundNumber);
	public abstract List<List<Card>> chooseCombination(int roundNumber, DiscardDeck discard);

	}
