package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import view.Admin;

public class Server {
    public static Vector<ClientHandler> ar = new Vector<>();
    public static Admin admin;

    public static void main(String[] args) throws Exception{

        ServerSocket server = new ServerSocket(2308);
        System.out.println("Server is running");
        admin = new Admin();
        admin.setVisible(true);
        int i=0;
        while (true) {
            Socket s = server.accept();
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            ClientHandler clh = new ClientHandler("client"+i,s);
            ar.add(clh);
            clh.start();
            i++;
        }
    }
}
