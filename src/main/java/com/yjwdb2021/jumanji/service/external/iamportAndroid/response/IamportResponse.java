package com.yjwdb2021.jumanji.service.external.iamportAndroid.response;

import com.google.gson.annotations.SerializedName;

public class IamportResponse<T> {

	@SerializedName("code")
	int code;
	
	@SerializedName("message")
	String message;
	
	@SerializedName("response")
	T response;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public T getResponse() {
		return response;
	}
	
}
