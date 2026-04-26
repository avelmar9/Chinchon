package dominio;

public enum Value {
	UNO("Uno", 1), DOS("Dos", 2), TRES("Tres", 3), CUATRO("Cuatro", 4), CINCO("Cinco", 5), SEIS("Seis", 6),
	SIETE("Siete", 7), DIEZ("Sota", 10), ONCE("Caballo", 11), DOCE("Rey", 12);

	private final String str;
	private final int value;

	Value(String str, int value) {
		this.str = str;
		this.value = value;
	}

	public String getStr() {
		return str;
	}

	public int getValue() {
		return value;
	}

}
