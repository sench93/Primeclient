package com.prime.primeclient.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sench on 10/1/2016.
 */

public class AnalyticsResponse {

    @JsonProperty("paymentAmount")
    public int paymentAmount;

    @JsonProperty("bonusAmount")
    public int bonusAmount;

    @JsonProperty("type")
    public int type;

    @JsonProperty("date")
    public String date;

}
