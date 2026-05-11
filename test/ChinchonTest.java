package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.Card;
import domain.CombinationValidator;
import domain.Suit;
import domain.Value;

/**
 * Clase de pruebas unitarias para {@code CombinationValidator}.
 * 
 * Comprueba el correcto funcionamiento de las validaciones
 * de grupos iguales, escaleras y chinchón.
 */
public class ChinchonTest {

    /**
     * Instancia del validador utilizada en las pruebas.
     */
    private CombinationValidator validator;

    /**
     * Inicializa el validador antes de cada prueba.
     */
    @BeforeEach
    public void setUp() {
        validator = new CombinationValidator();
    }

    /**
     * Comprueba que un grupo de cartas con el mismo valor
     * se considera una combinación válida.
     */
    @Test
    public void validGroupOfEquals() {
        List<Card> group = new ArrayList<>();
        group.add(new Card(Suit.CLUBS, Value.THREE));
        group.add(new Card(Suit.CUPS, Value.THREE));
        group.add(new Card(Suit.GOLD, Value.THREE));

        List<List<Card>> groups = new ArrayList<>();
        groups.add(group);

        assertTrue(validator.isValidCombination(groups));
    }

    /**
     * Comprueba que un grupo con distintos valores
     * no se considera una combinación válida.
     */
    @Test
    public void invalidGroupOfEqualsDifferentValues() {
        List<Card> group = new ArrayList<>();
        group.add(new Card(Suit.CLUBS, Value.THREE));
        group.add(new Card(Suit.CUPS, Value.FOUR));
        group.add(new Card(Suit.GOLD, Value.THREE));

        List<List<Card>> groups = new ArrayList<>();
        groups.add(group);

        assertFalse(validator.isValidCombination(groups));
    }

    /**
     * Comprueba que una escalera válida con salto
     * del 7 al 10 se acepta correctamente.
     */
    @Test
    public void validStraightwithJump() {
        List<Card> group = new ArrayList<>();
        group.add(new Card(Suit.CLUBS, Value.SIX));
        group.add(new Card(Suit.CLUBS, Value.SEVEN));
        group.add(new Card(Suit.CLUBS, Value.TEN));

        List<List<Card>> groups = new ArrayList<>();
        groups.add(group);

        assertTrue(validator.isValidCombination(groups));
    }

    /**
     * Comprueba que una escalera con diferentes palos
     * no se considera válida.
     */
    @Test
    void invalidStraightDifferentSuits() {
        List<Card> group = new ArrayList<>();
        group.add(new Card(Suit.CLUBS, Value.FIVE));
        group.add(new Card(Suit.CUPS, Value.SIX));
        group.add(new Card(Suit.CLUBS, Value.SEVEN));

        List<List<Card>> groups = new ArrayList<>();
        groups.add(group);

        assertFalse(validator.isValidCombination(groups));
    }

    /**
     * Comprueba que una combinación de siete cartas
     * con diferentes palos no forma un chinchón válido.
     */
    @Test
    void invalidChinchonDifferentSuits() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUBS, Value.ONE));
        cards.add(new Card(Suit.CLUBS, Value.TWO));
        cards.add(new Card(Suit.CUPS, Value.THREE));
        cards.add(new Card(Suit.CLUBS, Value.FOUR));
        cards.add(new Card(Suit.CLUBS, Value.FIVE));
        cards.add(new Card(Suit.CLUBS, Value.SIX));
        cards.add(new Card(Suit.CLUBS, Value.SEVEN));

        assertFalse(validator.isChinchon(cards));
    }
}