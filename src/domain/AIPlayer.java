package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un jugador controlado por la máquina (IA) en el Chinchón.
 * Toma decisiones automáticas en cada fase del turno:
 * <ul>
 *   <li>Roba del descarte si reduce sus puntos sueltos, si no roba del mazo.</li>
 *   <li>Cierra si tiene 6 o 7 cartas combinables.</li>
 *   <li>Descarta siempre la carta no combinada de mayor valor.</li>
 * </ul>
 */
public class AIPlayer extends Player {

    /**
     * Construye un jugador IA con el nombre indicado.
     *
     * @param name nombre del jugador IA
     */
    public AIPlayer(String name) {
        super(name);
    }

    /**
     * Fase de robo de la IA.
     * Evalúa si la carta visible del descarte mejora su mano reduciendo los puntos 
     * de las cartas no combinadas. Si mejora, roba del descarte; si no, roba del mazo
     * Después comprueba si tiene chinchón.
     *
     * @param deck        mazo principal
     * @param discard     montón de descarte
     * @param roundNumber número de ronda actual
     * @return {@code true} si la IA tiene chinchón válido
     */
    @Override
    protected boolean takingPhase(Deck deck, DiscardDeck discard, int roundNumber) {
        Card topDiscard;
        int pointsWithDiscard;
        int currentPoints;

        topDiscard = discard.peek();
        currentPoints = uncombinedPoints();
        
        hand.addCard(topDiscard);
        pointsWithDiscard = uncombinedPoints();
        hand.removeCard(hand.numCards() - 1);

        if (pointsWithDiscard < currentPoints) {
            discard.pop();
            hand.addCard(topDiscard);
            System.out.printf("%s roba del descarte: %s%n", name, topDiscard);
        } else {
            hand.addCard(deck.takeCard());
            System.out.printf("%s roba del mazo.%n", name);
        }

        if (validator.isChinchon(hand.getHand())) {
            if (roundNumber > 1) {
                System.out.printf("%s tiene CHINCHÓN.%n", name);
                lastCombination = new ArrayList<>();
                lastCombination.add(new ArrayList<>(hand.getHand()));
                chinchon = true;
                return true;
            }
        }

        return false;
    }

    /**
     * Fase de cierre o descarte de la IA.
     * Intenta cerrar si tiene combinaciones válidas; si no, descarta la peor carta.
     *
     * @param discard     montón de descarte
     * @param roundNumber número de ronda actual
     * @return {@code true} si la IA cierra la ronda
     */
    @Override
    protected boolean closingPhase(DiscardDeck discard, int roundNumber) {
        List<List<Card>> groups;

        groups = chooseCombination(roundNumber, discard);

        if (groups != null) {
            System.out.printf("%s cierra la ronda.%n", name);
            return true;
        }

        discardWorstCard(discard);
        return false;
    }

    /**
     * Calcula automáticamente los mejores grupos de combinación.
     * Si puede cerrar (6 o 7 combinadas), descarta la carta suelta con valor entre 1 y 5 y devuelve los grupos.
     * Si no puede cerrar, devuelve {@code null}.
     *
     * @param roundNumber número de ronda actual
     * @param discard     montón de descarte
     * @return lista de grupos combinados si puede cerrar, {@code null} en caso contrario
     */
    @Override
    protected List<List<Card>> chooseCombination(int roundNumber, DiscardDeck discard) {
     
        List<List<Card>> groups;
        List<Card> combinedCards;
        List<Card> looseCards;
        boolean canClose;
        boolean validDiscardRange;
        Card worst;

        groups = findBestGroups();
        combinedCards = new ArrayList<>();

        for (List<Card> group : groups) {
            combinedCards.addAll(group);
        }

        looseCards = new ArrayList<>(hand.getHand());
        looseCards.removeAll(combinedCards);

        
        validDiscardRange = true;
        for (Card card : looseCards) {
            if (card.getNumber().getValue() < 1 || card.getNumber().getValue() > 5) {
                validDiscardRange = false;
            }
        }
        
        canClose = (looseCards.size() == 1 && combinedCards.size() == 7)
                || (looseCards.size() == 2 && combinedCards.size() == 6);

        
        if (!canClose || !validDiscardRange || !validator.isValidCombination(groups)) {
            return null;
        }

        if (looseCards.size() == 2) {
            worst = looseCards.get(0).getNumber().getValue() >= looseCards.get(1).getNumber().getValue()
                    ? looseCards.get(0) : looseCards.get(1);
            discard.push(worst);
            System.out.printf("%s descarta: %s%n", name, worst);
        } else {
            discard.push(looseCards.get(0));
            System.out.printf("%s descarta: %s%n", name, looseCards.get(0));
        }

        lastCombination = groups;
        return groups;
    }

