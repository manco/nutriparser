package pl.nutrivia.nutriparser.parser;

import com.google.common.collect.Iterables;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import pl.nutrivia.nutriparser.dto.ProductDto;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Stream;

import static com.codepoetics.protonpack.StreamUtils.takeUntil;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

@Component
public class WorkbookParser {

	public Collection<ProductDto> extractProducts(final File file) throws IOException, InvalidFormatException {
		try (final Workbook workbook = WorkbookFactory.create(file)) {
			final Sheet productsSheet = workbook.getSheet(SheetMetadata.PRODUKTY_SHEET_NAME);
			return extractProducts(productsSheet);
		}
	}

	private static Collection<ProductDto> extractProducts(final Sheet productsSheet) {
		return
			takeUntil(productRowStream(productsSheet), ProductRow::isEndRow)
			.filter(ProductRow::isValid)
			.collect(toList())
			;
	}

	private static Stream<ProductRow> productRowStream(Sheet sheet) {
		final SheetMetadata metaData = new SheetMetadata(sheet);
		return stream(sheet.spliterator(), false).map(row -> new ProductRow(row, metaData));
	}

}
