package dominio;

import java.util.ArrayList;
import java.util.List;

public class Round {

    private final List<Player> players;
    private final Deck deck;
    private final DiscardDeck discard;
    private final int roundNumber;

    public Round(List<Player> players, int numDecks, int roundNumber) {
        this.players     = players;
        this.deck        = new Deck(numDecks);
        this.discard     = new DiscardDeck();
  
        this.roundNumber = roundNumber;
    }

    public boolean play() {
    	List<Card> actualDeck= deck.getCards();
    	boolean roundOver;
    	Player player;
        dealCards();
        takeInitialDiscardCard();
         roundOver = false;

        for (int i = 0; !roundOver; i++) {
            if (i == players.size()) i = 0;
             player = players.get(i);

              player.playTurn(deck, discard, roundNumber);

            

            if (deck.isEmpty()) {
                System.out.println("El mazo se ha agotado. Pasando el mazo de descarte al mazo principal...");
               actualDeck=refillDeck();
               takeInitialDiscardCard();
             
            }
        }

        return false;
    }

    private void dealCards() {
        for (int i = 0; i < 7; i++) {
            for (Player player : players) {
                player.getHand().addCard(deck.takeCard());
            }
        }
    }
	private ArrayList<Card> refillDeck() {
		ArrayList<Card> newDeck ;
		newDeck = discard.getDiscard();
	    return newDeck;
	}
	private void takeInitialDiscardCard() {
		 discard.push(deck.takeCard());
	        System.out.printf("Carta inicial del descarte: %s ", discard.getTopCardStr());

	}
}