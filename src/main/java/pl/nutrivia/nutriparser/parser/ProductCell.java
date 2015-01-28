package pl.nutrivia.nutriparser.parser;

import org.apache.poi.ss.usermodel.Cell;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class ProductCell {

    private static final BigDecimal CELL_END_PRODUCTS = BigDecimal.valueOf(ProductSheet.CELL_END_PRODUCTS);

    private final Optional<Cell> cell;

    public ProductCell(Optional<Cell> cell) {
        this.cell = Objects.requireNonNull(cell);
    }

    public String getStringValueOrEmpty() {
        return cell.map(Cell::getStringCellValue).orElse("");
    }

    public BigDecimal getNumericValueOrZero() {
        return cell.map(Cell::getNumericCellValue).map(BigDecimal::valueOf).orElse(BigDecimal.ZERO);
    }

    public boolean isNumeric() {
        return cell.map(ProductCell::isTypeNumeric).orElse(false);
    }

    public boolean isEndCell() {
        return CELL_END_PRODUCTS.compareTo(getNumericValueOrZero()) == 0;
    }

    private static boolean isTypeNumeric(Cell cell) {
        return Cell.CELL_TYPE_NUMERIC == cell.getCellType();
    }

    public boolean isPresent() {
        return cell.isPresent();
    }
}
