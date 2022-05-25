package com.loft.model;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class DeliveryInfo implements Serializable {

    private String deliveryName;
    private String deliveryCity;
    private String deliveryPostalCode;
    private String deliveryStreet;
    private String deliveryHouseNumber;
    private String deliveryFlatNumber;
    private String deliveryCountry;


}
