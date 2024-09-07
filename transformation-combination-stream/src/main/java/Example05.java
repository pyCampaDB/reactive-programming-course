import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Example05 {
    public static void main(String[] args) {
        Flux<String> flux = Flux.fromArray(
                new String[]{"a", "b", "c"}
        );

        Mono<String> mono = Mono.just("f");

        Flux<String> fluxConcat = flux.concatWith(mono);
        //Flux<String> fluxConcat = Flux.concat(flux, mono);
        fluxConcat.subscribe(e -> System.out.println(e + " "));
    }
}
