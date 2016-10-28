package com.prime.primeclient.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by sench on 10/24/2016.
 */

public class TypeTwo extends Object implements Serializable {

    @JsonProperty("paymentAmount")
    public int paymentAmount;

    @JsonProperty("restaurantBonusAmount")
    public int restaurantBonusAmount;

    @JsonProperty("date")
    public String date;

}
