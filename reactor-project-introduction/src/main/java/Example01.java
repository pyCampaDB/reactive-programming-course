import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class Example01 {
    public static void main(String[] args) {
        List<Integer> elementsFromMono = new ArrayList<>();
        List<Integer> elementsFromFlux = new ArrayList<>();

        //Create a Mono
        Mono<Integer> mono = Mono.just(121);

        //Create a Flux
        Flux<Integer> flux = Flux.just(13,15,9,7,19,2);

        // subscribe to Mono
        mono.subscribe(elementsFromMono::add);

        //subscribe to Flux
        flux.subscribe(elementsFromFlux::add);

        System.out.println(elementsFromMono);
        System.out.println(elementsFromFlux);

    }
}
