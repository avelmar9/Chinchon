package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un jugador humano en el Chinchón.
 * Interactúa con el usuario a través de {@link ConsoleInput} para tomar decisiones
 * en cada fase del turno: robar, descartar o intentar cerrar con combinaciones.
 */
public class HumanPlayer extends Player {
	
	private final ConsoleInput input;
    /**
     * Construye un jugador humano con el nombre indicado.
     *
     * @param name nombre del jugador 
     * @param input gestor de entrada por consola
     */
    public HumanPlayer(String name, ConsoleInput input) {
		super(name);
        this.input = input;

    }

    /**
     * Fase de robo del turno humano.
     * Muestra la mano y comprueba si hay chinchón antes de robar.
     * Si no hay chinchón, pregunta al jugador de dónde quiere robar.
     *
     * @param deck        mazo principal
     * @param discard     montón de descarte
     * @param roundNumber número de ronda actual
     * @return {@code true} si el jugador tiene chinchón válido
     */
    @Override
    protected boolean takingPhase(Deck deck, DiscardDeck discard, int roundNumber) {
       
        int option;
        Card taken;

        hand.showCards();

        if (validator.isChinchon(hand.getHand())) {
            if (roundNumber == 1) {
                System.out.println("Tienes chinchón pero no puedes usarlo en la primera ronda.");
            } else {
                System.out.println("¡CHINCHÓN! Ganas la partida.");
                lastCombination = new ArrayList<>();
                lastCombination.add(new ArrayList<>(hand.getHand()));
                chinchon = true;
                return true;
            }
        }

        System.out.printf("Carta visible del descarte: %s%n", discard.getTopCardStr());
        System.out.println("¿De dónde robas?");
        System.out.println("1. Mazo");
        System.out.println("2. Descarte");

        option = input.readIntInRange(1, 2);
        taken = (option == 1) ? deck.takeCard() : discard.pop();

        System.out.printf("Has robado: %s%n", taken);
        hand.addCard(taken);

        return false;
    }

    /**
     * Fase de cierre o descarte del turno humano.
     * Muestra las opciones disponibles y gestiona la decisión del jugador.
     * Si el jugador intenta cerrar con una combinación inválida, vuelve a mostrar el menú.
     *
     * @param discard     montón de descarte
     * @param roundNumber número de ronda actual
     * @return {@code true} si el jugador cierra la ronda correctamente
     */
    @Override
    protected boolean closingPhase(DiscardDeck discard, int roundNumber) {
       
        List<List<Card>> groups;
        int option;

        do {
            hand.showCards();
            System.out.println("¿Qué quieres hacer?");
            System.out.println("1. Descartar una carta");
            System.out.println("2. Intentar cerrar");

            option = input.readIntInRange(1, 2);

            if (option == 1) {
                discardCard(discard);
                return false;
            }

            groups = chooseCombination(roundNumber, discard);

            if (groups == null) {
                System.out.println("Combinación no válida, elige otra opción.");
            }

        } while (groups == null);

        return true;
    }

    /**
     * Pide al jugador que elija una carta de su mano para descartar.
     *
     * @param discard montón de descarte donde se coloca la carta
     */
    private void discardCard(DiscardDeck discard) {
        
        Card discarded;
        int index;

        System.out.println("Elige la carta a descartar:");
        index = input.readIntInRange(1, hand.numCards());
        discarded = hand.removeCard(index - 1);
        discard.push(discarded);

        System.out.printf("Has descartado: %s%n", discarded);
    }

