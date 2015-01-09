package nutriparser.parser;

import com.google.common.collect.Maps;
import nutriparser.dto.Mineral;
import nutriparser.dto.Vitamin;
import nutriparser.util.CellUtils;
import nutriparser.util.MapsUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static nutriparser.parser.SheetMetadata.*;

public class ProductRow {

    private static final int CELL_INDEX_NAME = 3;
    private static final int CELL_INDEX_PRODUCT_NUMBER = 2;
    private static final double CELL_END_PRODUCTS = -1.0;

    private final Optional<Cell> nameCell;
    private final Optional<Cell> numberCell;
    private final Map<Vitamin, Optional<Cell>> vitaminesCells;
    private final Map<Mineral, Optional<Cell>> mineralsCells;

    public ProductRow(final Row row) {
        numberCell = CellUtils.getCellMaybe(row, CELL_INDEX_PRODUCT_NUMBER);
        nameCell = CellUtils.getCellMaybe(row, CELL_INDEX_NAME);
        vitaminesCells = MapsUtils.toMap(VITAMINES, cellsFromIndexes(row, CELLS_INDEXES_VITAMINS));
        mineralsCells = MapsUtils.toMap(MINERALS, cellsFromIndexes(row, CELLS_INDEXES_MINERALS));
    }

    public String getName() {
        return CellUtils.getStringValueOrNull(nameCell);
    }

    public Map<Vitamin, Double> getVitaminesValues() {
        return valuesFromCells(vitaminesCells);
    }

    public Map<Mineral, Double> getMineralsValues() {
        return valuesFromCells(mineralsCells);
    }

    public boolean isValid() {
        return isNumberCellValid() && nameCell.isPresent();
    }

    public boolean isEndRow() {
        return isNumberCellValid() && Math.abs(CellUtils.getNumericValueOrZero(numberCell) - CELL_END_PRODUCTS) < 0.1;
    }

    private boolean isNumberCellValid() {
        return numberCell.map(CellUtils::isNumeric).orElse(false);
    }

    private static <T> Function<T, Optional<Cell>> cellsFromIndexes(final Row row, final Map<T, Integer> indexesMap) {
        return element -> CellUtils.getCellMaybe(row, indexesMap.get(element));
    }

    private static <T> Map<T, Double> valuesFromCells(Map<T, Optional<Cell>> cells) {
        return Maps.transformValues(cells, CellUtils::getNumericValueOrZero);
    }

}
