package pl.nutrivia.nutriparser.parser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class ProductRowTest {

    @Mock private Row row;
    @Mock private Cell nameCell;
    @Mock private Cell numberCell;

    @Mock private Collection<NameWithIndex> vitamines;
    @Mock private Collection<NameWithIndex> minerals;

    @Test
    public void shouldIsValid() {

        //given
        given(numberCell.getCellType()).willReturn(Cell.CELL_TYPE_NUMERIC);
        given(row.getCell(eq(ProductSheet.CELL_INDEX_NAME), any(Row.MissingCellPolicy.class))).willReturn(nameCell);
        given(row.getCell(eq(ProductSheet.CELL_INDEX_PRODUCT_NUMBER), any(Row.MissingCellPolicy.class))).willReturn(numberCell);

        //when
        final boolean result = new ProductRow(row, vitamines, minerals).isValid();

        //then
        assertThat(result).isTrue();

    }
}