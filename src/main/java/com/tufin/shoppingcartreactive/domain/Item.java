package com.tufin.shoppingcartreactive.domain;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Item {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue
    private Long id;
    private String name;

    public Item(String name) {
        this.name = name;
    }
}
