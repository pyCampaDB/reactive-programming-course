import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ExamplesTest {
    @Test
    public void testFlux(){
        Flux<Integer> fluxToTest = Flux.just(9,8,7,6,5);

        StepVerifier.create(fluxToTest)
                .expectNext(9)
                .expectNext(8)
                .expectNext(7)
                .expectNext(6)
                .expectNext(5)
                .expectComplete()
                .verify();
    }

    @Test
    public void testFluxString(){
        Flux<String> fluxToTest = Flux.just("Kenpachi", "Obito", "Gojo", "Benzema", "Özil", "Khedira", "Lass")
                .filter(name -> name.length()<=5)
                .map(String::toUpperCase);
        StepVerifier.create(fluxToTest)
                .expectNext("OBITO")
                .expectNext("GOJO")
                .expectNextMatches(name -> name.startsWith("ÖZ"))
                .expectNext("LASS")
                .expectComplete()
                .verify();

    }
}
