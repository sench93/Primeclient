package com.prime.primeclient.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Responses<T> {
    @JsonProperty("content")
    public T content;

    @JsonProperty("message")
    public String message;

    @JsonProperty("status")
    public int status;





}
