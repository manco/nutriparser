package pl.nutrivia.nutriparser.parser;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import pl.nutrivia.nutriparser.api.InvalidFormatException;
import pl.nutrivia.nutriparser.api.ProductDto;
import pl.nutrivia.nutriparser.api.WorkbookParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import static com.codepoetics.protonpack.StreamUtils.takeUntil;
import static java.util.stream.Collectors.toList;

public class WorkbookParserImpl implements WorkbookParser {

	@Override
	public Collection<ProductDto> extractProducts(final File file) throws IOException, InvalidFormatException {
		try (final Workbook workbook = WorkbookFactory.create(file)) {
			return extractProducts(workbook);
		} catch (final org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
			throw new InvalidFormatException(e);
		}
	}

	@Override
	public Collection<ProductDto> extractProducts(final InputStream inputStream) throws IOException, InvalidFormatException {
		try (final Workbook workbook = WorkbookFactory.create(inputStream)) {
			return extractProducts(workbook);
		} catch (final org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
			throw new InvalidFormatException(e);
		}
	}

	private static Collection<ProductDto> extractProducts(final Workbook workbook) {
		return
			takeUntil(new ProductSheet(workbook).productRowStream(), ProductRow::isEndRow)
			.filter(ProductRow::isValid)
			.collect(toList())
			;
	}
}
