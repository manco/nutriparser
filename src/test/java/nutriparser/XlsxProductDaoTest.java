package nutriparser;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.fest.assertions.api.Assertions.assertThat;

public class XlsxProductDaoTest {

    @Test
    public void shouldCreateWorkbookFromXls() throws IOException {
        //given

        //when
        final File file = new File(getClass().getResource("/Posilki-v4.91.xls").getFile());
        final Workbook workbook = XlsxProductDao.createWorkbook(file);

        //then
        assertThat(workbook.getSheet(XlsxProductDao.PRODUKTY_SHEET_NAME)).isNotEmpty();
    }
}