    /**
     * Busca los mejores grupos combinables en la mano de la IA.
     * Primero busca grupos de iguales y luego escaleras con las cartas restantes.
     *
     * @return lista de grupos combinables encontrados
     */
    private List<List<Card>> findBestGroups() {
        List<List<Card>> groups;
        List<Card> remaining;

        groups = new ArrayList<>();
        remaining = new ArrayList<>(hand.getHand());

        groups.addAll(findEquals(remaining));
        for (List<Card> g : groups) {
            remaining.removeAll(g);
        }
        groups.addAll(findStraights(remaining));

        return groups;
    }

    /**
     * Busca grupos de cartas del mismo valor en la lista dada.
     * Solo incluye grupos de 3 o más cartas.
     *
     * @param cards lista de cartas donde buscar
     * @return lista de grupos de iguales encontrados
     */
    private List<List<Card>> findEquals(List<Card> cards) {
        List<List<Card>> groups;
        List<Card> group;

        groups = new ArrayList<>();

        for (Value v : Value.values()) {
            group = new ArrayList<>();
            for (Card c : cards) {
                if (c.getNumber() == v) {
                    group.add(c);
                }
            }
            if (group.size() >= 3) {
                groups.add(group);
            }
        }

        return groups;
    }

    /**
     * Busca escaleras en la lista de cartas dada.
     * Agrupa cartas del mismo palo con valores consecutivos (permitiendo el salto 7→10).
     * Solo incluye escaleras de 3 o más cartas.
     *
     * @param cards lista de cartas donde buscar
     * @return lista de escaleras encontradas
     */
    private List<List<Card>> findStraights(List<Card> cards) {
        List<List<Card>> groups;
        List<Card> bySuit;
        List<Card> run;
        Card prev;
        Card curr;
        boolean consecutive;

        groups = new ArrayList<>();

        for (Suit s : Suit.values()) {
            bySuit = new ArrayList<>();
            for (Card c : cards) {
                if (c.getSuit() == s) {
                    bySuit.add(c);
                }
            }
            bySuit.sort((a, b) -> Integer.compare(a.getNumber().getValue(), b.getNumber().getValue()));

            run = new ArrayList<>();
            for (int i = 0; i < bySuit.size(); i++) {
                curr = bySuit.get(i);
                if (run.isEmpty()) {
                    run.add(curr);
                } else {
                    prev = run.get(run.size() - 1);
                    consecutive = curr.getNumber().getValue() == prev.getNumber().getValue() + 1
                            || (prev.getNumber().getValue() == 7 && curr.getNumber().getValue() == 10);
                    if (consecutive) {
                        run.add(curr);
                    } else {
                        run = new ArrayList<>();
                        run.add(curr);
                    }
                }
            }
            if (run.size() >= 3) {
                groups.add(run);
            }
        }

        return groups;
    }

    /**
     * Calcula los puntos de las cartas no combinadas en la mano actual.
     * Se usa para comparar si robar del descarte o del mazo es más beneficioso.
     *
     * @return suma de puntos de las cartas no combinadas
     */
    private int uncombinedPoints() {
        List<List<Card>> groups;
        List<Card> combined;
        int pts;

        groups = findBestGroups();
        combined = new ArrayList<>();

        for (List<Card> g : groups) {
            combined.addAll(g);
        }

        pts = 0;
        for (Card c : hand.getHand()) {
            if (!combined.contains(c)) {
                pts += c.getNumber().getValue();
            }
        }

        return pts;
    }

    /**
     * Descarta la carta no combinada de mayor valor de la mano.
     * Si todas las cartas están combinadas, descarta la última carta de la mano.
     *
     * @param discard montón de descarte
     */
    private void discardWorstCard(DiscardDeck discard) {
        List<List<Card>> groups;
        List<Card> combined;
        Card worst;
        int worstPts;

        groups = findBestGroups();
        combined = new ArrayList<>();

        for (List<Card> g : groups) {
            combined.addAll(g);
        }

        worst = null;
        worstPts = -1;

        for (Card c : hand.getHand()) {
            if (!combined.contains(c) && c.getNumber().getValue() > worstPts) {
                worstPts = c.getNumber().getValue();
                worst = c;
            }
        }

        if (worst == null) {
            worst = hand.getHand().get(hand.numCards() - 1);
        }

        hand.removeCard(hand.getHand().indexOf(worst));
        discard.push(worst);
        System.out.printf("%s descarta: %s%n", name, worst);
    }
}