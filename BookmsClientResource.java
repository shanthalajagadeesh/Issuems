package com.example.issuems;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class BookmsClientResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookmsClientResource.class);
    @Autowired
    private WebClient webClient;

    @GetMapping("/client-Bookms")
    @CircuitBreaker(name="issuemsClient", fallbackMethod = "bookmsfallback")
    public Mono getBookFromBookms() {
        LOGGER.info("about to call bookms from issuems");
        return webClient.get().uri("/books").retrieve().bodyToMono(Object.class);
    }
    private Mono bookmsfallback(CallNotPermittedException ex) {
        LOGGER.error("fallback invoked");
        return Mono.just("CalledFromCache");
    }
}
