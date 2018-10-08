/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ver2;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author VietAnh
 */
public class Client implements ActionListener{
    
    private JFrame frameLogin,frameChat;
    //private String userName,passWord;
    private JTextField textName;
    private JPasswordField textPass;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    
    private JTextField inputText;
    private JTextArea textArea;
    
    public void initLoginFrame(){
        
        frameLogin = new JFrame();
        
        JPanel panel = new JPanel();
        JLabel labelName = new JLabel("UserName");
        JLabel labelPass = new JLabel("PassWord");
        textName = new JTextField(15);
        textPass = new JPasswordField(15);
        JButton buttonLogin = new JButton("Login");
        buttonLogin.setActionCommand("login");
        buttonLogin.addActionListener(this);
        
        panel.add(labelName);
        panel.add(textName);
        panel.add(labelPass);
        panel.add(textPass);
        panel.add(buttonLogin);
        
        frameLogin.add(panel);
        
        frameLogin.pack();
        frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameLogin.setLocationRelativeTo(null);
        frameLogin.setVisible(true);
        
    }
    
    public void initChatFrame(){
        frameChat = new JFrame();
        
        inputText = new JTextField(40);
        inputText.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                out.println(inputText.getText());
                inputText.setText("");
            }
        });
        textArea = new JTextArea(8,40);
        textArea.setEditable(false);
        
        frameChat.getContentPane().add(new JScrollPane(textArea),"North");
        frameChat.getContentPane().add(inputText,"Center");
        
        frameChat.pack();
        frameChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameChat.setLocationRelativeTo(null);
        frameChat.setVisible(true);
    }
    
    public void closeFrame(JFrame frame){
        frame.dispose();
        frame.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("login".equals(e.getActionCommand())){ 
            String userName = textName.getText();
            String passWord = String.valueOf(textPass.getPassword());
            out.println(userName);
            out.println(passWord);
        }
    }
    
    public void runClient() throws IOException{
        
        socket = new Socket("192.168.1.2",9999);
        out = new PrintWriter(socket.getOutputStream(),true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        // Xử lý tất cả các yêu cầu từ server gửi tới
        while(true){
            String line = in.readLine();
            
            if(line.startsWith("SUCCESSLOGIN")){
                closeFrame(frameLogin);
                initChatFrame();
            }
            if(line.startsWith("ERROR")){
                JOptionPane.showMessageDialog(null, "Sai tai khoan hoac mat khau");
            }
            if(line.startsWith("MESSAGE")){
                textArea.append(line.substring(7) +"\n");
            }
        }
            
        
    }
    
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.initLoginFrame();
        client.runClient();
    }
    
}
