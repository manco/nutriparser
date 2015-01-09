package nutriparser.parser;

import nutriparser.dto.Mineral;
import nutriparser.dto.ProductDto;
import nutriparser.dto.Vitamin;
import org.junit.Test;

import java.io.File;
import java.util.Collection;
import java.util.Optional;

import static org.fest.assertions.api.Assertions.assertThat;


public class XlsxProductDaoIntegrationalTest {

	public static final double INVALID_VALUE = Double.MAX_VALUE;
	private final XlsxProductDao dao = new XlsxProductDao(new ProductDtoFactory());

	private final File file = inputFile();

	@Test
	public void shouldFindKurczak() throws Exception {
		//given

		//when
		final Collection<ProductDto> products = dao.extractProducts(file);

		//then
		assertThat(findKurczak(products).isPresent()).isTrue();
	}

	@Test
	public void shouldProcessMinerals() throws Exception {
		//given

		//when
		final Collection<ProductDto> products = dao.extractProducts(file);

		//then
		final Optional<ProductDto> kurczak = findKurczak(products);
		assertThat(kurczak.map(p -> p.getMineral(Mineral.Fe)).orElse(INVALID_VALUE)).isEqualTo(0.5);
		assertThat(kurczak.map(p -> p.getMineral(Mineral.Na)).orElse(INVALID_VALUE)).isEqualTo(47.0);
	}

	@Test
	public void shouldProcessVitamines() throws Exception {
		//given

		//when
		final Collection<ProductDto> products = dao.extractProducts(file);

		//then
		assertThat(findKurczak(products).map(p -> p.getVitamin(Vitamin.B2)).orElse(INVALID_VALUE)).isEqualTo(0.15);
	}

	private static Optional<ProductDto> findKurczak(Collection<ProductDto> products) {
		return products.stream().filter(XlsxProductDaoIntegrationalTest::isNameKurczak).findAny();
	}

	private static boolean isNameKurczak(ProductDto p) {
		return "Mięso z piersi indyka bez skóry".equals(p.getName());
	}

	private File inputFile() {
		return new File(getClass().getResource("/Posilki-v4.91.xls").getFile());
	}
}
