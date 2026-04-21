package dominio;

import java.util.Scanner;

public class ConsoleInput {

	private Scanner keyboard;
	private static ConsoleInput instance;

	private ConsoleInput() {
		this.keyboard = new Scanner(System.in);
	}
	 public static ConsoleInput getInstance() {
	        if (instance == null) {
	            instance = new ConsoleInput();
	        }
	        return instance;
	    }
	/* Limpia el buffer del teclado */
	@SuppressWarnings("unused")
	private void cleanInput() {
		if (keyboard.hasNextLine()) {
			keyboard.nextLine();
		}
	}

	public int readInt() {
		int value = 0;
		String input;
		boolean valid;
		do {
			try {
				input = keyboard.nextLine();
				value = Integer.parseInt(input.trim());
				valid = true;
			} catch (NumberFormatException e) {
				System.out.println("Valor no válido. Introduce un número entero:");
				valid = false;
			}

		} while (!valid);
		return value;
	}

	public int readIntLessThan(int upperBound) {
		int value;
		boolean valid;
		do {
			value = readInt();
			if (value < upperBound) {
				valid = true;

			} else {

				System.out.printf("El valor debe ser menor que %d%n", upperBound);
				valid = false;
			}
		} while (!valid);
		return value;
	}

	public int readIntLessOrEqualThan(int upperBound) {
		int value;
		boolean valid;
		do {
			value = readInt();
			if (value <= upperBound) {
				valid = true;

			} else {
				System.out.printf("El valor debe ser menor o igual que %d%n", upperBound);
				valid = false;
			}
		} while (!valid);
		return value;
	}

	public int readIntGreaterThan(int lowerBound) {
		int value;
		boolean valid;
		do {
			value = readInt();
			if (value > lowerBound) {
				valid = true;

			} else {

				System.out.printf("El valor debe ser mayor que %d%n", lowerBound);
				valid = false;
			}
		} while (!valid);
		return value;
	}

	public int readIntGreaterOrEqualThan(int lowerBound) {
		int value;
		boolean valid;
		do {
			value = readInt();
			if (value >= lowerBound) {
				valid = true;

			} else {

				System.out.printf("El valor debe ser mayor o igual que %d%n", lowerBound);
				valid = false;
			}
		} while (!valid);
		return value;
	}

	public int readIntInRange(int lowerBound, int upperBound) {
		int value;
		boolean valid;

		do {
			value = readInt();
			if (value >= lowerBound && value <= upperBound) {
				valid = true;
			} else {
				System.out.printf("El valor debe estar entre %d y %d%n", lowerBound, upperBound);
				valid = false;
			}
		} while (!valid);

		return value;
	}

	public double readDouble() {
		double value = 0;
		String input = "";
		boolean valid;
		do {
			try {
				input = keyboard.nextLine();
				value = Double.parseDouble(input.trim());
				valid = true;
			} catch (NumberFormatException e) {
				System.out.println("Valor no válido. Introduce un número decimal:");
				valid = false;
			}
		} while (!valid);

		return value;
	}

	public double readDoubleLessThan(double upperBound) {
		double value;
		boolean valid;
		do {
			value = readDouble();
			if (value < upperBound) {
				valid = true;
			} else {
				System.out.printf("El valor debe ser menor que %.2f%n", upperBound);
				valid = false;
			}
		} while (!valid);
		return value;
	}

	public double readDoubleLessOrEqualThan(double upperBound) {
		double value;
		boolean valid;
		do {
			value = readDouble();
			if (value <= upperBound) {
				valid = true;
			} else {
				System.out.printf("El valor debe ser menor o igual que %.2f%n", upperBound);
				valid = false;
			}
		} while (!valid);
		return value;
	}

	public double readDoubleGreaterThan(double lowerBound) {
		double value;
		boolean valid;
		do {
			value = readDouble();
			if (value > lowerBound) {
				valid = true;
			} else {
				System.out.printf("El valor debe ser mayor que %.2f%n", lowerBound);
				valid = false;
			}
		} while (!valid);
		return value;
	}

	public double readDoubleGreaterOrEqualThan(double lowerBound) {
		double value;
		boolean valid;

		do {
			value = readDouble();
			if (value >= lowerBound) {
				valid = true;
			} else {
				System.out.printf("El valor debe ser mayor o igual que %.2f%n", lowerBound);
				valid = false;
			}
		} while (!valid);
		return value;
	}

	public double readDoubleInRange(double lowerBound, double upperBound) {
		double value;
		boolean valid;
		do {
			value = readDouble();
			if (value >= lowerBound && value <= upperBound) {
				valid = true;
			} else {
				System.out.printf("El valor debe estar entre %.2f y %.2f%n", lowerBound, upperBound);
				valid = false;
			}
		} while (!valid);
		return value;
	}

	public char readChar() {
		char value = ' ';
		String input;
		boolean valid;

		do {
			input = keyboard.nextLine();
			if (input.length() == 1) {
				value = input.charAt(0);
				valid = true;
			} else {
				System.out.println("Introduce solo un carácter:");
				valid = false;
			}
		} while (!valid);
		return value;
	}

	public String readString() {
		return keyboard.nextLine();
	}

	public String readString(int maxLength) {

		String input;
		boolean valid;

		do {
			input = keyboard.nextLine();
			if (input.length() <= maxLength) {
				valid = true;
			} else {
				System.out.printf("La cadena debe tener como máximo %d caracteres%n", maxLength);
				valid = false;
			}
		} while (!valid);
		return input;
	}

	public boolean readBooleanUsingChar(char affirmativeValue, char negativeValue) {
		char input;
		boolean valid;
		boolean value = false;

		do {
			input = Character.toLowerCase(readChar());
			if (input == Character.toLowerCase(affirmativeValue)) {
				value = true;
				valid = true;
			} else if (input == Character.toLowerCase(negativeValue)) {
				value = false;
				valid = true;
			} else {
				System.out.printf("Introduce '%c' o '%c'%n", affirmativeValue, negativeValue);
				valid = false;
			}
		} while (!valid);

		return value;
	}
}
