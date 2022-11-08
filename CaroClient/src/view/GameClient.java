package view;

import controller.Client;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class GameClient extends javax.swing.JFrame {

    /**
     * Creates new form GameClient
     */
    private String value = "";
    private int[][] arr;
    private int numberOfX = 0;
    private int numberofO = 0;
    private int danhtruoc = 0;
    private String username;

    public GameClient(String username) {
        initComponents();
        name.setText(username);
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jPanel1.setLayout(new GridLayout(size, size));
        button = new JButton[size][size];
        arr = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                arr[i][j] = 0;
                button[i][j] = new JButton("");
                button[i][j].setBackground(Color.white);
                jPanel1.add(button[i][j]);
            }
        }
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    Client.socketHandler.write("dangxuat," + name.getText());
                } catch (IOException ex) {
                    Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
    }

    public void addButtonAction() {
        if (danhtruoc == 1) {
            value = "X";
        } else {
            value = "O";
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final int a = i, b = j;
                button[i][j].addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        addActionEvent(evt, a, b);
                    }

                    private void addActionEvent(ActionEvent evt, int a, int b) {
                        if (arr[a][b] == 0) {
                            if (check()) {
                                try {
                                    String[] s = jLabel1.getText().split(":");
                                    Client.socketHandler.write("caro," + a + "," + b + "," + s[1]);
                                } catch (IOException ex) {
                                    Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                button[a][b].setText(value);
                                button[a][b].setFont(new java.awt.Font("Segoe UI", 1, 12));
                                if (value.equals("X")) {
                                    numberOfX++;
                                } else {
                                    numberofO++;
                                }
                                System.out.println("X: " + Client.gameClient.getNumberOfX());
                                System.out.println("O: " + Client.gameClient.getNumberofO());
                                arr[a][b] = 1;
                                if (checkWin(button, a, b)) {
                                    JOptionPane.showMessageDialog(rootPane, "Win");
                                    setNewGame();
                                    try {
                                        String s[] = jLabel1.getText().split(":");
                                        Client.socketHandler.write("win," + s[1] + "," + name.getText());
                                    } catch (IOException ex) {
                                        Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                            else {
                                show("cho luot danh cua doi thu");
                            }
                        } else {
                            show("khong danh vao o nay");
                        }
                    }
                });
            }
        }
    }

    private boolean check() {
        if (danhtruoc == 1) {
            if (numberOfX != numberofO) {
                return false;
            }
        }
        if (danhtruoc == 0) {
            if ((numberOfX - numberofO) != 1) {
                return false;
            }
        }
        return true;
    }

    public void setNewGame() {
        danhtruoc = (danhtruoc == 1) ? 0 : 1;
        if (danhtruoc == 1) value = "X";
        else value = "O";
        numberOfX = 0;
        numberofO = 0;
        if (danhtruoc ==1) {
            show("Ban danh truoc");
        }
        else show("Doi thu danh truoc");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                button[i][j].setText("");
                arr[i][j] = 0;
            }
        }
    }

    public void setDaDanh(int x, int y) {
        arr[x][y] = 1;
    }

    public void show(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public boolean checkWin(JButton b[][], int i, int j) {
        int d = 0, k = i, h;
        // kiểm tra hàng
        while (b[k][j].getText() == b[i][j].getText()) {
            d++;
            k++;
        }
        k = i - 1;
        while (b[k][j].getText() == b[i][j].getText()) {
            d++;
            k--;
        }
        if (d > 4) {
            return true;
        }
        d = 0;
        h = j;
        // kiểm tra cột
        while (b[i][h].getText() == b[i][j].getText()) {
            d++;
            h++;
        }
        h = j - 1;
        while (b[i][h].getText() == b[i][j].getText()) {
            d++;
            h--;
        }
        if (d > 4) {
            return true;
        }
        // kiểm tra đường chéo 1
        h = i;
        k = j;
        d = 0;
        while (b[i][j].getText() == b[h][k].getText()) {
            d++;
            h++;
            k++;
        }
        h = i - 1;
        k = j - 1;
        while (b[i][j].getText() == b[h][k].getText()) {
            d++;
            h--;
            k--;
        }
        if (d > 4) {
            return true;
        }
        // kiểm tra đường chéo 2
        h = i;
        k = j;
        d = 0;
        while (b[i][j].getText() == b[h][k].getText()) {
            d++;
            h++;
            k--;
        }
        h = i - 1;
        k = j + 1;
        while (b[i][j].getText() == b[h][k].getText()) {
            d++;
            h--;
            k++;
        }
        if (d > 4) {
            return true;
        }
        // nếu không đương chéo nào thỏa mãn thì trả về false.
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        name = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        name.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        name.setText("chu phong");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 562, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );

        jButton1.setText("BACK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Send");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton3.setText("Ket ban");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(name)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3))))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(name)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addGap(83, 83, 83)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jButton3)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Client.closeView(Client.View.GAMECLIENT);
        Client.closeView(Client.View.LOGIN);
        Client.openView(Client.View.HOMEPAGE, name.getText());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try {
            String[] s = jLabel1.getText().split(":");
            Client.socketHandler.write("chat," + jTextField1.getText() + "," + s[1]);
            jTextArea1.setText(jTextArea1.getText() + "\nyou:" + jTextField1.getText());
            jTextField1.setText("");
        } catch (IOException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameClient("abc").setVisible(true);
            }
        });
    }

    private JButton[][] button;

    public JLabel getjLabel1() {
        return jLabel1;
    }

    public void setjLabel1(JLabel jLabel1) {
        this.jLabel1 = jLabel1;
    }

    public JTextArea getjTextArea1() {
        return jTextArea1;
    }

    public void setjTextArea1(JTextArea jTextArea1) {
        this.jTextArea1 = jTextArea1;
    }

    public JButton[][] getButton() {
        return button;
    }

    public void setButton(JButton[][] button) {
        this.button = button;
    }

    public int getDanhtruoc() {
        return danhtruoc;
    }

    public void setDanhtruoc(int danhtruoc) {
        this.danhtruoc = danhtruoc;
    }

    public int getNumberOfX() {
        return numberOfX;
    }

    public void setNumberOfX(int numberOfX) {
        this.numberOfX = numberOfX;
    }

    public int getNumberofO() {
        return numberofO;
    }

    public void setNumberofO(int numberofO) {
        this.numberofO = numberofO;
    }

    private static int size = 15;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel name;
    // End of variables declaration//GEN-END:variables
}
