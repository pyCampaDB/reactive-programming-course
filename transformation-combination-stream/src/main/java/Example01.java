import reactor.core.publisher.Flux;

public class Example01 {
    public static void main(String[] args) {
        Flux.fromArray(
                new String[]{
                        "Zaraki",
                        "Kenpachi",
                        "Battösai",
                        "Madara"
                }
        ).map(String::toUpperCase)
                .subscribe(System.out::println);
    }
}
