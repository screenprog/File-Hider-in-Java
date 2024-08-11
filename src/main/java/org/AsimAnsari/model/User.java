package org.AsimAnsari.model;

public record User(String name, String email, String password) {

    public User(String name, String email) {
        this(name, email, null);
    }
}
