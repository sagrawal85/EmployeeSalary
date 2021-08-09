package com.employee.domain;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;


@Entity
@Data
@Builder
@Table(name="employees")
public class Employees implements Serializable {

    private static Long serialVersionUID = 1L;

    @NotBlank
    @Id    
    @Column(name = "id",nullable=false)
    private String id;

    @NotBlank
    @Column(name = "login",unique=true, nullable=false)
    private String login;

    @NotBlank
    @Column(name = "name",nullable=false)
    private String name;

    @NotNull
    @DecimalMin(value="0.0")
    @Digits(integer=7, fraction=3)
    @Column(name = "salary",nullable=false)
    private double salary;

    @NotNull
    @Column(name = "startdate",nullable=false)
    private Date startdate;
    
    public Employees(){}

    public Employees(String id, String login, String name, double salary, Date startDate) {
        this.id = id;
    	this.login = login;
        this.name = name;
        this.salary = salary;
        this.startdate = startDate;
    }
   
    @Override
    public String toString() {
        return "Employees{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", salary='" + salary + '\'' +
                ", startdate='" + startdate + '\'' +
                '}';
    }
}
