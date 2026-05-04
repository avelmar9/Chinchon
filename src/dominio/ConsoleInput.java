package dominio;

import java.util.Scanner;

/**
 * Gestiona la entrada de datos del usuario por consola.
 * Implementa el patrón Singleton para garantizar una única instancia
 * y un único {@link Scanner} activo durante toda la ejecución.
 * <p>
 * Proporciona métodos para leer enteros, decimales, caracteres, cadenas
 * y booleanos con validación automática y mensajes de error al usuario.
 * </p>
 */
public class ConsoleInput {

    /** Scanner asociado a la entrada estándar del sistema. */
    private Scanner keyboard;

    /** Única instancia de la clase (patrón Singleton). */
    private static ConsoleInput instance;

    /**
     * Constructor privado. Inicializa el scanner con {@code System.in}.
     */
    private ConsoleInput() {
        this.keyboard = new Scanner(System.in);
    }

    /**
     * Devuelve la única instancia de {@code ConsoleInput}.
     * La crea si aún no existe (lazy initialization).
     *
     * @return instancia única de {@code ConsoleInput}
     */
    public static ConsoleInput getInstance() {
        if (instance == null) {
            instance = new ConsoleInput();
        }
        return instance;
    }

    /**
     * Limpia el buffer del teclado consumiendo la línea pendiente.
     */
    
    public void cleanInput() {
        if (keyboard.hasNextLine()) {
            keyboard.nextLine();
        }
    }

    /**
     * Lee un número entero de la consola.
     * Repite la lectura hasta que el usuario introduce un entero válido.
     *
     * @return entero introducido por el usuario
     */
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

    /**
     * Lee un entero estrictamente mayor que el límite inferior indicado.
     *
     * @param lowerBound límite inferior (exclusivo)
     * @return entero mayor que {@code lowerBound}
     */
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

    /**
     * Lee un entero dentro del rango [{@code lowerBound}, {@code upperBound}] (ambos inclusivos).
     *
     * @param lowerBound límite inferior (inclusivo)
     * @param upperBound límite superior (inclusivo)
     * @return entero en el rango indicado
     */
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

    
    /**
     * Lee un único carácter de la consola.
     * Repite la lectura si el usuario introduce más de un carácter.
     *
     * @return carácter introducido por el usuario
     */
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

    /**
     * Lee una cadena de texto de la consola sin restricción de longitud.
     *
     * @return cadena introducida por el usuario
     */
    public String readString() {
        return keyboard.nextLine();
    }

    

    /**
     * Lee un booleano representado por dos caracteres: uno afirmativo y otro negativo.
     * No distingue entre mayúsculas y minúsculas.
     *
     * @param affirmativeValue carácter que representa {@code true}
     * @param negativeValue    carácter que representa {@code false}
     * @return {@code true} si el usuario introduce el carácter afirmativo,
     *         {@code false} si introduce el negativo
     */
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

	public void close() {
		if(keyboard != null) {
			keyboard.close();
		}
		
	}
}
