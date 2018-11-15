/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ver4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author VietAnh
 */
public class Client implements ActionListener,MouseListener{
    
    //private Map<String,FormChat> mapChat = new HashMap<String,FormChat>();
    
    private static final int PORT = 9999;
    
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket client;
    
    private User user;
    private Map<String,FormChat> mapChat = new HashMap<String,FormChat>();
    private FormLogin loginForm;
    private FormUser userForm;

    public Client() {
        try {
            client = new Socket(InetAddress.getLocalHost(),PORT);
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());  
        } catch (Exception e) {
            System.out.println("client can not connect to server");
        }
        loginForm = new FormLogin(this);
        loginForm.createUI();
        
    }
    
    public void runClient() throws Exception{
        
        // Check login
        while(true){
            String massage = (String) in.readObject();
            if(massage.equals("SUCCESS")){
                loginForm.closeFrame();
                break;
            }
            else{
                JOptionPane.showMessageDialog(null, "Incorrect");
            }
        }
        
        user = (User) in.readObject();
        userForm = new FormUser(user.getImage(), user.getUserName(), user.getFriendList(), this);
        userForm.createUI();
        
        while(true){
            Message msg = (Message) in.readObject();
            String sender = msg.getSender();
            String receiver = msg.getReceiver();
            String content =  msg.getContent();
            
            // Message gửi đến không phải cho group
            if(receiver.equals(user.getUserName())){
                if(mapChat.containsKey(sender)){
                    mapChat.get(sender).appendTextArea(sender, content);
                }
                else{
                    mapChat.put(sender, new FormChat(receiver, sender, out));
                    mapChat.get(sender).createUI();
                    mapChat.get(sender).loadHistory();
                    //mapChat.get(sender).appendTextArea(sender, content);
                }
            }
            // Mesage gửi đến group
            else{
                if(mapChat.containsKey(receiver)){
                    mapChat.get(receiver).appendTextArea(sender, content);
                }
                else{
                    mapChat.put(receiver, new FormChat(user.getUserName(), receiver, out));
                    mapChat.get(receiver).createUI();
                    mapChat.get(receiver).loadHistory();
                    //mapChat.get(receiver).appendTextArea(sender, content);
                }
            }
        }
    }
    
    
    // Gửi username - password cho server 
    // Trong actionPerformed không thể thêm try-catch
    public void sendMassageLogin(String username,String password){
        try {
            out.writeObject(username);
            out.writeObject(password);
            System.out.println("send done");
        } catch (Exception e) {
            System.out.println("can not send username - password");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if("login".equals(e.getActionCommand())){
            String username,password;
            username = loginForm.getUserName();
            password = loginForm.getPassWord();
            System.out.println(""+ username + " " + password);
            sendMassageLogin(username,password);         
        }
    }
    
    

    @Override
    public void mouseClicked(MouseEvent e) {
       if (e.getClickCount() == 2) {
            JTable target = (JTable) e.getSource();
            int row = target.getSelectedRow();
            String receiver = (String) target.getValueAt(row, 0);
            if(!mapChat.containsKey(receiver)){
                mapChat.put(receiver, new FormChat(user.getUserName(), receiver, out));
                mapChat.get(receiver).createUI();
                mapChat.get(receiver).loadHistory();
            }
            
         }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.runClient();
    }   
}
