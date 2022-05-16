package com.loft.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class ShoppingCartItem {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;


}
