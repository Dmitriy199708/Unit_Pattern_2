package ru.netology.selenium.patterns.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.selenium.patterns.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.selenium.patterns.data.DataGenerator.Registration.getUser;
import static ru.netology.selenium.patterns.data.DataGenerator.getRandomLogin;
import static ru.netology.selenium.patterns.data.DataGenerator.getRandomPassword;

public class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Successful user registration")
    public void successfulUserRegistration() {
        var registeredUser = getRegisteredUser("active");
        $x("//span[@data-test-id='login'] //input").setValue(registeredUser.getLogin());
        $x("//span[@data-test-id='password'] //input").setValue(registeredUser.getPassword());
        $x("//button[@data-test-id='action-login'] ").click();
        $x("//h2")
                .shouldBe(Condition.visible)
                .shouldBe(Condition.exactText("Личный кабинет"));
    }

    @Test
    @DisplayName("User login without password")
    public void userLoginWithoutPassword() {
        var registeredUser = getRegisteredUser("active");
        $x("//span[@data-test-id='login'] //input").setValue(registeredUser.getLogin());
        $x("//button[@data-test-id='action-login'] ").click();
        $x("//span[@data-test-id='password'] //span[@class='input__sub']")
                .shouldBe(Condition.visible)
                .shouldBe(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("User login without login")
    public void userLoginWithoutLogin() {
        var registeredUser = getRegisteredUser("active");
        $x("//span[@data-test-id='password'] //input").setValue(registeredUser.getPassword());
        $x("//button[@data-test-id='action-login'] ").click();
        $x("//span[@data-test-id='login'] //span[@class='input__sub']")
                .shouldBe(Condition.visible)
                .shouldBe(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Login without login and password")
    public void loginWithoutLoginAndPassword() {
        $x("//button[@data-test-id='action-login'] ").click();
        $x("//span[@data-test-id='password'] //span[@class='input__sub']")
                .shouldBe(Condition.visible)
                .shouldBe(Condition.exactText("Поле обязательно для заполнения"));
        $x("//span[@data-test-id='login'] //span[@class='input__sub']")
                .shouldBe(Condition.visible)
                .shouldBe(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Login with a blocked account")
    public void loginWithBlockedAccount() {
        var registeredUser = getRegisteredUser("blocked");
        $x("//span[@data-test-id='login'] //input").setValue(registeredUser.getLogin());
        $x("//span[@data-test-id='password'] //input").setValue(registeredUser.getPassword());
        $x("//button[@data-test-id='action-login'] ").click();
        $x("//div[@data-test-id='error-notification'] //div[@class='notification__content']")
                .shouldBe(Condition.text("Пользователь заблокирован"), Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Login as a random user")
    public void loginAsRandomUser() {
        var notRegisteredUser = getUser("active");
        $x("//span[@data-test-id='login'] //input").setValue(notRegisteredUser.getLogin());
        $x("//span[@data-test-id='password'] //input").setValue(notRegisteredUser.getPassword());
        $x("//button[@data-test-id='action-login'] ").click();
        $x("//div[@data-test-id='error-notification'] //div[@class='notification__content']")
                .shouldBe(Condition.exactText("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Login with a random login")
    public void loginWithRandomLogin() {
        var RegisteredUser = getRegisteredUser("active");
        var randomLogin = getRandomLogin();
        $x("//span[@data-test-id='login'] //input").setValue(randomLogin);
        $x("//span[@data-test-id='password'] //input").setValue(RegisteredUser.getPassword());
        $x("//button[@data-test-id='action-login'] ").click();
        $x("//div[@data-test-id='error-notification'] //div[@class='notification__content']")
                .shouldBe(Condition.exactText("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Login with a random login")
    public void loginWithRandomPassword() {
        var RegisteredUser = getRegisteredUser("active");
        var randomPassword = getRandomPassword();
        $x("//span[@data-test-id='login'] //input").setValue(RegisteredUser.getLogin());
        $x("//span[@data-test-id='password'] //input").setValue(randomPassword);
        $x("//button[@data-test-id='action-login'] ").click();
        $x("//div[@data-test-id='error-notification'] //div[@class='notification__content']")
                .shouldBe(Condition.exactText("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
    }


}
