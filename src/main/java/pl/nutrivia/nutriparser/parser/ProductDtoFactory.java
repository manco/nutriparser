package pl.nutrivia.nutriparser.parser;

import pl.nutrivia.nutriparser.dto.ProductDto;
import org.springframework.stereotype.Component;

//TODO użycie nutriparsera jako zależności w nutriva-webapp
//TODO cóż z tą klasą?
@Component
public class ProductDtoFactory {
	
	public ProductDto fromRow(final ProductRow row) {
		return new ProductDto(row.getName(), row.getVitaminesValues(), row.getMineralsValues());
	}
}
