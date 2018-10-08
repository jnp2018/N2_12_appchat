/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ver3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author VietAnh
 */
public class Client implements ActionListener{
    
    private Socket socket;
    //private ActionListener listener;
    private LoginForm loginForm;
    private RoomChatForm roomChat;
    private BufferedReader in;
    private PrintWriter out;
    private ChatForm chatToA;
    private ChatForm chatToB;
    private ChatForm chatToC;
    private ChatForm chatToGroupA;
    
    
    
    public Client() throws IOException{
        socket = new Socket(InetAddress.getLocalHost(),7777);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(),true);
        loginForm = new LoginForm(this);
    }
    
    public void runClient() throws IOException{
        String message;
        String sender,receiver,content;
        DecodeMessage decode = new DecodeMessage();
        
        //  Vòng lặp đăng nhập
        while(true){
            message = in.readLine();
            
            if(message.startsWith("ERROR"))
                JOptionPane.showMessageDialog(null, "incorrect");
            if(message.startsWith("SUCCESS")){
                roomChat = new RoomChatForm(this);
                loginForm.closeFrame();
                System.out.println("hahahaah");
                break;
            }
        }
        
        // Vòng lặp check tin nhắn
        while(true){
            message = in.readLine();
            
            sender = decode.getSender(message);
            receiver = decode.getReceiver(message);
            content = decode.getContent(message);
            
            System.out.println(sender + receiver + content);

            if(receiver.equals("groupa")){
                if(chatToGroupA == null)
                    chatToGroupA = new ChatForm(loginForm.getUserName(),"groupa",out);
                chatToGroupA.appendTextArea(sender + ": " + content);
            }else{
                if(sender.equals("usera")){
                    if(chatToA == null)
                        chatToA = new ChatForm(loginForm.getUserName(),"usera",out);                
                    chatToA.appendTextArea(sender + ": " + content);
                }
                if(sender.equals("userb")){
                    if(chatToB == null)
                        chatToB = new ChatForm(loginForm.getUserName(),"userb",out);
                    chatToB.appendTextArea(sender + ": " + content);
                }
                if(sender.equals("userc")){
                    if(chatToC == null)
                        chatToC = new ChatForm(loginForm.getUserName(),"userc",out);
                    chatToC.appendTextArea(sender + ": " + content);
                }
            }             
        } 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("login".equals(e.getActionCommand())){
            String userName,passWord;
            userName = loginForm.getUserName();
            passWord = loginForm.getPassWord();
            
            out.println(userName);
            out.println(passWord);
        }
        if("A Chat".equals(e.getActionCommand())){
            if(chatToA == null)
                chatToA = new ChatForm(loginForm.getUserName(),"usera",out);
        }
        if("B Chat".equals(e.getActionCommand())){
            if(chatToB == null)
                chatToB = new ChatForm(loginForm.getUserName(),"userb",out);
        }
        if("C Chat".equals(e.getActionCommand())){
            if(chatToC == null)
                chatToC = new ChatForm(loginForm.getUserName(),"userc",out);
        }
        if("GroupA Chat".equals(e.getActionCommand())){
            if(chatToGroupA == null)
                chatToGroupA = new ChatForm(loginForm.getUserName(),"groupa",out);
        }
    }
    
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.runClient();
    }
    
}
