package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/fruits")
public class FruitController {
    private final FruitDao fruitDao;

    public FruitController(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @GetMapping
    public Mono<String> sayHello() {
        return Mono.just("Hello");
    }

    @PostMapping
    public Mono<ResponseEntity<Fruit>> createFruit(@RequestBody Fruit fruit) {
        System.out.println("Fruit to save: " + fruit);
        return fruitDao
                .save(fruit)
                .map(
                        savedFruit ->
                                ResponseEntity.status(HttpStatus.CREATED).body(savedFruit)
                );
    }


}
