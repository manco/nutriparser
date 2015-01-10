package pl.nutrivia.nutriparser.util;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class MapsUtils {

    private MapsUtils() {
    }

    public static <K, V> Map<K, V> toMap(Collection<K> keys, Function<K, V> valueFunction) {
        return keys.stream().collect(Collectors.toMap(Function.identity(), valueFunction));
    }
}
