package com.rafalpiotrowski.ssp.application.command;

public class WelcomeUserCommand {

    private String userName;

    public WelcomeUserCommand(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
