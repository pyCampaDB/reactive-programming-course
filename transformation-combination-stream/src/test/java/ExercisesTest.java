import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class ExercisesTest {

    @Test
    public void mergeWithTest(){
        StepVerifier.create(mergeReturn())
                .expectNext("a")
                .expectNext("c")
                .expectNext("b")
                .expectNext("d")
                .verifyComplete();
    }

    private static Flux<String> mergeReturn() {
        Flux<String> firstFlux = Flux.fromArray(new String[]{"a", "b"})
                .delayElements(Duration.ofMillis(100));
        Flux<String> secondFlux = Flux.fromArray(new String[]{"c", "d"})
                .delayElements(Duration.ofMillis(100));
        return firstFlux.mergeWith(secondFlux);
    }
}
