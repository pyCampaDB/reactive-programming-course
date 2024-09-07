import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Example03 {
    public static void main(String[] args) {
        Flux.just(2,0,9,19,17,29)
                .map(element -> {
                    if (element == 0)
                        throw new RuntimeException("Exception occurred!");
                    return element;
                }).onErrorContinue(
                        (ex, element) -> {
                            System.out.println("Exception: " + ex);
                            System.out.println("The element causing the exception is: " + element);
                        })
                .log()
                .subscribe();
    }
}
