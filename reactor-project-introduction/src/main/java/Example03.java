import reactor.core.publisher.Mono;

public class Example03 {
    public static void main(String[] args) {
        Mono<String> mono = Mono.fromSupplier(
                () -> {
                    throw new RuntimeException("An exception occurred");
                }
        );

        /* Same that Example 2, except the above statement */
        mono.subscribe(
                System.out::println,
                System.out::println,
                () -> System.out.println("Completed!")
        );
    }
}
