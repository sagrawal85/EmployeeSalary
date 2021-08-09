package com.employee.dto;

import java.sql.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class EmployeeDTO {

	@NotBlank(message = "Employee ID is mandatory")
	private String id;
	@NotBlank(message = "Employee login is mandatory")
	private String login;
	@NotBlank(message = "Employee Name is mandatory")
	private String name;

	@NotNull(message = "Invalid Salary")
	@DecimalMin(value = "0.0")
	@Digits(integer = 7, fraction = 3)
	private double salary;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Invalid date")
	private Date startDate;
	
	private double minSalary = 0;	
	private double maxSalary=4000.0;	
	private int offset;
	private int limit;
}
