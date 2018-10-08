/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ver4;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import ver3.EncodeMessage;

/**
 *
 * @author VietAnh
 */
public class FormChat extends JFrame implements ActionListener{
    
    private JTextArea textArea;
    private JTextField textInput;
    private JButton buttonSend;
    private ObjectOutputStream out;
    private String sender;
    private String receiver;

    public FormChat(String sender, String receiver,ObjectOutputStream out){
        this.sender = sender;
        this.receiver = receiver;
        this.out = out;
        textArea = new JTextArea(10, 40);
        textInput = new JTextField(32);
        buttonSend = new JButton("send");
    }
    
    public void createUI(){
        Container pane = getContentPane();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
      
        textArea.setEditable(false);    
        textInput.addActionListener(this);
        buttonSend.addActionListener(this);
        //sendButton.setActionCommand("send");
        
        panel.add(new JScrollPane(textArea),BorderLayout.PAGE_START);
        panel.add(textInput, BorderLayout.LINE_START);
        panel.add(buttonSend, BorderLayout.CENTER);
        
        pane.add(panel);
        
        setTitle(sender + " to " + receiver);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public String getInputText(){
        return textInput.getText();
    }
    
    public void appendTextArea(String user, String message){
        textArea.append(user + ": " + message + "\r\n");
    }
    
    public void loadHistory(){
        String filePath = filePath();
        System.out.println(filePath);
        //System.out.println(filePath);
        try(FileReader in = new FileReader(filePath)){
            BufferedReader br = new BufferedReader(in);
            String line;
            while((line = br.readLine()) != null){
                textArea.append(line + "\r\n");
            }
            
            in.close();
            br.close();
        } 
        catch (Exception e) {
            System.out.println("can not open file:" + filePath);
        }
    }
    
    public String filePath(){
        // receiver = group
        if(receiver.startsWith("group")){
            String filePath = "historychat\\" + receiver + ".txt";
            return filePath;
        }
        // sender < receiver
        if(sender.compareTo(receiver) < 0){
            String filePath = "historychat\\" + sender + "_" + receiver + ".txt";
            return filePath;
        }
        //sender > receiver
        if(sender.compareTo(receiver) > 0){
            String filePath = "historychat\\" + receiver + "_" + sender + ".txt";
            return filePath;
        } 
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttonSend || e.getSource() == textInput){
            String text = textInput.getText();
            // Kiểm tra nếu text thỏa mãn thì mới gửi
            if(!text.equals("") && text != null){
                appendTextArea(sender,text);
                Message msg = new Message(sender, receiver, text);
                try {
                    out.writeObject(msg);
                } catch (IOException ex) {
                    System.out.println("can not send message frome " + sender);
                }
                textInput.setText("");
            }
            
        }
    }
    
//    public static void main(String[] args) {
//        new FormChat("a","b",null).createUI();
//    }
    
}
