package com.step.verifier.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class SimpleService {
    public Mono<String> searchOne(){
        return Mono.just("Hello");
    }

    public Flux<String> searchAll(){
        return Flux.just("Hello", "What's", "up", "?");
    }

    public Flux<String> slowSearchAll(){
        return Flux.just("Hello", "What's", "up", "?").delaySequence(Duration.ofSeconds(10));
    }
}
