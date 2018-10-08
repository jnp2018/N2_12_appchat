/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ver4;

import com.sun.jndi.toolkit.dir.SearchFilter;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author VietAnh
 */
public class User implements Serializable{
    private String userName;
    private String image;
    private String status;
    private ArrayList<Friend> friendList;

    public User(String userName, String image, String status, ArrayList<Friend> friendList) {
        this.userName = userName;
        this.image = image;
        this.status = status;
        this.friendList = friendList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Friend> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<Friend> friendList) {
        this.friendList = friendList;
    }
    
    
}
