package nutriparser;

import com.google.common.annotations.VisibleForTesting;
import nutriparser.domain.ProductDto;
import nutriparser.util.CellUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

@Component
public class XlsxProductDao {

	private static final int CELL_END_PRODUCTS = -1;
	@VisibleForTesting static final String PRODUKTY_SHEET_NAME = "PRODUKTY";

	private final XlsxRowProcessor rowProcessor;

	@Autowired
	public XlsxProductDao(XlsxRowProcessor rowProcessor) {
		this.rowProcessor = rowProcessor;
	}

	public Collection<ProductDto> extractProducts(final File file) {
		final Workbook workbook = createWorkbook(file);
		final Sheet productsSheet = workbook.getSheet(PRODUKTY_SHEET_NAME);
		return extractProducts(productsSheet);
	}

	private Collection<ProductDto> extractProducts(final Iterable<Row> productsSheet) {
		final Collection<ProductDto> products = new LinkedList<>();
		for (final Row row: productsSheet) {
			if (isEndRow(row)) {
				return products;
			}
			if (rowProcessor.isRowValid(row)) {
				final ProductDto p = rowProcessor.processRow(row);
				products.add(p);
			}
		}
		return products;
	}

	private static boolean isEndRow(final Row row) {
		final Cell cell = CellUtils.getCell(row, InputXlsxMetaData.CELL_INDEX_PRODUCT_NUMBER);
		return CellUtils.isCellNumeric(cell) && Math.abs(cell.getNumericCellValue() - CELL_END_PRODUCTS) < 0.1;
	}

	@VisibleForTesting static Workbook createWorkbook(File file) {
		try {
			return WorkbookFactory.create(file);
		} catch (final IOException | InvalidFormatException e) {
			throw new RuntimeException(e);
		}
	}
}
