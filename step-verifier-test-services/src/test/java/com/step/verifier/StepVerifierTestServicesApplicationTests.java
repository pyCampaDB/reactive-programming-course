package com.step.verifier;

import com.step.verifier.services.SimpleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

@SpringBootTest
class StepVerifierTestServicesApplicationTests {

	@Autowired
	SimpleService simpleService;

	@Test
	void monoTest(){
		Mono<String> one = simpleService.searchOne();
		StepVerifier.create(one).expectNext("Hello").verifyComplete();
	}

	@Test
	void severalTest(){
		Flux<String> several = simpleService.searchAll();
		StepVerifier.create(several)
				.expectNext("Hello")
				.expectNext("What's")
				.expectNext("up")
				.expectNext("?")
				.verifyComplete();

	}

	@Test
	void slowSeveralTest(){
		Flux<String> several = simpleService.slowSearchAll();
		StepVerifier.create(several)
				.expectNext("Hello")
				.thenAwait(Duration.ofSeconds(1))
				.expectNext("What's")
				.thenAwait(Duration.ofSeconds(1))
				.expectNext("up")
				.thenAwait(Duration.ofSeconds(1))
				.expectNext("?")
				.thenAwait(Duration.ofSeconds(1))
				.verifyComplete();
	}

}
