import reactor.core.publisher.Flux;

public class Example02 {
    public static void main(String[] args) {
        Flux.fromArray(
                        new String[]{
                                "Zaraki",
                                "Kenpachi",
                                "Battösai",
                                "Madara"
                        }
                ).filter(name -> name.length()>6)
                .map(String::toUpperCase)
                .subscribe(System.out::println);
    }
}
