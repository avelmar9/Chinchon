package dominio;

import java.util.List;

public class Round {

    private final List<Player> players;
    private final Deck deck;
    private final DiscardDeck discard;
    private final CombinationValidator validator;
    private final int roundNumber;

    public Round(List<Player> players, int numDecks, int roundNumber) {
        this.players     = players;
        this.deck        = new Deck(numDecks);
        this.discard     = new DiscardDeck();
        this.validator   = new CombinationValidator();
        this.roundNumber = roundNumber;
    }

    public boolean play() {
    	boolean roundOver, closed, chinchon;
    	Player player;
        dealCards();
        discard.push(deck.takeCard());
        System.out.printf("Carta inicial del descarte: %s ", discard.getTopCardStr());

         roundOver = false;

        for (int i = 0; !roundOver; i++) {
            if (i == players.size()) i = 0;
             player = players.get(i);

             closed = player.playTurn(deck, discard, roundNumber);

            if (closed) {
                chinchon = validator.isChinchon(player.getHand().getHand())
                                   && roundNumber > 1;
                return chinchon;
            }

            if (deck.isEmpty()) {
                System.out.println("El mazo se ha agotado. La ronda termina sin cierre.");
                roundOver = true;
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
}