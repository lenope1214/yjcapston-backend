package com.yjwdb2021.jumanji.service.external.iamportAndroid.request;

import com.google.gson.annotations.SerializedName;

public class ExtraRequesterEntry {

    @SerializedName("requester")
    private String requester;

    public ExtraRequesterEntry(String requester) {
        this.requester = requester;
    }

    public String getRequester() {
        return requester;
    }
}
