package pl.nutrivia.nutriparser.parser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class ProductRowTest {

    @Mock private Row row;
    @Mock private Cell nameCell;
    @Mock private Cell numberCell;

    @Test
    public void shouldIsValid() {

        //given
        given(numberCell.getCellType()).willReturn(Cell.CELL_TYPE_NUMERIC);
        given(row.getCell(eq(ProductSheet.CELL_INDEX_NAME), any(Row.MissingCellPolicy.class))).willReturn(nameCell);
        given(row.getCell(eq(ProductSheet.CELL_INDEX_PRODUCT_NUMBER), any(Row.MissingCellPolicy.class))).willReturn(numberCell);

        //when
        final boolean result = new ProductRow(row, Collections.emptyList(), Collections.emptyList()).isValid();

        //then
        assertThat(result).isTrue();

    }
}