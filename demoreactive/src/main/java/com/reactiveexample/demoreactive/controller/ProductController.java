package com.reactiveexample.demoreactive.controller;

import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.Flow;

@RestController
@RequestMapping("/products")
public class ProductController {


    // Reactive Response
    @GetMapping(value = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Publisher<String> getProducts() {
        Random random = new Random();
        Flux<String> products;
        products = Flux.<String>generate(sink -> sink.next("Live Temperature : " + random
                .nextInt(50))).delayElements(Duration.ofSeconds(2));
        return products;

    }
}
