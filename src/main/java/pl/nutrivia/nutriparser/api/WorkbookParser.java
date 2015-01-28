package pl.nutrivia.nutriparser.api;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import pl.nutrivia.nutriparser.api.ProductDto;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public interface WorkbookParser {
    Collection<ProductDto> extractProducts(File file) throws IOException, InvalidFormatException;
    Collection<ProductDto> extractProducts(InputStream inputStream) throws IOException, InvalidFormatException;
}
