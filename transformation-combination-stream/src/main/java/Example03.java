import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Example03 {
    public static void main(String[] args) {
        Flux.fromArray(
                        new String[]{
                                "Zaraki",
                                "Kenpachi",
                                "Battösai",
                                "Madara"
                        }
                )
                .flatMap(Example03::putModifiedNameInMono)
                //with map, returns "MonoJust" 4 times
                .subscribe(System.out::println);
    }
    private static Mono<String> putModifiedNameInMono(String name){
        return Mono.just(name.concat(" modified"));
    }
}
