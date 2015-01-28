package pl.nutrivia.nutriparser.parser;

import com.google.common.collect.Maps;
import org.apache.poi.ss.usermodel.Row;
import pl.nutrivia.nutriparser.api.ProductDto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.nutrivia.nutriparser.parser.ProductSheet.*;

public class ProductRow implements ProductDto {

    private final ProductCell nameCell;
    private final ProductCell numberCell;
    private final Row row;
    private final Map<String, ProductCell> vitaminesCells;
    private final Map<String, ProductCell> mineralsCells;

    public ProductRow(final Row row, final Collection<NameWithIndex> vitaminNames, Collection<NameWithIndex> mineralsNames) {
        nameCell = getProductCell(row, CELL_INDEX_NAME);
        numberCell = getProductCell(row, CELL_INDEX_PRODUCT_NUMBER);
        this.row = row;

        vitaminesCells = nameWithCellFrom(row, vitaminNames);
        mineralsCells =  nameWithCellFrom(row, mineralsNames);
    }

    private static Map<String, ProductCell> nameWithCellFrom(Row row, Collection<NameWithIndex> names) {
        return names.stream().collect(
                Collectors.toMap(
                    NameWithIndex::getName,
                    nameWithIndex -> getProductCell(row, nameWithIndex.getIndex())
                )
        );
    }

    @Override
    public String getName() {
        return nameCell.getStringValueOrEmpty();
    }

    @Override
    public BigDecimal getProtein() {
        return getCellNumberValue(CELL_INDEX_PROTEIN);
    }

    @Override
    public BigDecimal getFat() {
        return getCellNumberValue(CELL_INDEX_FAT);
    }

    @Override
    public BigDecimal getCarbo() {
        return getCellNumberValue(CELL_INDEX_CARBO);
    }

    @Override
    public Optional<BigDecimal> getFatSaturated() {
        return getCellNumberValueMaybe(CELL_INDEX_FAT_SATURATED);
    }

    @Override
    public Optional<BigDecimal> getFatMonoUnsaturated() {
        return getCellNumberValueMaybe(CELL_INDEX_FAT_MONO_UNSATURATED);
    }

    @Override
    public Optional<BigDecimal> getFatPolyUnsaturated() {
        return getCellNumberValueMaybe(CELL_INDEX_FAT_POLY_UNSATURATED);
    }

    @Override
    public Optional<BigDecimal> getCholesterol() {
        return getCellNumberValueMaybe(CELL_INDEX_CHOLESTEROL);
    }

    @Override
    public Optional<BigDecimal> getFiber() {
        return getCellNumberValueMaybe(CELL_INDEX_FIBER);
    }

    @Override
    public Map<String, BigDecimal> getVitamines() {
        return valuesFromCells(vitaminesCells);
    }

    @Override
    public Map<String, BigDecimal> getMinerals() {
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

    private static Map<String, BigDecimal> valuesFromCells(Map<String, ProductCell> cells) {
        return Maps.transformValues(cells, ProductCell::getNumericValueOrZero);
    }

    private Optional<BigDecimal> getCellNumberValueMaybe(int index) {
        return Optional.ofNullable(getCellNumberValue(index));
    }

    private BigDecimal getCellNumberValue(int index) {
        return getProductCell(row, index).getNumericValueOrZero();
    }

    private static ProductCell getProductCell(final Row row, int index) {
        return new ProductCell(Optional.ofNullable(row.getCell(index, Row.RETURN_BLANK_AS_NULL)));
    }

}
