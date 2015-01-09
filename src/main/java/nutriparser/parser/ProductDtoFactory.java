package nutriparser.parser;

import nutriparser.dto.ProductDto;
import org.springframework.stereotype.Component;

//TODO test z kontekstem springa
//TODO unit testy dla productCell, Row
//TODO użycie nutriparsera jako zależności w nutriva-webapp

@Component
public class ProductDtoFactory {
	
	public ProductDto processRow(final ProductRow row) {
		return new ProductDto(row.getName(), row.getVitaminesValues(), row.getMineralsValues());
	}
}
