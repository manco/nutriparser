package nutriparser.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.util.Assert;

public final class CellUtils {
	private CellUtils() {
	}

	static boolean isCellNumeric(final Cell cell) {
		return cell != null && Cell.CELL_TYPE_NUMERIC == cell.getCellType();
	}

	static boolean isNotEmpty(final Row row, final int index) {
		return row.getCell(index, Row.RETURN_BLANK_AS_NULL) != null;
	}

	static Cell getCell(final Row row, int index) {
		return row.getCell(index, Row.RETURN_BLANK_AS_NULL);
	}


}
