package com.loft.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_item")
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    private String productName;

    private String thumbnail;

    private BigDecimal price;

    private BigDecimal displayPrice;

    private int quantity;


}
