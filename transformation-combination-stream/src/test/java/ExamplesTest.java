import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ExamplesTest {
    @Test
    public void transformMapTest(){
        List<String> namesList = Arrays.asList("google", "abc", "ig", "stackoverflow");
        Flux<String> namesFlux = Flux.fromIterable(namesList)
                .filter(name->name.length()>5)
                .map(String::toUpperCase)
                .log();
        StepVerifier.create(namesFlux)
                .expectNext("GOOGLE", "STACKOVERFLOW")
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatMapTest(){
        List<String> namesList = Arrays.asList("google", "abc", "ig", "stackoverflow");
        Flux<String> namesFlux = Flux.fromIterable(namesList)
                .filter(name->name.length()>5)
                .flatMap(name -> {
                    return Mono.just(name.toUpperCase());
                })
                .log();
        StepVerifier.create(namesFlux)
                .expectNext("GOOGLE", "STACKOVERFLOW")
                .verifyComplete();
    }


    @Test
    public void combiningStreamUsingMergeTest(){
        Flux<String> flux1 = Flux.just("Blenders", "Old", "Johnnie");
        Flux<String> flux2 = Flux.just("Pride", "Monk", "Walker");
        Flux<String> fluxMerge = Flux.merge(flux1,flux2).log();

        StepVerifier.create(fluxMerge)
                .expectSubscription()
                .expectNext("Blenders", "Old", "Johnnie", "Pride", "Monk", "Walker")
                .verifyComplete();
    }

    @Test
    public void combiningStreamUsingMergeWithDelayTest(){
        Flux<String> flux1 = Flux.just("Blenders", "Old", "Johnnie")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("Pride", "Monk", "Walker")
                .delayElements(Duration.ofSeconds(1));

        Flux<String> fluxMerge = Flux.merge(flux1,flux2).log();

        StepVerifier.create(fluxMerge)
                .expectSubscription()
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void combiningStreamWithDelayAndConcatOperatorTest(){
        Flux<String> flux1 = Flux.just("Blenders", "Old", "Johnnie")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("Pride", "Monk", "Walker")
                .delayElements(Duration.ofSeconds(1));

        Flux<String> fluxConcat = Flux.concat(flux1,flux2).log();

        StepVerifier.create(fluxConcat)
                .expectSubscription()
                .expectNext("Blenders", "Old", "Johnnie", "Pride", "Monk", "Walker")
                .verifyComplete();
    }

    @Test
    public void combiningStreamWithDelayAndZipOperatorTest(){
        Flux<String> flux1 = Flux.just("Blenders", "Old", "Johnnie", "Zaraki")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("Pride", "Monk", "Walker", "Kenpachi")
                .delayElements(Duration.ofSeconds(1));

        Flux<String> fluxZip = Flux.zip(flux1, flux2,(f1,f2) -> {
            return f1.concat(" ").concat(f2);
        }).log();

        StepVerifier.create(fluxZip)
                .expectSubscription()
                .expectNext("Blenders Pride", "Old Monk", "Johnnie Walker", "Zaraki Kenpachi")
                .verifyComplete();
    }
}
