package org.AsimAnsari.service;

import org.AsimAnsari.model.User;
import org.AsimAnsari.repo.UserDAO;
import org.AsimAnsari.views.UserView;

import java.sql.SQLException;
import java.util.Scanner;

public class LoginService {
    Scanner sc;

    public LoginService() {
        this.sc = new Scanner(System.in);
    }

    public void signup(String email){
        System.out.println("Enter name : ");
        String name = sc.nextLine();
        boolean passUnmatched = true;
        String password;
        do {
            System.out.println("Set password : ");
            password = sc.nextLine();
            System.out.println("Confirm password : ");
            String confirmPass = sc.nextLine();
            if(confirmPass.equals(password))
                passUnmatched = false;
            else
                System.out.println("Not confirmed set again...");
        }while(passUnmatched);

        Integer response = UserService.saveUser(new User(name, email, Encryption.encrypt(password)));
        switch (response){
            case 0 -> System.out.println("User already exist");
            case 1 -> System.out.println("User registered");
            default -> System.out.println("Unexpected value: " + response);
        }
    }


    public void login(String email) throws SQLException {
        User user = UserDAO.getUserByEmail(email);
        System.out.println("Enter password : ");
        String password = sc.nextLine();
        if (Encryption.decrypt(password, user.password()))
            new UserView(user).home();
        else
            System.out.println("Unauthorised user");
    }
}
