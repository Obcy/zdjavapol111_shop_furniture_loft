package com.loft.model;


import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class BillingInfo implements Serializable {

    private String billingName;
    private String billingCompanyName;
    private String billingTaxId;
    private String billingCity;
    private String billingPostalCode;
    private String billingStreet;
    private String billingHouseNumber;
    private String billingFlatNumber;
    private String billingCountry;


}
