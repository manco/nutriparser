package pl.nutrivia.nutriparser.parser;

import com.google.common.base.Strings;
import pl.nutrivia.nutriparser.dto.Mineral;
import pl.nutrivia.nutriparser.dto.ProductDto;
import pl.nutrivia.nutriparser.dto.Vitamin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.Collection;
import java.util.Optional;

import static org.fest.assertions.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WorkbookParserLongTest.class)
@Configuration
@ComponentScan(basePackageClasses = WorkbookParser.class)
public class WorkbookParserLongTest {

	public static final double INVALID_VALUE = Double.MAX_VALUE;
	@Autowired private WorkbookParser parser;

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
		assertThat(kurczak.map(p -> p.getMineral(Mineral.Fe)).orElse(INVALID_VALUE)).isEqualTo(0.5);
		assertThat(kurczak.map(p -> p.getMineral(Mineral.Na)).orElse(INVALID_VALUE)).isEqualTo(47.0);
	}

	@Test
	public void shouldProcessVitamines() throws Exception {
		//given

		//when
		final Collection<ProductDto> products = parser.extractProducts(file);

		//then
		assertThat(findKurczak(products).map(p -> p.getVitamin(Vitamin.B2)).orElse(INVALID_VALUE)).isEqualTo(0.15);
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
