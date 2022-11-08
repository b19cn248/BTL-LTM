/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hieu
 */
public class User implements Serializable {

    public static final long servialVersionUID = 12L;
    private int id;
    private String username;
    private String password;
    private int isOnline;
    private int win;
    private int lose;

    public User() {
    }

    public User(int id, String username, String password, int online) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isOnline = online;
        this.win = 0;
        this.lose = 0;
    }

    public User(int id, String username, String password, int win, int lose, int isOnline) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isOnline = isOnline;
        this.win = win;
        this.lose = lose;
    }
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }
    
  

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password + ", isOnline=" + isOnline + '}';
    }

    

}
