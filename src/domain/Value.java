package domain;

/**
 * Representa los valores posibles de las cartas de la baraja española.
 * Incluye los valores del 1 al 7 y las figuras: Sota (10), Caballo (11) y Rey (12).
 * Cada valor tiene un nombre en español y su valor numérico para el cálculo de puntos.
 */
public enum Value {

    /** As, valor 1. */
    ONE("Uno", 1),

    /** Dos, valor 2. */
    TWO("Dos", 2),

    /** Tres, valor 3. */
    THREE("Tres", 3),

    /** Cuatro, valor 4. */
    FOUR("Cuatro", 4),

    /** Cinco, valor 5. */
    FIVE("Cinco", 5),

    /** Seis, valor 6. */
    SIX("Seis", 6),

    /** Siete, valor 7. */
    SEVEN("Siete", 7),

    /** Sota, valor 10. */
    TEN("Sota", 10),

    /** Caballo, valor 11. */
    ELEVEN("Caballo", 11),

    /** Rey, valor 12. */
    TWELVE("Rey", 12);

    /** Nombre en español de la carta. */
    private final String str;

    /** Valor numérico de la carta, usado para el cálculo de puntos. */
    private final int value;

    /**
     * Constructor del enum.
     *
     * @param str   nombre en español de la carta
     * @param value valor numérico de la carta
     */
    Value(String str, int value) {
        this.str = str;
        this.value = value;
    }

    /**
     * Devuelve el nombre en español de la carta.
     *
     * @return nombre de la carta
     */
    public String getStr() {
        return str;
    }

    /**
     * Devuelve el valor numérico de la carta.
     * 
     *
     * @return valor numérico de la carta
     */
    public int getValue() {
        return value;
    }
}