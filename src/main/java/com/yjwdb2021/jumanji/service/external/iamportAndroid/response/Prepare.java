package com.yjwdb2021.jumanji.service.external.iamportAndroid.response;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Prepare {

    @SerializedName("merchant_uid")
    String merchant_uid;

    @SerializedName("amount")
    BigDecimal amount;
}
