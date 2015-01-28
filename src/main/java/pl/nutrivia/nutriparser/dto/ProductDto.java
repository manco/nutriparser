package pl.nutrivia.nutriparser.dto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

//TODO wydzielić do modułu przed implementacją integracji z http://www.opennutritiondatabase.com/
public interface ProductDto {
    String getName();

    BigDecimal getProtein();

    BigDecimal getFat();

    BigDecimal getCarbo();

    Optional<BigDecimal> getFatSaturated();

    Optional<BigDecimal> getFatMonoUnsaturated();

    Optional<BigDecimal> getFatPolyUnsaturated();

    Optional<BigDecimal> getCholesterol();

    Optional<BigDecimal> getFiber();

    Map<String, BigDecimal> getVitamines();

    Map<String, BigDecimal> getMinerals();
}
