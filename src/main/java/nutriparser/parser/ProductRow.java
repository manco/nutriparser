package nutriparser.parser;

import com.google.common.collect.Maps;
import nutriparser.dto.Mineral;
import nutriparser.dto.Vitamin;
import nutriparser.util.MapsUtils;
import org.apache.poi.ss.usermodel.Row;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static nutriparser.parser.SheetMetadata.*;

public class ProductRow {

    private static final int CELL_INDEX_NAME = 3;
    private static final int CELL_INDEX_PRODUCT_NUMBER = 2;

    private final ProductCell nameCell;
    private final ProductCell numberCell;
    private final Map<Vitamin, ProductCell> vitaminesCells;
    private final Map<Mineral, ProductCell> mineralsCells;

    public ProductRow(final Row row) {
        numberCell = getProductCell(row, CELL_INDEX_PRODUCT_NUMBER);
        nameCell = getProductCell(row, CELL_INDEX_NAME);
        vitaminesCells = MapsUtils.toMap(VITAMINES, cellsFromIndexes(row, CELLS_INDEXES_VITAMINS));
        mineralsCells = MapsUtils.toMap(MINERALS, cellsFromIndexes(row, CELLS_INDEXES_MINERALS));
    }

    public String getName() {
        return nameCell.getStringValueOrNull();
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
        return isNumberCellValid() && numberCell.isEndCell();
    }

    private boolean isNumberCellValid() {
        return numberCell.isNumeric();
    }

    private static <T> Function<T, ProductCell> cellsFromIndexes(final Row row, final Map<T, Integer> indexesMap) {
        return element -> getProductCell(row, indexesMap.get(element));
    }

    private static <T> Map<T, Double> valuesFromCells(Map<T, ProductCell> cells) {
        return Maps.transformValues(cells, ProductCell::getNumericValueOrZero);
    }

    private static ProductCell getProductCell(final Row row, int index) {
        return new ProductCell(Optional.ofNullable(row.getCell(index, Row.RETURN_BLANK_AS_NULL)));
    }

}
