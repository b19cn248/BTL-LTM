package controller;

import view.CreateRoom;
import view.GameClient;
import view.LoginForm;
import view.HomePage;
import view.JoinRoom;

/**
 *
 * @author Hieu
 */
public class Client {

    public enum View {
        LOGIN,
        HOMEPAGE,
        GAMECLIENT,
        CREATEROOM,
        JOINROOM,
    }

    public static LoginForm loginForm;
    public static HomePage homePage;
    public static GameClient gameClient;
    public static CreateRoom createRoom;
    public static JoinRoom joinRoom;
    public static SocketHandler socketHandler;

    private static void initGameClient(String username) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        gameClient = new GameClient(username);
        gameClient.setVisible(true);
    }

    public static void openView(View viewName, String username) {
        switch (viewName) {
            case LOGIN:
                loginForm = new LoginForm();
                loginForm.setVisible(true);
                break;
            case HOMEPAGE:
                homePage = new HomePage(username);
                homePage.setVisible(true);
                break;
            case GAMECLIENT:
                initGameClient(username);
                break;
            case CREATEROOM:
                createRoom = new CreateRoom(username);
                createRoom.setVisible(true);
                break;
             case JOINROOM:
                joinRoom = new JoinRoom(username);
                joinRoom.setVisible(true);
                break;
            default:
                System.out.println("Het roi em oi");
        }
    }

    public static void closeView(View viewName) {
        if (viewName != null) {
            switch (viewName) {
                case LOGIN:
                    loginForm.dispose();
                    break;
                case HOMEPAGE:
                    homePage.dispose();
                    break;
                case GAMECLIENT:
                    gameClient.dispose();
                    loginForm = new LoginForm();
                    loginForm.setVisible(true);
                    break;
                case CREATEROOM:
                    createRoom.dispose();
                    break;
                case JOINROOM:
                    joinRoom.dispose();
                    break;
            }
        }
    }

    public void initView() {

        loginForm = new LoginForm();
        loginForm.setVisible(true);
        socketHandler = new SocketHandler();
        Thread t = new Thread(socketHandler);
        t.start();
    }

    public static void main(String[] args) {
        new Client().initView();
    }
}
