/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ver3;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author VietAnh
 */
public class ChatForm implements ActionListener{
    private JFrame frame;
    private JTextArea textArea;
    private JTextField inputText;
    private ActionListener listener;
    private JButton sendButton;
    
    private String fromUser,toUser;
    private PrintWriter out;
    
    public ChatForm(String fromUser,String toUser, PrintWriter out){
        this.out = out;
        this.fromUser = fromUser;
        this.toUser = toUser;
        frame = new JFrame();
        //this.listener = listener;
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        textArea = new JTextArea(10, 40);
        textArea.setEditable(false);
        inputText = new JTextField(32);
        inputText.addActionListener(this);
        sendButton = new JButton("send");
        sendButton.addActionListener(this);
        //sendButton.setActionCommand("send");
        
        panel.add(new JScrollPane(textArea),BorderLayout.PAGE_START);
        panel.add(inputText, BorderLayout.LINE_START);
        panel.add(sendButton, BorderLayout.CENTER);
        
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }
    
    public String getInputText(){
        return inputText.getText();
    }
    
    public void appendTextArea(String text){
        textArea.append(text + "\r\n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        if("send".equals(e.getActionCommand())){
//            EncodeMessage endcode = new EncodeMessage(fromUser, toUser, inputText.getText());
//            appendTextArea(fromUser + ": " +inputText.getText());
//            out.println(endcode.getMessage());
//            inputText.setText("");
//        }
        if(e.getSource() == sendButton || e.getSource() == inputText){
            EncodeMessage endcode = new EncodeMessage(fromUser, toUser, inputText.getText());
            appendTextArea(fromUser + ": " +inputText.getText());
            out.println(endcode.getMessage());
            inputText.setText("");
        }
    }
    
}
