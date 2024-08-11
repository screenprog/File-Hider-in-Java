package org.AsimAnsari.repo;

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
        PreparedStatement ps = con.prepareStatement("SELECT email FROM users");
        ResultSet rs = ps.executeQuery();
        while( rs.next() )
            if(email.equals(rs.getString(1)))
                return true;

        return false;
    }

    public static int saveUser(User user) throws SQLException
    {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO users VALUES (default, ?, ?, ?)");
        ps.setString(1, user.name());
        ps.setString(2, user.email());
        ps.setString(3, user.password());
        return ps.executeUpdate();
    }

    public static User getUserByEmail(String email) throws SQLException {
        Connection con = MyConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM users where email = ?");
        ps.setString(1,email);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return new User(rs.getString(2), rs.getString(3), rs.getString(4));
    }
}
