package com.loft.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private String thumbnail;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    private BigDecimal price;

    @Transient
    private BigDecimal displayPrice;

    @PostLoad
    public void onPostLoad() {
        displayPrice = getPrice();
    }

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    private String author;


}
