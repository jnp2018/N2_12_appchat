/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ver3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author VietAnh
 */
public class Server {
    
    /*
    ** Port server
    */
    private static final int PORT = 7777;
    
    /*
    ** Lưu trữ UserName - PrintWriter như 1 cặp key - value.
    ** Dễ dàng trong việc sử dụng
    */
    private static Map<String,PrintWriter> map = new HashMap<String,PrintWriter>();
    
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(PORT);
            while(true){
                new Handler(server.accept()).start();
            }
        } catch (Exception e) {
            System.out.println("Error serverSocket: ");
        }
        
    }
    
    public static class Handler extends Thread{
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String userName,passWord;
        
        public Handler(Socket socket){
            this.client = socket;
        }
        
        @Override
        public void run(){
            try {
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(client.getOutputStream(),true);
                
                // check login
                while(true){
                    
                    userName = in.readLine();
                    passWord = in.readLine();

                    Boolean check = checkUser(userName,passWord);
                    System.out.println(userName + " " + passWord);
                    if(check == true){
                        // nếu nhập username-password đúng thì kiểm tra
                        // xem username này đã đăng nhập hay chưa rồi mới thêm vào map
                        synchronized (map) {
                        if (!map.containsKey(userName)) {
                            map.put(userName,out);                           
                            break;
                        }
                    }  
                    }else{
                        out.println("ERROR");
                    }
                }
                
                out.println("SUCCESS");
                DecodeMessage decode = new DecodeMessage();
                while(true){
                    String message = in.readLine();
                    String receiver = decode.getReceiver(message);
                    String sender = decode.getSender(message);
//                    String content = decode.getContent(message);
                    
                    // không cần kiểm tra line có rỗng hay không vì trong DECODE đã kiểm tra
//                    if(decode.getReceiver(message).equals("usera")){
//                        if(map.containsKey("usera"))
//                            map.get("usera").println(message);
//                    }
//                    if(decode.getReceiver(message).equals("userb")){
//                        if(map.containsKey("userb"))
//                            map.get("userb").println(message);
//                    }
//                    if(decode.getReceiver(message).equals("userc")){
//                        if(map.containsKey("userc"))
//                            map.get("userc").println(message);
//                    }
//                    if(decode.getReceiver(message).equals("groupa")){
//                        for(String user : map.keySet()){
//                            map.get(user).println(message);
//                        }
//                    }
                    directionMessage(sender, receiver, message);
//                   
                }
                
            } catch (Exception e) {
            }
            finally {
                // This client is going down!  Remove its name and its print
                // writer from the sets, and close its socket.
                if (userName != null) {
                    map.remove(userName);
                }
                try {
                    client.close();
                } catch (Exception e) {
                }
            }
        }
        
        public Boolean checkUser(String userName,String passWord){
            if(userName.equals("usera") && passWord.equals("password"))
                return true;
            else if(userName.equals("userb") && passWord.equals("password"))
                return true;
            else if(userName.equals("userc") && passWord.equals("password"))
                return true;
            else if(userName.equals("userd") && passWord.equals("password"))
                return true;
            else
                return false;
        }
        
        public void directionMessage(String sender, String receiver, String message){
            if(!sender.equals(receiver)){
                if(receiver.equals("usera")){
                        if(map.containsKey(receiver))
                            map.get(receiver).println(message);
                }
                if(receiver.equals("userb")){
                    if(map.containsKey(receiver))
                        map.get("userb").println(message);                   
                }
                if(receiver.equals("userc")){
                    if(map.containsKey(receiver))
                        map.get(receiver).println(message);
                }
                if(receiver.equals("groupa")){
                    for(String user : map.keySet()){
                        if(!user.equals(sender))
                            map.get(user).println(message);
                    }
                }
            }  
        }
        
    }
    
    
}
