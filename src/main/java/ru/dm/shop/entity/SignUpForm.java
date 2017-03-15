package ru.dm.shop.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import ru.dm.shop.validator.FieldEquals;
import ru.dm.shop.validator.UniqueEmail;
import ru.dm.shop.validator.UniqueUsername;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@FieldEquals(field="password", equalsTo="confirmPassword")
public class SignUpForm {
    @Size(min = 3, max = 40, message = "Логин должен иметь от 3 до 40 символов")
    @NotEmpty(message = "Введите логин")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9-._]*$", message = "Логин должен начинаться с буквы и содержать только латинские символы, цифры и символы \'.\', \'_\', \'-\'")
    @UniqueUsername
    private String username;

    @Email(regexp = ".+@.+", message = "Введите корректный email")
    @NotEmpty(message = "Введите email")
    @UniqueEmail
    private String email;

    @Size(min = 6, max = 20, message = "Пароль должен иметь от 6 до 20 символов")
    @NotEmpty(message = "Введите пароль")
    private String password;

    @Size(min = 6, max = 20, message = "Пароль должен иметь от 6 до 20 символов")
    @NotEmpty(message = "Подтвердите пароль")
    private String confirmPassword;

    public String getRole() {
        return "ROLE_USER";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}