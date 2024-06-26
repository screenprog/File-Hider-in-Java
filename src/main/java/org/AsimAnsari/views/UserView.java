package org.AsimAnsari.views;

import org.AsimAnsari.dao.DataDAO;
import org.AsimAnsari.model.Data;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    String email;
    boolean logedIn;
    public UserView(String email)
    {
        this.logedIn = true;
        this.email = email;
    }

    public void home() {
        do {
            System.out.println("Welcome "+this.email);
            System.out.println("Press 1 to show hidden files");
            System.out.println("Press 2 to hide a new file");
            System.out.println("Press 3 to un-hide a file");
            System.out.println("Press 4 to logout");
            System.out.println("Press 0 to exit");
            Scanner sc = new Scanner(System.in);
            int ch = Integer.parseInt(sc.nextLine());
            switch(ch)
            {
                case 0 -> System.exit(0);
                case 1 -> {
                    try {
                        List<Data> files = DataDAO.getAllFiles(this.email);
                        System.out.println("ID\t |\t File Name");
                        for (Data file : files)
                            System.out.println(file.id() +"\t |\t "+ file.fileName());

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 2 -> {
                    System.out.println("Enter file path : ");
                    String filePath = sc.nextLine();
                    File f = new File(filePath);
                    Data file = new Data(f.getName(), filePath, this.email);
                    try
                    {
                        int response = DataDAO.hideFile(file);
                    }
                    catch (SQLException |IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
                case 3 -> {
                    try {
                        List<Data> files = DataDAO.getAllFiles(this.email);
                        System.out.println("ID\t |\t File Name");
                        for (Data file : files)
                            System.out.println(file.id() +"\t |\t "+ file.fileName());
                        System.out.println("Enter the id of file to un-hide");
                        int id = sc.nextInt();
                        boolean isValidID = false;
                        for(Data file : files)
                            if(file.id() == id){
                                isValidID = true;
                                break;
                            }
                        if(isValidID)
                            DataDAO.un_hide(id);
                        else
                            System.out.println("Wrong id");
                    } catch (SQLException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 4 -> logedIn = false;
                default -> System.out.println("Wrong option...!\n");
            }
        }while(logedIn);


    }
}
