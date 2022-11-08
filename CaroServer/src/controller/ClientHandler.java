package controller;

import DAO.UserDAO;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import model.User;
import view.Admin;

public class ClientHandler extends Thread {

    private String nameofThread;
    private Socket s;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private Room room;

    public ClientHandler() {
    }

    public ClientHandler(String nameofThread, Socket s) {
        this.nameofThread = nameofThread;
        this.s = s;
        room = new Room();
    }

    public void run() {
        try {
            while (true) {
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());

                // Lay msg tu Client gui len
                String msg = dis.readUTF();
                String[] s = msg.split(",");
                
                // Xu li Login
                if (s[0].equals("login")) {
                    UserDAO dao = new UserDAO();
                    User user = dao.check(s[1],s[2]);
                    if (user != null ) {
                        if (dao.checkOnline(user)) {
                            this.setNameofThread(user.getUsername());
                            System.out.println(this.getNameofThread()+" da login thanh cong");
                            String online = this.getNameofThread()+" da Online";
                            Server.admin.getjTextArea2().setText(Server.admin.getjTextArea2().getText()+"\n"+online);
                            dao.updateOnline(user.getId());
                            dos.writeUTF("success," + user.getUsername());
                        }
                        else dos.writeUTF("isOnline");
                    } else {
                        System.out.println("fail");
                        dos.writeUTF("fail");
                    }
                }

                
                // Xu li tao phong
                if (s[0].equals("taophong")) { // tao mot phong moi
                    room.setId(Integer.parseInt(s[1]));
                    room.setPassword(s[2]);
                    dos.writeUTF("createRoom," + s[3]); 
                    System.out.println(this.getNameofThread()+" dat tao phong voi ID la "+s[1] + " va mat khau la " + s[2]);
                }

                
                // Xu li vao phong
                if (s[0].equals("vaophong")) {
                    int ID = Integer.parseInt(s[1]);
                    String pass = s[2];
                    System.out.println(this.getNameofThread()+" da va phong "+ ID);
                    for (ClientHandler x : Server.ar) {
                        if (x.getRoom().getId() == ID && x.getRoom().getPassword().equals(pass)) {
                            dos.writeUTF("vaothanhcong,"+x.getNameofThread()+","+s[3]);// ghi vao luong da gui len la 
                                                    //vao thanh cong va lay ten cua doi thu
                            x.dos.writeUTF("join," + this.getNameofThread());// ghi vao luong cho doi thu biet la minh da vao
                        }
                    }
                }

                
                // Xu li chat trong phong
                if (s[0].equals("chat")) {
                    for (ClientHandler x : Server.ar) {
                        if (x.getNameofThread().equals(s[2])) {
                            System.out.println(s[2]);
                            x.dos.writeUTF("chat,"+ this.getNameofThread() +","+s[1]);
                        }
                    }
                }
                
                // Xu li dang xuat
                if (s[0].equals("dangxuat")) {
                    UserDAO dao = new UserDAO();
                    User user = dao.find(s[1]);
                    dao.updateOffline(user.getId());
                    dos.writeUTF("dangxuat");
                }
                
                // Xu li danh co caro trong phong
                if (s[0].equals("caro")) {
                    for (ClientHandler x : Server.ar) {
                        if (x.getNameofThread().equals(s[3])) {
                            System.out.println(s[2]);
                            x.dos.writeUTF("caro,"+ s[1] +","+ s[2] );
                        }
                    }
                }
                
                
                // Xu li khi thang 
                if (s[0].equals("win")) {
                    UserDAO dao = new UserDAO();
                    dao.updateWin(s[2]);
                    for (ClientHandler x : Server.ar) {
                        if (x.getNameofThread().equals(s[1])) {
                            x.dos.writeUTF("win");
                            dao.updateLose(s[1]);
                        }
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getNameofThread() {
        return nameofThread;
    }

    public void setNameofThread(String nameofThread) {
        this.nameofThread = nameofThread;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

}
