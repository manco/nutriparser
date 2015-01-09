package nutriparser.parser;

import com.google.common.annotations.VisibleForTesting;
import nutriparser.dto.ProductDto;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Stream;

import static com.codepoetics.protonpack.StreamUtils.takeUntil;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@Component
public class XlsxProductDao {

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
		return
			takeUntil(productRowStream(productsSheet), ProductRow::isEndRow)
			.filter(ProductRow::isValid)
			.map(rowProcessor::processRow)
			.collect(toList())
			;
	}

	private static Stream<ProductRow> productRowStream(Iterable<Row> rows) {
		return stream(rows.spliterator(), false).map(ProductRow::new);
	}

	@VisibleForTesting static Workbook createWorkbook(File file) {
		try {
			return WorkbookFactory.create(file);
		} catch (final IOException | InvalidFormatException e) {
			throw new RuntimeException(e);
		}
	}
}
