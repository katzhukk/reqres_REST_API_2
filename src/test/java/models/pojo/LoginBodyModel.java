package models.pojo;

public class LoginBodyModel {
//        String regData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";

    public String getEmail() {
        return email;
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

    String email, password;

}
