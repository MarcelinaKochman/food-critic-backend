package com.wfiis.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import com.wfiis.validation.PasswordMatches;
import com.wfiis.validation.ValidPassword;

@PasswordMatches
public class User {
    @Id
    private String id;

    @NotNull
    @NotEmpty
    private String login;

    @NotNull
    @NotEmpty
    @ValidPassword
    private String password;
    private String matchingPassword;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
