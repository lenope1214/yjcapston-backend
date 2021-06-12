package com.yjwdb2021.jumanji.service.external.iamportAndroid.request.payco;

import com.google.gson.annotations.SerializedName;

public class OrderStatusData {

	@SerializedName("status")
	private String status;
	
	public OrderStatusData(String status) {
		this.status = status;
	}
	
}
