package nutriparser.parser;

import nutriparser.dto.ProductDto;

public class XlsxRowProcessor {
	
	public ProductDto processRow(final ProductRow row) {
		return new ProductDto(row.getName(), row.getVitaminesValues(), row.getMineralsValues());
	}
}
