package tests;

import models.lombok.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static specs.RegistrationSpec.*;


public class ReqresLombokTests extends TestBase {

    @DisplayName("Запрос на регистрацию пользователя. POST - SUCCESSFUL REGISTRATION")
    @Test
    void successfulRegistrationTest() {

        RegistrationBodyLombokModel regData = new RegistrationBodyLombokModel();

        regData.setEmail("eve.holt@reqres.in");
        regData.setPassword("pistol");

        RegistrationResponceLombokModel responce = step("Отправляем запрос на регистрацию пользователя", () ->
            given(registrationRequestSpec)
                    .body(regData)

                .when()
                    .post("/register")

                .then()
                    .spec(responseSpec200)
                    .extract().as(RegistrationResponceLombokModel.class));

        step("Проверяем успешную регистрацию пользователя", () ->
                assertThat(responce.getToken()).isAlphanumeric());
    }

    @DisplayName("Запрос на попытку регистрации без пароля. POST - UNSUCCESSFUL REGISTRATION")
    @Test
    void unsuccessfulRegistrationWithoutPasswordTest() {

        RegistrationBodyLombokModel regData = new RegistrationBodyLombokModel();

        regData.setEmail("eve.holt@reqres.in");

        step("Отправляем запрос на регистрации без пароля. Ожидаем статус код 400", () ->
        given(registrationRequestSpec)
                .body(regData)

            .when()
                .post("/register")

            .then()
                .spec(responseSpec400));
    }

    @DisplayName("Запрос на изменение данных пользователя. POST - CREATE")
    @Test
    void successfulCreateTest() {

        CreateBodyLombokModel createData = new CreateBodyLombokModel();

        createData.setName("marpheus");
        createData.setJob("leader");

        CreateResponceLombokModel responce = step("Отправляем запрос на изменение данных пользователя. Ожидаем статус код 201", () ->
            given()
                .spec(createRequestSpec)
                .body(createData)

            .when()
                .post("/users")

            .then()
                .spec(responseSpec201)
                .extract().as(CreateResponceLombokModel.class));

        step("Проверяем успешное изменение данных пользователя", () ->
                Assertions.assertEquals(responce.getJob(), "leader"));
    }

    @DisplayName("Запрос на поиск пользователя по id. GET - SINGLE USER")
    @Test
    void successfulFoundUserTest() {
        FoundResponceUserLombokModel responce = step("Отправляем запрос на поиск пользователя по id. Ожидаем статус код 200", () ->
        given(foundRequestSpec)

            .when()
                .get("/users/7")

            .then()
                .spec(responseSpec200)
                .extract().as(FoundResponceUserLombokModel.class));

        step("Проверяем данные найденного пользователя", () ->
                {
                    Assertions.assertEquals(responce.getData().get(0).getId(), 7);
                    Assertions.assertEquals(responce.getData().get(2).getFirst_name(), "Michael");
                    Assertions.assertEquals(responce.getData().get(3).getLast_name(), "Lawson");
                });
    }

    @DisplayName("Запрос на поиск несуществующего id пользователя. GET - SINGLE USER NOT FOUND")
    @Test
    void unsuccessfulFoundUserTest() {
        step("Отправляем запрос на поиск несуществующего id пользователя. Ожидаем статус код 404", () ->
        given(foundRequestSpec)
            .when()
                .get("/users/666")

            .then()
                .spec(responseSpec404));
    }
}
