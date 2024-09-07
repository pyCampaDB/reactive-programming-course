import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

public class Example01 {
    public static void main(String[] args) throws InterruptedException {
        Flux<String> netFlux = Flux.fromStream(Example01::getVideo)
                .delayElements(Duration.ofSeconds(2));

        netFlux.subscribe(part -> System.out.println("Subscriber 1 " + part));
        Thread.sleep(5000);
        netFlux.subscribe(part -> System.out.println("Subscriber 2 " + part));
        Thread.sleep(6000);
    }

    public static Stream<String> getVideo(){
        System.out.println("Request for the video");
        return Stream.of("part 1", "part 2", "part 3", "part 4", "part 5");
    }
}
