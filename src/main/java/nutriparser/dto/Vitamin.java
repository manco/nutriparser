package nutriparser.dto;

import java.util.Arrays;

public enum Vitamin {
	A("Retinol"),
	D,
	E("Tokoferol"),
	K,
	B1("Tiamina"),
	B2("Ryboflawina"),
	B3("Niacyna", "PP"),
	B6("Pirydoksyna"),
	B9("Kwas foliowy", "Folacyna"),
	B12,
	C("Kwas askorbinowy"),
	BETAKAROTEN
	;
	private final String[] names;
	Vitamin(String... names) {
		this.names = names;
	}
	@Override
	public String toString() {
		return String.format("%s - %s", name(),Arrays.toString(names));
	}
}
