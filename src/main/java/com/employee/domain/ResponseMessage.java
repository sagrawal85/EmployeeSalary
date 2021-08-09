package com.employee.domain;

import lombok.Data;

@Data
public class ResponseMessage {

	private int statuscode;
	private String message;

	public ResponseMessage() {
	}
	
	public ResponseMessage(int code, String message) {
		this.statuscode = code;
		this.message = message;
	}

}
