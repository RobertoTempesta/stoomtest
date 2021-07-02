package com.roberto.stoomtest.controllers.exceptions;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class FieldMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter @Setter
	private String fieldName;
	
	@Getter @Setter
	private String message;

	public FieldMessage(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}

}
