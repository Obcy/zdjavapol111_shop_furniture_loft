package com.loft.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "shoppingCartId")
    private Set<ShoppingCartItem> cartItems;


    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="userId")
    private User user;




}
