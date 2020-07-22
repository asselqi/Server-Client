package com.company;


import java.net.*;
import java.io.*;

public class client {

    public static void main(String[] args) throws IOException {
        //opening a socket for client-server communication
        try {
            Socket socket = new Socket("127.0.0.1", 4999);
            //getting ObjectInputStream
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            while (!socket.isClosed()) {
                //client inserting id and password
                System.out.println("Please insert ID and Password");
                System.out.print("ID: ");
                BufferedReader buffer_read = new BufferedReader(new InputStreamReader(System.in));
                String id = buffer_read.readLine();
                System.out.print("Password: ");
                String pwrd = buffer_read.readLine();
                //buffer_read.close();

                /*
                sending the id and password to the server through
                output streams
                 */
                //creating data output stream
                DataOutputStream data_stream = new DataOutputStream(socket.getOutputStream());
                //sending id
                data_stream.writeUTF(id);
                data_stream.flush();
                //sending password
                data_stream.writeUTF(pwrd);
                data_stream.flush();
                /*
                receiving user data from sever
                 */
                User user = (User) objectInputStream.readObject();
                System.out.println(user.toString());
            }

        } catch (SocketException se) {
            System.err.print("Server is not connected.");
        } catch (ClassNotFoundException nf) {
            System.err.print("Data read fail.");
        }
    }
}
