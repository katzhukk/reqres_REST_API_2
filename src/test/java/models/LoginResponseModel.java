package models;

public class LoginResponseModel {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    //        String regData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";
    String token;
}
