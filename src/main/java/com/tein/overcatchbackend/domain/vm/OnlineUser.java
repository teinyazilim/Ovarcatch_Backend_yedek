package com.tein.overcatchbackend.domain.vm;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("OnlineUser")
public class OnlineUser implements Serializable {


    private String email;
    private String status;
    private String time;

    public OnlineUser() {
    }

    public OnlineUser(String email, String status, String time) {

        this.email = email;
        this.status = status;
        this.time = time;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "OnlineUser{" +
            "email='" + email + '\'' +
            ", status='" + status + '\'' +
            ", time='" + time + '\'' +
            '}';
    }
}
