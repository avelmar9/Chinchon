package dominio;

import java.util.Comparator;
import java.util.List;

/**
 * Valida las combinaciones de cartas del juego del Chinchón.
 * Una combinación válida es un grupo de cartas que forma una de las siguientes jugadas:
 * <ul>
 *   <li><b>Iguales:</b> mínimo 3 cartas del mismo valor.</li>
 *   <li><b>Escalera:</b> mínimo 3 cartas consecutivas del mismo palo.</li>
 *   <li><b>Chinchón:</b> 7 cartas consecutivas del mismo palo.</li>
 * </ul>
 */
public class CombinationValidator {

    /**
     * Comprueba si una lista de grupos de cartas forma combinaciones válidas.
     * Cada grupo debe tener al menos 3 cartas y ser una escalera o un grupo de iguales.
     *
     * @param selectedCards lista de grupos de cartas a validar
     * @return {@code true} si todos los grupos son válidos, {@code false} en caso contrario
     */
    public boolean isValidCombination(List<List<Card>> selectedCards) {
        if (selectedCards == null || selectedCards.isEmpty()) {
            System.out.println("La combinación no puede estar vacía");
            return false;
        }

        for (List<Card> group : selectedCards) {
            if (group.size() < 3 || (!areEquals(group) && !isStraight(group))) {
                System.out.println("Combinaciones no válidas");
                return false;
            }
        }
        return true;
    }

    /**
     * Comprueba si todas las cartas del grupo tienen el mismo valor.
     *
     * @param cards grupo de cartas a comprobar
     * @return {@code true} si todas las cartas tienen el mismo valor
     */
    private boolean areEquals(List<Card> cards) {
        Value firstNumber = cards.get(0).getNumber();
        for (Card c : cards) {
            if (c.getNumber() != firstNumber)
                return false;
        }
        return true;
    }

    /**
     * Comprueba si las cartas del grupo forman una escalera válida.
     * Una escalera requiere que todas las cartas sean del mismo palo
     * y tengan valores consecutivos. Se permite el salto del 7 al 10
     * propio de la baraja española.
     *
     * @param cards grupo de cartas a comprobar
     * @return {@code true} si las cartas forman una escalera válida
     */
    private boolean isStraight(List<Card> cards) {
        Suit firstSuit = cards.get(0).getSuit();
        boolean normalStep, jumpStep;

        for (Card c : cards) {
            if (c.getSuit() != firstSuit) {
                return false;
            }
        }

        cards.sort(Comparator.comparing(Card::getNumber));

        for (int i = 0; i < cards.size() - 1; i++) {
            Value current = cards.get(i).getNumber();
            Value next = cards.get(i + 1).getNumber();

            normalStep = next.getValue() == current.getValue() + 1;
            jumpStep = current.getValue() == 7 && next.getValue() == 10;

            if (!normalStep && !jumpStep) {
                return false;
            }
        }
        return true;
    }

    /**
     * Comprueba si las 7 cartas forman un chinchón, es decir,
     * una escalera completa de 7 cartas consecutivas del mismo palo.
     *
     * @param cards lista de 7 cartas a comprobar
     * @return {@code true} si las cartas forman un chinchón
     */
    public boolean isChinchon(List<Card> cards) {
        if (cards.size() != 7) return false;
        return isStraight(cards);
    }
}
