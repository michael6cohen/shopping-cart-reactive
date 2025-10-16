package com.tufin.shoppingcartreactive.domain;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Node
public class Cart {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Relationship(type = "CONTAINS", direction = Relationship.Direction.OUTGOING)
    private List<Contains> items = new ArrayList<>();

    public Cart(String id) {
        this.id = id;
    }
}
