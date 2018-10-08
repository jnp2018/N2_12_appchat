/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ver2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

/**
 *
 * @author VietAnh
 */
public class Server {
    /*
     *Tập lưu trữ tất cả các writer của từng client
     *Giúp dễ dàng truyền tin nhắn
     */
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>(); 
    
    /*
     *Tập lưu trữ tất cả username
     */
    private static HashSet<String> names = new HashSet<String>();
    
    /*
     * Port của server
     */
    private static final int PORT = 9999;
    
    /*
     * Message cho client biết là đăng nhập thành công
    */
    private static final String SUCCESS = "SUCCESSLOGIN";
    
    /*
     * Message cho client biết là tài khoàn không đúng
    */
    private static final String ERROR = "ERROR";
    
    /*
     * Message cho client biết thông điệp gửi đến là tin nhắn
    */
    private static final String MESSAGE = "MESSAGE";
    
    
    
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        try {
            while(true){
                new Handler(server.accept()).start();
            }      
        } 
        finally{
            server.close();
        }
    }
    
    private static class Handler extends Thread{
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String userName,passWord;
        
        public Handler(Socket socket){   
        client = socket;    
        }
        
        @Override
        public void run(){  
            try {  
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(client.getOutputStream(),true);
                // Đăng nhập user 
                while(true){   
                    userName = in.readLine();
                    passWord = in.readLine();

                    Boolean check = checkUser(userName,passWord);
                    if(check == true){
                        synchronized(names){ // đồng bộ hóa, tránh trường hợp 2 người cùng đăng nhập 1 lúc
                            if(!names.contains(userName)){
                                names.add(userName);
                                break;
                            }
                        }
                    }
                    else{
                        out.println(ERROR);
                    }
                }  
                out.println(SUCCESS);
                writers.add(out);
                // Nhận message từ user
                while(true){
                    String line = in.readLine();
                    if(line.equals("")){
                        continue;
                    }
                    else{
                        for(PrintWriter writer : writers){
                            writer.println(MESSAGE + userName + ": " + line);
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
        
        // method check useName and passWord
        public Boolean checkUser(String userName,String passWord){
            if(userName.equals("user a") && passWord.equals("password"))
                return true;
            else if(userName.equals("user b") && passWord.equals("password"))
                return true;
            else if(userName.equals("user c") && passWord.equals("password"))
                return true;
            else if(userName.equals("user d") && passWord.equals("password"))
                return true;
            else
                return false;
            }
    }
}
    
