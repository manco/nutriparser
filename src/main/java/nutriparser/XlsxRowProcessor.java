package nutriparser;

import nutriparser.domain.Mineral;
import nutriparser.domain.ProductDto;
import nutriparser.domain.Vitamin;
import nutriparser.util.CellUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static nutriparser.InputXlsxMetaData.*;

public class XlsxRowProcessor {
	
	public ProductDto processRow(final Row row) {
		final Cell nameCell = row.getCell(CELL_INDEX_NAME, Row.RETURN_BLANK_AS_NULL);
		final Map<Vitamin, Double> vitamines = toMap(getAllVitamins(), valueFromCell(row, CELLS_INDEXES_VITAMINS));
		final Map<Mineral, Double> minerals = toMap(getAllMinerals(), valueFromCell(row, CELLS_INDEXES_MINERALS));
		return new ProductDto(nameCell.getStringCellValue(), vitamines, minerals);
	}

	private static <K, V> Map<K, V> toMap(Collection<K> keys, Function<K, V> fun) {
		return keys.stream().collect(Collectors.toMap(Function.identity(), fun));
	}

	private static <T> Function<T, Double> valueFromCell(final Row row, final Map<? extends T, Integer> indexesMap) {
		return (T element) -> row.getCell(indexesMap.get(element), Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
	}

	public boolean isRowValid(Row row) {
		final Cell numberCell = row.getCell(CELL_INDEX_PRODUCT_NUMBER);
		return CellUtils.isCellNumeric(numberCell) && CellUtils.isNotEmpty(row, CELL_INDEX_NAME);
	}

}
