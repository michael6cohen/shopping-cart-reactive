package com.tufin.shoppingcartreactive.web.graphql;

import com.tufin.shoppingcartreactive.domain.Cart;
import com.tufin.shoppingcartreactive.service.CartService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartMutation {

    private final CartService cartService;

    @MutationMapping
    public Mono<Cart> addItemToCart(@Argument @NotBlank String cartId,
                                    @Argument @NotBlank String name,
                                    @Argument @Positive int count,
                                    @Argument @Positive float price) {
        return cartService.addItemToCart(cartId, name, count, price);
    }

    @MutationMapping
    public Mono<Cart> reduceItemInCart(@Argument @NotBlank String cartId,
                                       @Argument @NotBlank String name,
                                       @Argument @Positive int count) {
        return cartService.reduceItemInCart(cartId, name, count);
    }

    @MutationMapping
    public Mono<Cart> clearCart(@Argument String cartId) {
        return cartService.clearCart(cartId);
    }
}
