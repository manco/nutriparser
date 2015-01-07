package nutriparser;

import nutriparser.domain.Mineral;
import nutriparser.domain.ProductDto;
import nutriparser.domain.Vitamin;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import static org.fest.assertions.api.Assertions.assertThat;


public class XlsxProductDaoIntegrationalTest {

	private final XlsxProductDao dao = new XlsxProductDao(new XlsxRowProcessor());

	@Test
	public void shouldHaveKurczak() throws IOException {
		//given

		//when
		final File file = new File(getClass().getResource("/Posilki-v4.91.xls").getFile());
		final Collection<ProductDto> products = dao.extractProducts(file);

		//then
		final Optional<ProductDto> kurczak = products.stream().filter(p -> "Mięso z piersi indyka bez skóry".equals(p.getName())).findAny();
		assertThat(kurczak.isPresent()).isTrue();
		assertThat(kurczak.map(p -> p.getVitamin(Vitamin.B2)).orElse(Double.MAX_VALUE)).isEqualTo(0.15);
		assertThat(kurczak.map(p -> p.getMineral(Mineral.Fe)).orElse(Double.MAX_VALUE)).isEqualTo(0.5);
		assertThat(kurczak.map(p -> p.getMineral(Mineral.Na)).orElse(Double.MAX_VALUE)).isEqualTo(47.0);
	}
}
