package com.prime.primeclient.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by sench on 10/1/2016.
 */

public class AnalyticsResponse {

    @JsonProperty("typeTwo")
    public ArrayList<TypeTwo> typeTwo;

    @JsonProperty("typeOne")
    public ArrayList<TypeOne> typeOne;

}
