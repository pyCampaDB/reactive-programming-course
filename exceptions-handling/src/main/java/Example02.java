import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Example02 {
    public static void main(String[] args) {
        Flux.just(2,7,9)
                .concatWith(Flux.error(
                        new RuntimeException("Exception occurred")
                ))
                .concatWith(Mono.just(19))
                .onErrorResume(err ->{
                    System.out.println("Error: " + err);
                    return Mono.just(99);
                })
                .log()
                .subscribe();
    }
}