    /**
     * Permite al jugador seleccionar grupos de combinación para intentar cerrar.
     * Realiza una copia de seguridad de la mano antes de extraer cartas
     * y la restaura si la combinación no es válida.
     *
     * @param roundNumber número de ronda actual
     * @param discard     montón de descarte, usado para la carta suelta al cerrar
     * @return lista de grupos combinados válidos, o {@code null} si el cierre no es válido
     */
    @Override
    protected List<List<Card>> chooseCombination(int roundNumber, DiscardDeck discard) {
        List<Card> handBackup;
        List<List<Card>> groups;
        List<Card> combinedCards = new ArrayList<>();
        List<Card> looseCards;

        handBackup = createHandBackup();
        groups = selectGroups();

        for (List<Card> group : groups) {
            combinedCards.addAll(group);
        }

        looseCards = new ArrayList<>(handBackup);
        looseCards.removeAll(combinedCards);

        if (!isValidClose(combinedCards, looseCards, discard)) {
            restoreHand(handBackup);
            return null;
        }

        if (!validator.isValidCombination(groups)) {
            restoreHand(handBackup);
            return null;
        }

        lastCombination = groups;
        return groups;
    }

    /**
     * Valida si el jugador puede cerrar según el número de cartas combinadas y sueltas.
     * <ul>
     *   <li>7 combinadas + 1 suelta: cierre perfecto, la suelta se descarta automáticamente.</li>
     *   <li>6 combinadas + 2 sueltas: el jugador elige cuál descartar; la elegida debe valer 1-5.</li>
     * </ul>
     *
     * @param combined lista de cartas combinadas
     * @param loose    lista de cartas no combinadas
     * @param discard  montón de descarte
     * @return {@code true} si el cierre es válido
     */
    private boolean isValidClose(List<Card> combined, List<Card> loose, DiscardDeck discard) {
        
        int option;
        Card discarded;

        if (loose.size() == 1 && combined.size() == 7) {
            discard.push(loose.get(0));
            System.out.printf("Cierre perfecto. Carta descartada: %s%n", loose.get(0));
            return true;
        }

        if (loose.size() == 2 && combined.size() == 6) {
            System.out.printf("Elige cuál descartar:%n");
            System.out.printf("1. %s%n", loose.get(0));
            System.out.printf("2. %s%n", loose.get(1));
            option = input.readIntInRange(1, 2);
            discarded = loose.get(option - 1);

            if (discarded.getNumber().getValue() > 5) {
                System.out.println("No puedes cerrar: la carta de descarte debe ser <= 5.");
                return false;
            }
            discard.push(discarded);
            System.out.printf("Carta descartada: %s%n", discarded);
            return true;
        }

        System.out.println("Debes combinar 6 o 7 cartas para cerrar.");
        return false;
    }

    /**
     * Permite al jugador seleccionar grupos de cartas de su mano de forma interactiva.
     * Muestra la mano actualizada tras cada selección y permite múltiples grupos.
     *
     * @return lista de grupos de cartas seleccionados por el jugador
     */
    private List<List<Card>> selectGroups() {
        
        List<List<Card>> groups = new ArrayList<>();
        boolean newGroup;
        List<Card> group;
        int index;

        do {
            group = new ArrayList<>();

            do {
                hand.showCards();
                System.out.println("Elige las cartas a combinar (0 para terminar grupo):");
                index = input.readInt();

                if (index != 0) {
                    if (index < 1 || index > hand.numCards()) {
                        System.out.printf("Índice no válido, elige entre 1 y %d%n", hand.numCards());
                    } else {
                        group.add(hand.removeCard(index - 1));
                    }
                }

            } while (index != 0);

            groups.add(group);

            hand.showCards();
            System.out.println("¿Quieres combinar otro grupo? (s/n)");
            newGroup = input.readBooleanUsingChar('s', 'n');

        } while (newGroup);

        return groups;
    }

    /**
     * Crea una copia de seguridad de las cartas actuales de la mano.
     *
     * @return lista con las cartas actuales de la mano
     */
    private List<Card> createHandBackup() {
        return new ArrayList<>(hand.getHand());
    }

    /**
     * Restaura la mano del jugador a partir de una copia de seguridad.
     * Se usa cuando una combinación resulta inválida.
     *
     * @param backup lista de cartas con la que restaurar la mano
     */
    private void restoreHand(List<Card> backup) {
        hand.getHand().clear();
        hand.getHand().addAll(backup);
    }
}