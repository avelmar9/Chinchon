package dominio;

import java.util.ArrayList;
import java.util.List;

public class HumanPlayer extends Player {

	public HumanPlayer(String name) {
		super(name);
	}

	

	public boolean takingPhase(Deck deck, DiscardDeck discard, int roundNumber) {
		ConsoleInput input = ConsoleInput.getInstance();
		int option;
		Card taken;

		hand.showCards();

		if (new CombinationValidator().isChinchon(hand.getHand(), roundNumber)) {
			 
				System.out.println("¡CHINCHÓN! Ganas la partida.");
				lastCombination = new ArrayList<>();
				lastCombination.add(new ArrayList<>(hand.getHand()));
				return true;
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

	public boolean closingPhase(DiscardDeck discard, int roundNumber) {
		ConsoleInput input = ConsoleInput.getInstance();
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

	private void discardCard(DiscardDeck discard) {
		ConsoleInput input = ConsoleInput.getInstance();
		Card discarded;
		int index;

		System.out.println("Elige la carta a descartar:");
		index = input.readIntInRange(1, hand.numCards());
		discarded = hand.removeCard(index - 1);
		discard.push(discarded);

		System.out.printf("Has descartado: %s%n", discarded);
	}

	@Override
	public List<List<Card>> chooseCombination(int roundNumber, DiscardDeck discard) {
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

		if (!new CombinationValidator().isValidCombination(groups)) {
			restoreHand(handBackup);
			return null;
		}

		lastCombination = groups;
		return groups;
	}

	private boolean isValidClose(List<Card> combined, List<Card> loose, DiscardDeck discard) {
		ConsoleInput input = ConsoleInput.getInstance();
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
			discard.push(discarded);
			System.out.printf("Carta descartada: %s%n", discarded);
			return true;
		}

		System.out.println("Debes combinar 6 o 7 cartas para cerrar.");
		return false;
	}

	private List<List<Card>> selectGroups() {
		ConsoleInput input = ConsoleInput.getInstance();
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
						System.out.printf("Índice no válido, elige entre 1 y %d", hand.numCards());
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

	private List<Card> createHandBackup() {
		return new ArrayList<>(hand.getHand());
	}

	private void restoreHand(List<Card> backup) {
		hand.getHand().clear();
		hand.getHand().addAll(backup);
	}
}