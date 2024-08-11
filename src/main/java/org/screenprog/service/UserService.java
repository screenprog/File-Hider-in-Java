package org.screenprog.service;

import org.screenprog.repo.UserDAO;
import org.screenprog.model.User;

import java.sql.SQLException;

public class UserService {
    public static Integer saveUser(User user)
    {
        try
        {
            if(UserDAO.isExist(user.email()))
                return 0;
            else
                return UserDAO.saveUser(user);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}
