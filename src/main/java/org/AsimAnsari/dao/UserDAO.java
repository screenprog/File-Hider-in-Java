package org.AsimAnsari.dao;

import org.AsimAnsari.db.MyConnection;
import org.AsimAnsari.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static boolean isExist(String email) throws SQLException
    {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("select email from users");
        ResultSet rs = ps.executeQuery();
        while( rs.next() )
        {
            if(email.equals(rs.getString(1)))
                return true;
        }
        return false;
    }

    public static int saveUser(User user) throws SQLException
    {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("insert into users values (default, ?, ?)");
        ps.setString(1, user.name());
        ps.setString(2, user.email());
        return ps.executeUpdate();
    }
}
