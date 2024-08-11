package org.screenprog.views;

import org.screenprog.repo.UserDAO;
import org.screenprog.service.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;


public class Welcome {
   LoginService loginService;
    Scanner sc;

    public Welcome() {
        this.loginService = new LoginService();
        this.sc = new Scanner(System.in);
    }

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
        System.out.println("Enter email : ");
        String email = sc.nextLine();
        String genOTP = GenerateOTP.getOTP();
        SendOTPService.sendOTP(email, genOTP);
        System.out.print("Enter OTP : ");
        String OTP = sc.nextLine();
        if(OTP.equals(genOTP))
        {
            loginService.signup(email);
        }
        else
            System.out.println("Wrong OTP");
    }

    private void login() {
        System.out.print("Enter email : ");
        String email = sc.nextLine();
        try
        {
            if (UserDAO.isExist(email))
            {
                loginService.login(email);
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
