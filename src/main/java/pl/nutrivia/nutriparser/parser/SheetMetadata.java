package pl.nutrivia.nutriparser.parser;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import pl.nutrivia.nutriparser.dto.Mineral;
import pl.nutrivia.nutriparser.dto.Vitamin;

import java.util.List;
import java.util.Map;

import static pl.nutrivia.nutriparser.dto.Mineral.*;
import static pl.nutrivia.nutriparser.dto.Vitamin.*;

public final class SheetMetadata {

	private static final int VITAMINES_OFFSET = 31;
	private static final int MINERALS_OFFSET = 22;

	static final List<Vitamin> VITAMINES = ImmutableList.of(BETAKAROTEN, A, D, E, B1, B2, B3, B6, B12, B9, C);
	static final List<Mineral> MINERALS = ImmutableList.of(Na, Mineral.K, Ca, P, Mg, Fe, Zn, Cu, Mn);

	static final Map<Vitamin, Integer> CELLS_INDEXES_VITAMINS = mapFromIndexes(VITAMINES, VITAMINES_OFFSET);
	static final Map<Mineral, Integer> CELLS_INDEXES_MINERALS = mapFromIndexes(MINERALS, MINERALS_OFFSET);

	static final String PRODUKTY_SHEET_NAME = "PRODUKTY";
	static final String CELL_END_PRODUCTS = "1700";

	private SheetMetadata() {
	}

	private static <T> Map<T, Integer> mapFromIndexes(List<T> items, int offset) {
		return Maps.toMap(items, item -> items.indexOf(item) + offset);
	}
}
