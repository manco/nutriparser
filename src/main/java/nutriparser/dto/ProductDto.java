package nutriparser.dto;

import java.util.Map;

public class ProductDto {
	private final String name;
	private final Map<Vitamin, Double> vitamines;
	private final Map<Mineral, Double> minerals;

	public ProductDto(String name, Map<Vitamin, Double> vitamines, Map<Mineral, Double> minerals) {
		this.name = name;
		this.vitamines = vitamines;
		this.minerals = minerals;
	}

	@Override
	public String toString() {
		return name;
	}
	
	public String getName() {
		return name;
	}
	public Double getVitamin(Vitamin vit) {
		return vitamines.get(vit);
	}
	public Double getMineral(Mineral mineral) {
		return minerals.get(mineral);
	}

}
