import reactor.core.publisher.Flux;

public class Example07 {
    public static void main(String[] args) {
        Flux<Integer> flux1 = Flux.just(1,2,3,4,5);
        Flux<Integer> flux2 = Flux.just(9,8,9);
        flux1.zipWith(flux2, Integer::sum).subscribe(System.out::println);
        //(Integer::sum) == (first, second) -> first+second
    }
}
