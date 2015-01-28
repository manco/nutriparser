package pl.nutrivia.nutriparser.parser;

import org.apache.poi.ss.usermodel.Cell;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ProductCellTest {

    @Mock private Cell cell;

    @Test
    public void shouldGetStringValue() {
        //given
        final String cellValue = "cell string value";
        given(cell.getStringCellValue()).willReturn(cellValue);

        //when
        final String result = new ProductCell(Optional.of(cell)).getStringValueOrEmpty();

        //then
        assertThat(result).isEqualTo(cellValue);
    }

    @Test
    public void shouldGetNumericValue() {
        final double numValue = 999.993;
        given(cell.getNumericCellValue()).willReturn(numValue);

        //when
        final BigDecimal result = new ProductCell(Optional.of(cell)).getNumericValueOrZero();

        //then
        assertThat(result.doubleValue()).isEqualTo(numValue);

    }

    @Test
    public void shouldBeNumeric() {
        //given
        given(cell.getCellType()).willReturn(Cell.CELL_TYPE_NUMERIC);

        //when
        final boolean result = new ProductCell(Optional.of(cell)).isNumeric();

        //then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldBeEndCell() {
        //given
        given(cell.getNumericCellValue()).willReturn((double) SheetMetadata.CELL_END_PRODUCTS);

        //when
        final boolean result = new ProductCell(Optional.of(cell)).isEndCell();

        //then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldHandleMissingCell() {
        //given
        final ProductCell missingCell = new ProductCell(Optional.<Cell>empty());

        //when

        //then
        assertThat(missingCell.isNumeric()).isFalse();
        assertThat(missingCell.isEndCell()).isFalse();
        assertThat(missingCell.isPresent()).isFalse();
        assertThat(missingCell.getNumericValueOrZero()).isEqualTo(BigDecimal.ZERO);
        assertThat(missingCell.getStringValueOrEmpty()).isEmpty();
    }

    @Test
    public void shouldNotBeEndCell() {
        //given
        given(cell.getNumericCellValue()).willReturn(133.0);

        //when
        final boolean result = new ProductCell(Optional.of(cell)).isEndCell();

        //then
        assertThat(result).isFalse();
    }
}