package com.loft.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "shopping_cart")
@NoArgsConstructor
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "")
    @JoinColumn(name = "shopping_cart_id")
    private Set<ShoppingCartItem> cartItems = new HashSet<>();

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;



}
