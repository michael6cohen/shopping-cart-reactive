package com.tufin.shoppingcartreactive.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RelationshipProperties
public class Contains {

    @RelationshipId
    private Long id;

    private int count;
    private float price;

    @TargetNode
    private Item item;

    public String getName() {
        return item != null ? item.getName() : null;
    }

    public Contains(Item item, int count, float price) {
        this.item = item;
        this.count = count;
        this.price = price;
    }
}
