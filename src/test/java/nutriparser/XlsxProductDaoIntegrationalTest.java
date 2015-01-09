package nutriparser;

import nutriparser.dto.Mineral;
import nutriparser.dto.ProductDto;
import nutriparser.dto.Vitamin;
import nutriparser.parser.XlsxProductDao;
import nutriparser.parser.XlsxRowProcessor;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import static org.fest.assertions.api.Assertions.assertThat;


public class XlsxProductDaoIntegrationalTest {

	public static final double INVALID_VALUE = Double.MAX_VALUE;
	private final XlsxProductDao dao = new XlsxProductDao(new XlsxRowProcessor());

	@Test
	public void shouldHaveKurczak() throws IOException {
		//given
		final File file = new File(getClass().getResource("/Posilki-v4.91.xls").getFile());

		//when
		final Collection<ProductDto> products = dao.extractProducts(file);

		//then
		assertThat(findKurczak(products).isPresent()).isTrue();
	}

	@Test
	public void shouldProcessMinerals() throws IOException {
		//given
		final File file = new File(getClass().getResource("/Posilki-v4.91.xls").getFile());

		//when
		final Collection<ProductDto> products = dao.extractProducts(file);

		//then
		final Optional<ProductDto> kurczak = findKurczak(products);
		assertThat(kurczak.map(p -> p.getMineral(Mineral.Fe)).orElse(INVALID_VALUE)).isEqualTo(0.5);
		assertThat(kurczak.map(p -> p.getMineral(Mineral.Na)).orElse(INVALID_VALUE)).isEqualTo(47.0);
	}

	@Test
	public void shouldProcessVitamines() throws IOException {
		//given
		final File file = new File(getClass().getResource("/Posilki-v4.91.xls").getFile());

		//when
		final Collection<ProductDto> products = dao.extractProducts(file);

		//then
		final Optional<ProductDto> kurczak = findKurczak(products);
		assertThat(kurczak.map(p -> p.getVitamin(Vitamin.B2)).orElse(INVALID_VALUE)).isEqualTo(0.15);
	}

	private static Optional<ProductDto> findKurczak(Collection<ProductDto> products) {
		return products.stream().filter(XlsxProductDaoIntegrationalTest::isNameKurczak).findAny();
	}

	private static boolean isNameKurczak(ProductDto p) {
		return "Mięso z piersi indyka bez skóry".equals(p.getName());
	}
}
