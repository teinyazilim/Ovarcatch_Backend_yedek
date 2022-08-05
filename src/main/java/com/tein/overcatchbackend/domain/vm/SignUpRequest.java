package com.tein.overcatchbackend.domain.vm;

//import javax.validation.constraints.*;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */

public class SignUpRequest {
    //@NotBlank
    //@Size(min = 4, max = 40)
    private String name;

    private String surname;

    private String payment;

    private String email;

    private String msisdn;
    //@NotBlank
    //@Size(min = 6, max = 20)
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}