/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ver4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author VietAnh
 */
public final class Server {
    
    private static final int PORT = 9999;
    
    private static Map<String,ObjectOutputStream> director = new HashMap<String, ObjectOutputStream>();
    
    private static Map<String,ArrayList<String>> group = new HashMap<String,ArrayList<String>>();
    
    private static Map<String,ArrayList<String>> friends = new HashMap<String,ArrayList<String>>();
    
    private static HashSet<String> listfile = new HashSet<String>();

    public Server() {
        initGroup();
        initFriends();
    }
    
    public static void initFriends(){
        // User1 : 
        String username1 = "user1";
        ArrayList<String> friend1 = new ArrayList<String>();
        friend1.add("user2");
        friend1.add("user3");
        friend1.add("user4");
        friend1.add("user5");
        friends.put(username1, friend1);
        
        // User2 :
        String username2 = "user2";
        ArrayList<String> friend2 = new ArrayList<String>();
        friend2.add("user1");
        friend2.add("user3");
        friend2.add("user4");
        friends.put(username2, friend2);
        
        // User3 :
        String username3 = "user3";
        ArrayList<String> friend3 = new ArrayList<String>();
        friend3.add("user1");
        friend3.add("user2");
        friends.put(username3, friend3);
       
        // ...
        // ...
        // UserN :
    }
    
    public static void initGroup(){
        // Group A gồm user1, user2, user3
        ArrayList<String> groupA = new ArrayList<String>();
        groupA.add("user1");
        groupA.add("user2");
        groupA.add("user3");
        group.put("groupa", groupA);
        // Group B gồm user1, user2, user4, user5
        ArrayList<String> groupB = new ArrayList<String>();
        groupB.add("user1");
        groupB.add("user2");
        groupB.add("user4");
        groupB.add("user5");
        group.put("groupb", groupB);
        // ...
        // Group N gồm ........
    }
    
