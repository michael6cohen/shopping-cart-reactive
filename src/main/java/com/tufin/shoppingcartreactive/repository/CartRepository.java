package com.tufin.shoppingcartreactive.repository;

import com.tufin.shoppingcartreactive.domain.Cart;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import reactor.core.publisher.Mono;

public interface CartRepository extends ReactiveNeo4jRepository<Cart, String> {

    @Query("""
    MERGE (c:Cart {id: $cartId})
    MERGE (i:Item {name: $name})
    MERGE (c)-[r:CONTAINS]->(i)
    ON CREATE SET r.count = $count, r.price = $price
    ON MATCH  SET r.count = coalesce(r.count,0) + $count, r.price = $price
    RETURN c
    """)
    Mono<Cart> addItem(String cartId, String name, int count, float price);

    @Query("""
    MATCH (c:Cart {id: $cartId})-[r:CONTAINS]->(i:Item)
    WHERE toLower(i.name) = toLower($name)
    SET r.count = coalesce(r.count,0) - $count
    WITH c, r
    WHERE r.count <= 0
    DELETE r
    RETURN c
    """)
    Mono<Cart> reduceItem(String cartId, String name, int count);
}