package com.cst438.domain;

public class StudentDTO {
	public String name;
	public String email;
	public int student_id;
	public int statusCode;
	public String status;
	
	@Override
	public String toString() {
		return"Student [id + " + student_id + ", status = " + statusCode + " name = " +
				name + ", email = " + email + "]";
	}
}