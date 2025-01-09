package com.qbitum.template.controller;

import com.qbitum.template.dao.FruitDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    @Transactional
    public Mono<ResponseEntity<Fruit>> createFruit(Fruit fruit) {
        if (fruit.getId() != null) {
            return Mono.error(
                new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Id was invalidly set on request."
                )
            );
        }

        return fruitDao
            .save(fruit)
            .map(
                savedFruit ->
                    ResponseEntity.status(HttpStatus.CREATED).body(savedFruit)
            );
    }
}
