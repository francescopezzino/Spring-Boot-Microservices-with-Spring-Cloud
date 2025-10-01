package com.reactiveexample.demoreactive.controller;

import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Random;

@RestController
@RequestMapping("/process")
public class ExampleProcessController {


    // Reactive Response
    @GetMapping("/onethread")
    void getProcesses() {
        Flux.range(1, 10)
                .log()
                .subscribe(this::processMethod);
    }

    @GetMapping("/parallel")
    void getProcessesParallel() {

        Flux.range(1, 10)
                .parallel(10)
                .runOn(Schedulers.parallel())
                .log()
                .subscribe(this::processMethod);
    }

    /*
    if the server resource are scarce, we have to appli backpressure and reduce the frequency
    at which the data have been streamed
     */
    @GetMapping("/parallelBackPressure")
    void getProcessesParallelBackPressure() {

        Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1000))
                .parallel(10)
                .runOn(Schedulers.parallel())
                .log()
                .subscribe(this::processMethod);
    }

    void processMethod(int i) {
        System.out.println("Processing -------------" + i);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
