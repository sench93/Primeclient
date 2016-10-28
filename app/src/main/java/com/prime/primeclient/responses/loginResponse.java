package com.prime.primeclient.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sench on 9/21/2016.
 */
public class loginResponse {

        @JsonProperty("token")
        public String token;

}
