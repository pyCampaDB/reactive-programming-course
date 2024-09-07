package com.reactive.api.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/mono")
    public Mono<String> getMono(){
        return Mono.just("Welcome to my channel, sign up!");
    }

    @GetMapping("/flux")//(path = "flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getFlux(){
        /*List<String> list = List.of("Welcome ", "to ", "my ", "channel");
        return Flux.fromIterable(list).delayElements(Duration.ofMillis(1500))
                .log();*/
        return Flux.just("Welcome ", "to ", "my ", "channel")
                .delayElements(Duration.ofMillis(1500))
                .log();

    }
}
