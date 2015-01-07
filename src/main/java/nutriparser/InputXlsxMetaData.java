package nutriparser;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import nutriparser.domain.Mineral;
import nutriparser.domain.Vitamin;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static nutriparser.domain.Mineral.*;
import static nutriparser.domain.Vitamin.*;

public final class InputXlsxMetaData {

	static final int CELL_INDEX_NAME = 3;
	static final int CELL_INDEX_PRODUCT_NUMBER = 2;

	static final Map<Vitamin, Integer> CELLS_INDEXES_VITAMINS = createVitaminCellsMap();
	static final Map<Mineral, Integer> CELLS_INDEXES_MINERALS = createMineralCellsMap();

	private static final int VITAMINS_OFFSET = 31;
	private static final int MINERALS_OFFSET = 22;

	private InputXlsxMetaData() {
	}

	static Collection<Vitamin> getAllVitamins() {
		final Collection<Vitamin> vits = Lists.newArrayList(Vitamin.values());
		vits.remove(Vitamin.K);
		return vits;
	}

	static Collection<Mineral> getAllMinerals() {
		return Arrays.asList(Mineral.values());
	}
	
	private static Map<Vitamin, Integer> createVitaminCellsMap() {
		return mapFromIndexes(Arrays.asList(BETAKAROTEN, A, D, E, B1, B2, B3, B6, B12, B9, C), VITAMINS_OFFSET);
	}
	private static Map<Mineral, Integer> createMineralCellsMap() {
		return mapFromIndexes(Arrays.asList(Na, Mineral.K, Ca, P, Mg, Fe, Zn, Cu, Mn), MINERALS_OFFSET);
	}

	private static <T> Map<T, Integer> mapFromIndexes(List<T> items, int offset) {
		return Maps.toMap(items, item -> items.indexOf(item) + offset);
	}
}
