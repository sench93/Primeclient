package com.prime.primeclient.responses;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by sench on 10/24/2016.
 */

public class TypeOne implements Serializable{
    @JsonProperty("paymentAmount")
    public int paymentAmount;

    @JsonProperty("bonusAmount")
    public int bonusAmount;

    @JsonProperty("date")
    public String date;

}
