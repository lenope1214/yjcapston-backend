package com.yjwdb2021.jumanji.service.external.iamportAndroid.request;

public class CardInfo {

	protected String card_number;
	protected String expiry;
	protected String birth;
	protected String pwd_2digit;

	public CardInfo(String card_number, String expiry, String birth, String pwd_2digit) {
		this.card_number = card_number;
		this.expiry = expiry;
		this.birth = birth;
		this.pwd_2digit = pwd_2digit;
	}
	
}
