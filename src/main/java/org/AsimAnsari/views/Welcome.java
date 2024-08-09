package org.AsimAnsari.views;

import org.AsimAnsari.repo.UserDAO;
import org.AsimAnsari.model.User;
import org.AsimAnsari.service.GenerateOTP;
import org.AsimAnsari.service.SendOTPService;
import org.AsimAnsari.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;


public class Welcome {
    UserView userView;

//    public Welcome() {
//        this.userView = new UserView();
//    }

    public void welcomeScreen()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Press 1 to login \nPress 2 to signup \nPress 0 to exit");
        System.out.print("Option: ");
        int choice = 0;
        try {
            choice = Integer.parseInt(br.readLine());
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        switch (choice)
        {
            case 1 -> login();
            case 2 -> signup();
            case 0 -> System.exit(0);
            default -> System.out.println("Incorrect option!");
        }
    }

    private void signup() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter email : ");
        String email = sc.nextLine();
        String genOTP = GenerateOTP.getOTP();
        SendOTPService.sendOTP(email, genOTP);
        System.out.print("Enter OTP : ");
        String OTP = sc.nextLine();
        if(OTP.equals(genOTP))
        {
            System.out.println("Enter name : ");
            String name = sc.nextLine();
            Integer response = UserService.saveUser(new User(name, email));
            switch (response){
                case 0 -> System.out.println("User already exist");
                case 1 -> System.out.println("User registered");
                default -> System.out.println("Unexpected value: " + response);
            }
        }
        else
            System.out.println("Wrong OTP");
    }

    private void login() {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter email : ");
        String email = sc.nextLine();
        try
        {
            if (UserDAO.isExist(email))
            {
                String genOTP = GenerateOTP.getOTP();
                SendOTPService.sendOTP(email,genOTP);
                System.out.print("Enter OTP : ");
                String OTP = sc.nextLine();
                if (OTP.equals(genOTP))
                    new UserView(UserDAO.getUserByEmail(email)).home();
                else
                    System.out.println("Wrong OTP");
            }
            else
            {
                System.out.println("User not found");
            }

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        
    }
}
