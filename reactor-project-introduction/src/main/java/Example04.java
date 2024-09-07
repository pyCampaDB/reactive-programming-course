import reactor.core.publisher.Flux;

public class Example04 {
    public static void main(String[] args) {
        Flux<String> flux = Flux.fromArray(new String[]{"data 1", "data 2", "data 3"});

        //flux.subscribe(System.out::println);
        flux.doOnNext(System.out::println).subscribe();

    }

}
