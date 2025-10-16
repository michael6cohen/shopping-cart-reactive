package com.tufin.shoppingcartreactive.web.graphql;

import com.tufin.shoppingcartreactive.domain.Cart;
import com.tufin.shoppingcartreactive.domain.Item;
import com.tufin.shoppingcartreactive.repository.CartRepository;
import com.tufin.shoppingcartreactive.repository.ItemRepository;
import com.tufin.shoppingcartreactive.service.CartService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartQuery {

    private final CartService cartService;

    @QueryMapping
    public Mono<Cart> getCart(@Argument @NotBlank String cartId) {
        log.info("Query: get cart by id={}", cartId);
        return cartService.getCart(cartId);
    }
}