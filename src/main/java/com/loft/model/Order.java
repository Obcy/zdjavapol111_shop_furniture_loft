package com.loft.model;

import com.loft.generators.OrderKeyGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "orders")
public class Order {

//    @Id
//    @GeneratedValue(strategy =  GenerationType.IDENTITY)
//    private String id;

    @Id
    @GenericGenerator(name = "order_number", strategy = "com.loft.generators.OrderNumberGenerator")
    @GeneratedValue(generator = "order_number")
    private String orderNumber;

    @GeneratorType(type = OrderKeyGenerator.class, when = GenerationTime.INSERT)
    private String orderKey;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_item_id")
    private Set<OrderItem> orderItems = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    private OrderStatus orderStatus = OrderStatus.NEW;

    @Embedded
    private DeliveryInfo deliveryInfo;

    @Embedded
    private BillingInfo billingInfo;

}
