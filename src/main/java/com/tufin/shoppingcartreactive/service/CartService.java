package com.tufin.shoppingcartreactive.service;

import com.tufin.shoppingcartreactive.domain.Cart;
import com.tufin.shoppingcartreactive.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    @Transactional
    public Mono<Cart> addItemToCart(String cartId, String name, int count, float price) {
        log.info("Add item: cartId={}, name={}, count={}, price={}", cartId, name, count, price);
        return cartRepository.addItem(cartId, name, count, price)
                .then(cartRepository.findById(cartId))
                .doOnSuccess(c -> log.info("Added item OK: cartId={}", cartId));
    }

    @Transactional
    public Mono<Cart> reduceItemInCart(String cartId, String name, int count) {
        log.info("Reduce item: cartId={}, name={}, count={}", cartId, name, count);
        return cartRepository.reduceItem(cartId, name, count)
                .then(cartRepository.findById(cartId))
                .doOnSuccess(c -> log.info("Reduced item OK: cartId={}", cartId));
    }

    @Transactional
    public Mono<Cart> clearCart(String cartId) {
        log.info("clearCart cartId={}", cartId);

        return cartRepository.findById(cartId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cart not found: " + cartId)))
                .flatMap(cart -> {
                    cart.setItems(new ArrayList<>());
                    return cartRepository.save(cart);
                })
                .flatMap(saved -> cartRepository.findById(saved.getId()));
    }

    public Mono<Cart> getCart(String cartId) {
        log.info("Fetching cart with ID: {}", cartId);
        return cartRepository.findById(cartId);
    }
}