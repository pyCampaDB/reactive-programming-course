import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;

public class Example01 {
    public static void main(String[] args) {
        Flux<String> cities = Flux.fromIterable(
                new ArrayList<>(
                        Arrays.asList(
                                "Tokyo","Córdoba", "Sri Lanka", "Edinburgh", "Zagreb"
                        )
                )
        );
        cities.log().subscribe();
    }
}
