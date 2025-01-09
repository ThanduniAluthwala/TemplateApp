package com.qbitum.template.dao;

import com.qbitum.template.controller.Fruit;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FruitDao
    extends
        ReactiveCrudRepository<Fruit, Integer>,
        ReactiveQueryByExampleExecutor<Fruit> {}
