package DAO;

import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class UserDAO extends DBcontext{

    public UserDAO() {
    }
    
    public User check(String username, String password) {
        String sql = "select *from users where username = ? and password = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                 return new User(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getInt(4));
            }
           
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    public User find(String username) {
        String sql = "select *from users where username = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                 return new User(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getInt(4),
                            rs.getInt(5),
                            rs.getInt(6));
            }
           
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    public boolean checkOnline(User user) {
        if (user!=null && user.getIsOnline()==0) return true;
        return false;
    }
    
    public void updateOnline(int id) {
        String sql = "update users set is_online = 1 where id=?";
         try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);            
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void updateOffline(int id) {
        String sql = "update users set is_online = 0 where id=?";
         try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);            
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void updateWin(String username) {
        UserDAO dao = new UserDAO();
        int w = dao.find(username).getWin();
        String sql = "update users set win = ? where username=?";
         try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1,w+1);
            st.setString(2, username);            
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void updateLose(String username) {
        UserDAO dao = new UserDAO();
        int l = dao.find(username).getLose();
        String sql = "update users set lose = ? where username=?";
         try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1,l+1);
            st.setString(2, username);            
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        System.out.println(dao.find("1").getLose());
        System.out.println(dao.find("1").getWin());
    }
}
