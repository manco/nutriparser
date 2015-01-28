package pl.nutrivia.nutriparser.parser;

import com.google.common.base.Strings;
import org.junit.Test;
import pl.nutrivia.nutriparser.dto.ProductDto;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import static org.fest.assertions.api.Assertions.assertThat;

public class WorkbookParserLongTest {

	public static final BigDecimal INVALID_VALUE = BigDecimal.valueOf(999999999999L);
	private final WorkbookParser parser = new WorkbookParser();

	private final File file = inputFile();

	@Test
	public void shouldFindKurczak() throws Exception {
		//given

		//when
		final Collection<ProductDto> products = parser.extractProducts(file);
		//then
		assertThat(products).hasSize(1022);
		assertThat(findKurczak(products).isPresent()).isTrue();
	}

	@Test
	public void shouldProcessMinerals() throws Exception {
		//given

		//when
		final Collection<ProductDto> products = parser.extractProducts(file);

		//then
		final Optional<ProductDto> kurczak = findKurczak(products);
		assertThat(kurczak.map(p -> p.getMinerals().get("Żelazo")).orElse(INVALID_VALUE)).isEqualTo(BigDecimal.valueOf(0.5));
		assertThat(kurczak.map(p -> p.getMinerals().get("Sód")).orElse(INVALID_VALUE)).isEqualTo(BigDecimal.valueOf(47));
	}

	@Test
	public void shouldProcessVitamines() throws Exception {
		//given

		//when
		final Collection<ProductDto> products = parser.extractProducts(file);

		//then
		assertThat(findKurczak(products).map(p -> p.getVitamines().get("B2")).orElse(INVALID_VALUE)).isEqualTo(BigDecimal.valueOf(0.15));
	}

	private static Optional<ProductDto> findKurczak(Collection<ProductDto> products) {
		return products.stream().filter(WorkbookParserLongTest::isNameKurczak).findAny();
	}

	private static boolean isNameKurczak(ProductDto p) {
		return "Mięso z piersi indyka bez skóry".equalsIgnoreCase(Strings.nullToEmpty(p.getName()).trim());
	}

	private File inputFile() {
		return new File(getClass().getResource("/Posilki-v4.91.xls").getFile());
	}

}
