package pl.nutrivia.nutriparser.parser;

import org.junit.Test;
import pl.nutrivia.nutriparser.api.ProductDto;
import pl.nutrivia.nutriparser.api.WorkbookParser;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.api.Fail.fail;

public class WorkbookParserImplLongTest {

	private final WorkbookParser parser = new WorkbookParserImpl();

	private final File file = inputFile();

	@Test
	public void shouldFindKurczak() throws Exception {
		//given

		//when
		final Collection<ProductDto> products = parser.extractProducts(file);
		//then
		assertThat(products).hasSize(1022);
		final ProductDto kurczak = findKurczak(products).orElseGet(() -> {
            fail("kurczak should have been found");
            return null;
        });
		assertThatValueEquals(kurczak.getCarbo(), 0);
		assertThatValueEquals(kurczak.getProtein(), 19.2);
		assertThatValueEquals(kurczak.getFat(), 0.7);
		assertThatValueEquals(kurczak.getCholesterol(), 49);
		assertThatValueEquals(kurczak.getFiber(), 0);
		assertThatValueEquals(kurczak.getFatSaturated(), 0.2);
		assertThatValueEquals(kurczak.getFatMonoUnsaturated(), 0.3);
		assertThatValueEquals(kurczak.getFatPolyUnsaturated(), 0.2);
		assertThat(kurczak.getMinerals()).contains(entry("Żelazo", 0.5));
		assertThat(kurczak.getMinerals()).contains(entry("Sód", 47.0));
		assertThat(kurczak.getMinerals()).contains(entry("B2", 0.15));
	}

	private static void assertThatValueEquals(Optional<BigDecimal> valueMaybe, double shouldEqual) {
		assertThatValueEquals(valueMaybe.get(), shouldEqual);
	}

	private static void assertThatValueEquals(BigDecimal value, double shouldEqual) {
		assertThat(value).isEqualTo(BigDecimal.valueOf(shouldEqual));
	}

	private static Optional<ProductDto> findKurczak(Collection<ProductDto> products) {
		return products.stream().filter(WorkbookParserImplLongTest::isNameKurczak).findAny();
	}

	private static boolean isNameKurczak(ProductDto p) {
		return "Mięso z piersi indyka bez skóry".equalsIgnoreCase(p.getName().trim());
	}

	private File inputFile() {
		return new File(getClass().getResource("/Posilki-v4.91.xls").getFile());
	}

}
