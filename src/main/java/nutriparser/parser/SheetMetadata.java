package nutriparser.parser;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import nutriparser.dto.Mineral;
import nutriparser.dto.Vitamin;

import java.util.List;
import java.util.Map;

import static nutriparser.dto.Mineral.*;
import static nutriparser.dto.Vitamin.*;

public final class SheetMetadata {

	private static final int VITAMINES_OFFSET = 31;
	private static final int MINERALS_OFFSET = 22;

	public static final List<Vitamin> VITAMINES = ImmutableList.of(BETAKAROTEN, A, D, E, B1, B2, B3, B6, B12, B9, C);
	public static final List<Mineral> MINERALS = ImmutableList.of(Na, Mineral.K, Ca, P, Mg, Fe, Zn, Cu, Mn);

	public static final Map<Vitamin, Integer> CELLS_INDEXES_VITAMINS = mapFromIndexes(VITAMINES, VITAMINES_OFFSET);
	public static final Map<Mineral, Integer> CELLS_INDEXES_MINERALS = mapFromIndexes(MINERALS, MINERALS_OFFSET);
	static final String PRODUKTY_SHEET_NAME = "PRODUKTY";

	private SheetMetadata() {
	}

	private static <T> Map<T, Integer> mapFromIndexes(List<T> items, int offset) {
		return Maps.toMap(items, item -> items.indexOf(item) + offset);
	}
}