    // Lấy toàn bộ file trong folder history
    public static void getAllFile(){
        File folder = new File("historychat");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            String path = listOfFiles[i].getName();
            if (listOfFiles[i].isFile() && !listfile.contains(path)) {
                listfile.add(path);
            } else if (listOfFiles[i].isDirectory()) {
              System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        initFriends();
        initGroup();
        getAllFile();
        
        try {  
            while(true){
                new Handler(server.accept()).start();
            }
        } 
        catch (Exception e) {
            System.out.println("server can not connect to client");
        }
        finally{
            server.close();
        }
    }
    
    public static class Handler extends Thread{
        private Socket client;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        private User user;
        private FileWriter fo;

        public Handler(Socket client) {
            this.client = client;
        }
        
        @Override
        public void run(){
            try {
                // Create  input - output 
                out = new ObjectOutputStream(client.getOutputStream());
                in = new ObjectInputStream(client.getInputStream());
                // Check login
                while(true){
                    String username, password;
                    // Việc check username và password có null hay "" do client xử lý
                    username = (String) in.readObject();
                    password = (String) in.readObject();
                    
                    if(checkUser(username, password)){
                        synchronized (director) {
                            if (!director.containsKey(username)) {  
                                // Nếu chua có trong container thì thêm user vào
                                director.put(username, out);
                                out.writeObject("SUCCESS");
                                break;
                            }
                        }
                    }
                    else{
                        out.writeObject("ERROR");
                    } 
                }
                
                out.writeObject(user);
                
                while(true){
                    Message msg = (Message) in.readObject();
                    System.out.println("sender:" + msg.getSender());
                    System.out.println("receiver:" + msg.getReceiver());
                    System.out.println("content:" + msg.getContent());
                    
                    String msgToFile = msg.getSender() + ": " + msg.getContent();
                    
                    String fileName = getFileName(msg.getSender(), msg.getReceiver());
                    
                    // Nếu file history giữa 2 user chưa được tạo
                    if(!listfile.contains(fileName)){
                        createFile(fileName);
                        listfile.add(fileName);
                        
                    }

                    writeToFile(fileName, msgToFile);
                    broadcastMessage(msg);
                }        
            } catch (Exception e) {
                System.out.println("error frome server");
            }
            finally{
                
            }
        }
        
        // Check login và trả về user tương ứng
        public boolean checkUser(String username, String password){
            if(username.equals("user1") && password.equals("a")){
            ArrayList<Friend> friend = new ArrayList<Friend>();
            friend.add(new Friend("user2", "online"));
            friend.add(new Friend("user3", "online"));
            friend.add(new Friend("user4", "online"));
            friend.add(new Friend("user5", "online"));
            friend.add(new Friend("groupa", "online"));
            friend.add(new Friend("groupb", "online"));
            this.user = new User("user1", "image\\1.png", "online", friend);
            return true;
            }
            if(username.equals("user2") && password.equals("a")){
                ArrayList<Friend> friend = new ArrayList<Friend>();
                friend.add(new Friend("user1", "online"));
                friend.add(new Friend("user3", "online"));
                friend.add(new Friend("user4", "online"));
                friend.add(new Friend("groupa", "online"));
                friend.add(new Friend("groupb", "online"));
                this.user = new User("user2", "image\\2.png", "online", friend);
                return true;
            }
            return false;
        }
        
        // Gửi tim nhắn đến các user
        public void broadcastMessage(Message msg){
            // lấy ra user có username giống người gửi message
            String receiver = msg.getReceiver();
            String sender = msg.getSender();
            String content = msg.getContent();
            
            System.out.println("sender: " + msg.getSender());
            System.out.println("receiver: " + msg.getReceiver());
            System.out.println("content: " + msg.getContent());
            
            if(receiver.equals("groupa")){
                for(String friend : group.get("groupa")){
                    if(!friend.equals(sender) && director.containsKey(friend)){
                        try {
                            director.get(friend).writeObject(msg);    
                        } catch (Exception e) {
                            System.out.println("can not broadcast to groupb");
                        }
                    }
                }
            }
            else if(receiver.equals("groupb")){
                for(String friend : group.get("groupb")){
                    if(!friend.equals(sender) && director.containsKey(friend)){
                        try {
                            director.get(friend).writeObject(msg);    
                        } catch (Exception e) {
                            System.out.println("can not broadcast to groupb");
                        }
                    }
                }
            }
            else{
                for(String friend : friends.get(sender)){
                    if(friend.equals(receiver) && director.containsKey(friend)){
                        try {
                            director.get(friend).writeObject(msg);
                        } catch (Exception e) {
                            System.out.println("can not broadcast to " + receiver);
                        }
                        
                    }
                }
            }
        }  
        
        // Ghi message nhận được vào file tương ứng
        public void writeToFile(String fileName, String msg){
            String path = "historychat\\" + fileName;
            try {
                fo = new FileWriter(new File(path),true);
                fo.write(msg + "\r\n");
                fo.close();    
            } catch (Exception e) {
                System.out.println("can not save file");           
            }
            
        }
        
        // Tạo 1 file vào thư mục history
        public void createFile(String fileName){
            try {
                Path path = Paths.get("historychat\\" + fileName);
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            } catch (Exception e) {
                System.err.println("server không thể tạo file: " + e.getMessage());
            }
        }
        
        // Lấy filePath từ sender và receiver
        public String getFileName(String sender,String receiver){
            // receiver = group
            if(receiver.startsWith("group")){
                String fileName = receiver + ".txt";
                return fileName;
            }
            // sender < receiver
            if(sender.compareTo(receiver) < 0){
                String fileName = sender + "_" + receiver + ".txt";
                return fileName;
            }
            //sender > receiver
            if(sender.compareTo(receiver) > 0){
                System.out.println("1");
                String fileName = receiver + "_" + sender + ".txt";
                return fileName;
            } 
            return null;
        }
        
        
        
    }
}
