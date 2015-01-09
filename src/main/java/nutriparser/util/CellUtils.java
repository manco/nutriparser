package nutriparser.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.util.Assert;

import java.util.Optional;

public final class CellUtils {
	private CellUtils() {
	}

	public static boolean isNumeric(final Cell cell) {
		return Cell.CELL_TYPE_NUMERIC == cell.getCellType();
	}

	public static String getStringValueOrNull(final Optional<Cell> cell) {
		return cell.map(Cell::getStringCellValue).orElse(null);
	}

	public static Double getNumericValueOrZero(final Optional<Cell> cell) {
		return cell.map(Cell::getNumericCellValue).orElse(0.0);
	}

	public static Optional<Cell> getCellMaybe(final Row row, int index) {
		return Optional.ofNullable(row.getCell(index, Row.RETURN_BLANK_AS_NULL));
	}


}
