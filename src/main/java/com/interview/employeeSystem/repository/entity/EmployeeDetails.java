package com.interview.employeeSystem.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Emp_Details")
public class EmployeeDetails implements Serializable {

    @Id
    private int id;
    @Column(name = "Employee_Name")
    private String employeeName;
    private String designation;
    private String department;
    private int role_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role) {
        this.role_id = role_id;
    }
}
