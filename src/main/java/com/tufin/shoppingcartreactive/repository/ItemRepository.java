package com.tufin.shoppingcartreactive.repository;

import com.tufin.shoppingcartreactive.domain.Item;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;

public interface ItemRepository extends ReactiveNeo4jRepository<Item, Long> {
}