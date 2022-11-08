package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import model.User;

public class SocketHandler implements Runnable {

    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private Socket s = null;
    private User user;

    @Override
    public void run() {
        try {
            s = new Socket("localhost", 2308);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            String msg = "";
            while (true) {
                // Nhan message tu Server gui ve
                msg = dis.readUTF();
                String[] s = msg.split(",");
                System.out.println(msg);

                // Login thanh cong
                if (msg.startsWith("success")) {
                    Client.openView(Client.View.HOMEPAGE, s[1]);
                    Client.closeView(Client.View.LOGIN);
                }

                // Login that bai
                if (msg.equals("fail")) {
                    Client.loginForm.show("Tai khoan hoac mat khau khong chinh xac!!!");
                }

                // Xu li da dang nhap o noi khac
                if (msg.equals("isOnline")) {
                    Client.loginForm.show("Tai khoan da duoc dang nhap o mot noi khac");
                }

                // Xu li khi nhan duoc yeu cau tao phong
                if (msg.startsWith("createRoom")) {
                    System.out.println(msg);

                    Client.openView(Client.View.GAMECLIENT, s[1]);
                    Client.gameClient.setDanhtruoc(1);
                    Client.gameClient.addButtonAction();
                    Client.closeView(Client.View.CREATEROOM);
                }

                // Xu li khi co mot Client khac va phong
                if (msg.startsWith("join")) { // cai nay chi de set ten doi thu cua ban
                    {
                        Client.gameClient.getjLabel1().setText("doi thu:" + s[1]);
                    }
                }

                // Xu li vao phong
                if (msg.startsWith("vao")) { // khi vao thanh cong thi se mo phong gameClient ra, close cai JoinRoom
                    Client.openView(Client.View.GAMECLIENT, s[2]);
                    Client.closeView(Client.View.JOINROOM);
                    Client.gameClient.setDanhtruoc(0);
                    Client.gameClient.addButtonAction();
                    Client.gameClient.getjLabel1().setText("doi thu:" + s[1]);
                }

                // Xu li khi dang xuat
                if (msg.equals("dangxuat")) {
                    Client.closeView(Client.View.HOMEPAGE);
                    Client.openView(Client.View.LOGIN, "");
                }

                // Xu li chat trong phong
                if (s[0].equals("chat")) {
                    System.out.println(s[1]);
                    Client.gameClient.getjTextArea1().setText(Client.gameClient.getjTextArea1().getText()
                            + "\n" + s[1] + ":" + s[2]);
                }

                // Xu li danh co trong phong
                if (s[0].equals("caro")) {
                    int x = Integer.parseInt(s[1]);
                    int y = Integer.parseInt(s[2]);
                    String st = "";
                    if (Client.gameClient.getValue().equals("X")) {
                        st = "O";
                        Client.gameClient.setNumberofO(Client.gameClient.getNumberofO() + 1);
                    } else {
                        Client.gameClient.setNumberOfX(Client.gameClient.getNumberOfX() + 1);
                        st = "X";
                    }
                    Client.gameClient.getButton()[x][y].setText(st);
                    Client.gameClient.setDaDanh(x, y);
                    System.out.println("X: " + Client.gameClient.getNumberOfX());
                    System.out.println("O: " + Client.gameClient.getNumberofO());
                }

                // Xu li khi mot trong hai chien thang
                if (s[0].equals("win")) {
                    Client.gameClient.show("Doi thu da thang ban");
                    Client.gameClient.setNewGame();
                }
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void write(String msg) throws IOException {
        dos.writeUTF(msg);
    }

}
