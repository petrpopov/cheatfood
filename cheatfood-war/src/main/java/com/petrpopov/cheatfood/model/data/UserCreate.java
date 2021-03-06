package com.petrpopov.cheatfood.model.data;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * User: petrpopov
 * Date: 16.07.13
 * Time: 15:58
 */

@FieldMatch.List({
        @FieldMatch(first = "password", second = "passwordRepeat", message = "The password fields must match")
})
public class UserCreate {

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    @NotEmpty
    @NotNull
    private String passwordRepeat;

    public UserCreate() {
    }

    public UserCreate(String email, String password) {
        this.email = email;
        this.password = password;
    }

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

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCreate that = (UserCreate) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }
}
