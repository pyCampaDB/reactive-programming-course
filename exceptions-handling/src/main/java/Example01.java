import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Example01 {
    public static void main(String[] args) {
        Flux.just(2,7,9)
                .concatWith(Flux.error(
                        new RuntimeException("Exception occurred")
                ))
                .concatWith(Mono.just(19))
                .onErrorReturn(99)
                .log()
                .subscribe();
    }
}
