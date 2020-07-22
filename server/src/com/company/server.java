package com.company;

import java.net.*;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class server {

    public static void main(String[] args) throws IOException, SQLException {
        //getting data from database
        Map<String, User> data = getDataFromServer();
        //connecting to client through port 4999
        try {
            ServerSocket ss = new ServerSocket(4999);
            Socket socket = ss.accept();
            System.out.println("Client is connected");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            while (socket.isClosed() == false) {
            /*
            getting the id and password data from the client
             */
                //creating data input stream
                DataInputStream data_stream = new DataInputStream(socket.getInputStream());
                //read data from the socket
                if (!socket.isOutputShutdown()) {
                    String id = data_stream.readUTF();
                    String pwrd = data_stream.readUTF();
                    //id and password authentication
                    User user = authenticateData(data, id, pwrd);
                    if (user == null) {
                        System.out.println("No such user.");
                    } else {
                        objectOutputStream.writeObject(user);
                    }
                }
            }
        } catch (SocketException se) {
            System.out.println("No connections.");
        }

    }

    public static Map<String, User> getDataFromServer() throws SQLException {
        MysqlDB db = new MysqlDB();
        Map<String, User> resMap = new HashMap<String, User>();
        Connection conn = db.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from users");
        while (rs.next()) {
            User user = new User(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4), rs.getString(5));
            resMap.put(rs.getString(1), user);
        }
        return resMap;
    }

    public static User authenticateData(Map<String, User> data, String id, String pwrd) {
        User user = data.get(id);
        if (user != null) {
            return (user.getPassword().equals(pwrd)) ? user : null;
        }
        return null;
    }
}
