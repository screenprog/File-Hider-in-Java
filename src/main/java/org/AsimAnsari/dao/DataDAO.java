package org.AsimAnsari.dao;

import org.AsimAnsari.db.MyConnection;
import org.AsimAnsari.model.Data;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataDAO {
    public static List<Data> getAllFiles(String email) throws SQLException
    {
        List<Data> files = new ArrayList<>();
        Connection con = MyConnection.getConnection();
        PreparedStatement pst = con.prepareStatement("select * from data where email = ?");
        pst.setString(1,email);
        ResultSet resultSet = pst.executeQuery();
        while(resultSet.next())
        {
            int id = Integer.parseInt(resultSet.getString(1));
            String fileName = resultSet.getString(2);
            String path = resultSet.getString(3);
            files.add(new Data(id,fileName,path));

        }

        return files;
    }

    public static int hideFile(Data file) throws SQLException, IOException
    {
        Connection connection = MyConnection.getConnection();
        PreparedStatement pst = connection.prepareStatement(
                "insert into data(name, path, email, bin_data) values(?, ?, ?, ?)"
        );
        pst.setString(1,file.fileName());
        pst.setString(2,file.path());
        pst.setString(3, file.email());
        File f = new File(file.path());
        FileReader fr = new FileReader(file.path());
        pst.setCharacterStream(4, fr, f.length());
        int ans = pst.executeUpdate();
        fr.close();
        boolean b = f.delete();
        return ans;
    }

    public static void un_hide(int id) throws SQLException, IOException
    {
        Connection connection = MyConnection.getConnection();
        PreparedStatement pst = connection.prepareStatement("select path, bin_data from data where id = ?");
        pst.setInt(1,id);
        ResultSet rs = pst.executeQuery();
        rs.next();
        String path = rs.getString("path");
        Clob c = rs.getClob("bin_data");
        Reader r = c.getCharacterStream();
        FileWriter fw = new FileWriter(path);
        int i;
        while((i = r.read()) != -1)
        {
            fw.write((char)i);
        }
        fw.close();
        pst = connection.prepareStatement("delete from data where id = ?");
        pst.setInt(1,id);
        pst.executeUpdate();
        System.out.println("Successfully unhidden");
    }
}
