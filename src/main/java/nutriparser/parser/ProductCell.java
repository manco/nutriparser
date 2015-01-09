package nutriparser.parser;

import org.apache.poi.ss.usermodel.Cell;

import java.util.Objects;
import java.util.Optional;

public class ProductCell {

    private static final double CELL_END_PRODUCTS = -1.0;

    private final Optional<Cell> cell;

    public ProductCell(Optional<Cell> cell) {
        this.cell = Objects.requireNonNull(cell);
    }

    public String getStringValueOrNull() {
        return cell.map(Cell::getStringCellValue).orElse(null);
    }

    public Double getNumericValueOrZero() {
        return cell.map(Cell::getNumericCellValue).orElse(0.0);
    }

    public boolean isNumeric() {
        return cell.map(ProductCell::isTypeNumeric).orElse(false);
    }

    public boolean isEndCell() {
        return Math.abs(getNumericValueOrZero() - CELL_END_PRODUCTS) < 0.1;
    }

    private static boolean isTypeNumeric(Cell cell) {
        return Cell.CELL_TYPE_NUMERIC == cell.getCellType();
    }

    public boolean isPresent() {
        return cell.isPresent();
    }
}
