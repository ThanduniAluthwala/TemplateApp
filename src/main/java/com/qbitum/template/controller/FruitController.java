package com.qbitum.template.controller;

import com.qbitum.template.dao.FruitDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

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
    public Mono<ResponseEntity<Fruit>> createFruit(
        @RequestBody Mono<Fruit> fruitMono
    ) {
        return fruitMono
            .flatMap(fruitDao::save)
            .map(
                savedFruit ->
                    ResponseEntity.status(HttpStatus.CREATED).body(savedFruit)
            );
    }
}
