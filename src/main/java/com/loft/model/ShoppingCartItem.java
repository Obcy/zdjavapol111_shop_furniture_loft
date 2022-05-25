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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    private BigDecimal totalItemPrice = BigDecimal.ZERO;


}
