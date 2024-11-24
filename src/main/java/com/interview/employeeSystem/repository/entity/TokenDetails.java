package com.interview.employeeSystem.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class TokenDetails {

    @Id
    private int token_Id;
    private int employee_Id;
    private String Auth_Token;
    private Date create_Time;
    private Date expire_Time;

    public int getToken_Id() {
        return token_Id;
    }

    public void setToken_Id(int token_Id) {
        this.token_Id = token_Id;
    }

    public int getEmployee_Id() {
        return employee_Id;
    }

    public void setEmployee_Id(int employee_Id) {
        this.employee_Id = employee_Id;
    }

    public String getAuth_Token() {
        return Auth_Token;
    }

    public void setAuth_Token(String auth_Token) {
        Auth_Token = auth_Token;
    }

    public Date getCreate_Time() {
        return create_Time;
    }

    public void setCreate_Time(Date create_Time) {
        this.create_Time = create_Time;
    }

    public Date getExpire_Time() {
        return expire_Time;
    }

    public void setExpire_Time(Date expire_Time) {
        this.expire_Time = expire_Time;
    }
}
