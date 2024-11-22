package tests;

import models.lombok.LoginBodyLombokModel;
import models.lombok.LoginResponseLombokModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static specs.RegistrationSpec.*;


public class ReqresLombokTests extends TestBase {

    @DisplayName("Запрос на регистрацию пользователя. POST - SUCCESSFUL REGISTRATION")
    @Test
    void successfulRegistrationTest() {

        LoginBodyLombokModel regData = new LoginBodyLombokModel();

        regData.setEmail("eve.holt@reqres.in");
        regData.setPassword("pistol");

        LoginResponseLombokModel responce = step("Отправляем запрос на регистрацию пользователя", () ->
            given(registrationRequestSpec)
                    .body(regData)

                .when()
                    .post("/register")

                .then()
                    .spec(responseSpec200)
                    .extract().as(LoginResponseLombokModel.class));

        step("Проверяем успешную регистрацию пользователя", () ->
                assertThat(responce.getToken()).isAlphanumeric());
    }

    @DisplayName("Запрос на попытку регистрации без пароля. POST - UNSUCCESSFUL REGISTRATION")
    @Test
    void unsuccessfulRegistrationWithoutPasswordTest() {

        String regData = "{\"email\": \"eve.holt@reqres.in\"}";

        given(registrationRequestSpec)
                .body(regData)

            .when()
                .post("/register")

            .then()
                .spec(responseSpec400);
    }

    @DisplayName("Запрос на изменение данных пользователя. POST - CREATE")
    @Test
    void successfulCreateTest() {

        String createData = "{\"name\": \"marpheus\", \"job\": \"leader\"}";

        given()
                .spec(createRequestSpec)
                .body(createData)

            .when()
                .post("/users")

            .then()
                .spec(responseSpec201)
                .body("job", is("leader"));
    }

    @DisplayName("Запрос на поиск пользователя по id. GET - SINGLE USER")
    @Test
    void successfulFoundUserTest() {

        given(foundRequestSpec)

            .when()
                .get("/users/7")

            .then()
                .spec(responseSpec200)
                .body("data.id", is(7))
                .body("data.first_name", is("Michael"))
                .body("data.last_name", is("Lawson"));
    }

    @DisplayName("Запрос на поиск несуществующего id пользователя. GET - SINGLE USER NOT FOUND")
    @Test
    void unsuccessfulFoundUserTest() {

        given(foundRequestSpec)
            .when()
                .get("/users/666")

            .then()
                .spec(responseSpec404);
    }
}
