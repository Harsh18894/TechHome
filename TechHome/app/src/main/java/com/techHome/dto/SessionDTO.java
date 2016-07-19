package com.techHome.dto;

/**
 * Created by Dell on 5/15/2016.
 */
public class SessionDTO {

    private String name;
    private String email;
    private String mobile;
    private String address;
    private String accessTokenId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccessTokenId() {
        return accessTokenId;
    }

    public void setAccessTokenId(String accessTokenId) {
        this.accessTokenId = accessTokenId;
    }

    public String toString() {
        return "SessionDTO [name = " + name + ", email = " + email + ", mobile = " + mobile + ", address = " + address + ", accessToken = " + accessTokenId + "]";
    }

}
