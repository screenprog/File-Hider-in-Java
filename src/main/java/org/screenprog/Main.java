package org.screenprog;

import org.screenprog.views.Welcome;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to app");
        Welcome welcome = new Welcome();
        do {
            welcome.welcomeScreen();
        } while (true);
    }
}