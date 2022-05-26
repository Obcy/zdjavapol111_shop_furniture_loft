package com.loft.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "shopping_cart_item")
@NoArgsConstructor
public class ShoppingCartItem {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    @Transient
    private BigDecimal totalItemPrice = BigDecimal.ZERO;


    @PostLoad
    public void onPostLoad() {
        totalItemPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

}
