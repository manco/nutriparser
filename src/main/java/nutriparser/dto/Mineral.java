package nutriparser.dto;

import java.util.Arrays;

public enum Mineral {
	Na("Sód"),
	K("Potas"),
	Ca("Wapń"),
	P("Fosfor"),
	Mg("Magnez"),
	Fe("Żelazo"),
	Zn("Cynk"),
	Cu("Miedź"),
	Mn("Mangan")
	;
	private final String[] names;
	Mineral(String... names) {
		this.names = names;
	}
	@Override
	public String toString() {
		return String.format("%s - %s", name(),Arrays.toString(names));
	}
}
