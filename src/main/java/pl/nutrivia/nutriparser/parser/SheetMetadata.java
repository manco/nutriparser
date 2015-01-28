package pl.nutrivia.nutriparser.parser;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class SheetMetadata {

	static final int CELL_INDEX_NAME = 3;
	static final int CELL_INDEX_PRODUCT_NUMBER = 2;
	static final int CELL_INDEX_PROTEIN = 8;
	static final int CELL_INDEX_FAT = 9;
	static final int CELL_INDEX_CARBO = 10;
	static final int CELL_INDEX_FAT_SATURATED = 12;
	static final int CELL_INDEX_FAT_MONO_UNSATURATED = 13;
	static final int CELL_INDEX_FAT_POLY_UNSATURATED = 14;

	static final int CELL_INDEX_CHOLESTEROL = 15;
	static final int CELL_INDEX_FIBER = 16;
	static final String PRODUKTY_SHEET_NAME = "PRODUKTY";

	private static final int VITAMINES_OFFSET = 31;
	private static final int MINERALS_OFFSET = 22;

	private static final int VITAMINES_EXPECTED_AMOUNT = 10;
	private static final int MINERALS_EXPECTED_AMOUNT = VITAMINES_OFFSET - MINERALS_OFFSET;

	private static final int ROW_INDEX_HEADER = 6;

	@VisibleForTesting static final int CELL_END_PRODUCTS = 1700;

	private final List<NameWithIndex> mineralsNames;
	private final List<NameWithIndex> vitaminesNames;

	public SheetMetadata(Sheet sheet) {
		final Row headerRow = Iterables.get(sheet, ROW_INDEX_HEADER);
		mineralsNames = getNamesFrom(headerRow, mineralsIndexes());
		vitaminesNames = getNamesFrom(headerRow, vitaminesIndexes());

		checkSize(mineralsNames, MINERALS_EXPECTED_AMOUNT);
		checkSize(vitaminesNames, VITAMINES_EXPECTED_AMOUNT);
	}

	private static void checkSize(Collection<?> collection, int expected) {
		final int size = collection.size();
		Preconditions.checkArgument(size == expected, "wrong amount: "+ size);
	}

	private static IntStream mineralsIndexes() {
		return IntStream.range(MINERALS_OFFSET, VITAMINES_OFFSET);
	}

	private static IntStream vitaminesIndexes() {
		return IntStream.range(VITAMINES_OFFSET, VITAMINES_OFFSET + VITAMINES_EXPECTED_AMOUNT);
	}

	public List<NameWithIndex> getMineralsNames() {
		return Collections.unmodifiableList(mineralsNames);
	}

	public List<NameWithIndex> getVitaminesNames() {
		return Collections.unmodifiableList(vitaminesNames);
	}

	private static List<NameWithIndex> getNamesFrom(Row row, IntStream stream) {
		return stream
				.mapToObj(row::getCell)
				.filter(SheetMetadata::hasText)
				.map(SheetMetadata::nameWithIndex)
				.collect(toList());
	}

	private static NameWithIndex nameWithIndex(Cell cell) {
		return new NameWithIndex(cell.getStringCellValue(), cell.getColumnIndex());
	}

	private static boolean hasText(Cell cell) {
		return !cell.getStringCellValue().trim().isEmpty();
	}
}
