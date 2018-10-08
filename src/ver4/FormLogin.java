/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ver4;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author VietAnh
 */
public class FormLogin extends JFrame {
    
    private ActionListener listener;
    private JTextField textName;
    private JPasswordField textPass;
    private JButton buttonLogin;

    public FormLogin(ActionListener listener) {
        this.listener = listener;
        textName = new JTextField(15);
        textPass = new JPasswordField(15);
        buttonLogin = new JButton("Login");
        buttonLogin.setActionCommand("login");
        buttonLogin.addActionListener(this.listener);
    }
    
    public void createUI(){
        Container pane = getContentPane();
        JPanel panel = new JPanel();
        JLabel labelName = new JLabel("UserName");
        JLabel labelPass = new JLabel("PassWord");
        
        panel.add(labelName);
        panel.add(textName);
        panel.add(labelPass);
        panel.add(textPass);
        panel.add(buttonLogin);
        
        pane.add(panel);
        
        pack();
        setSize(new Dimension(600, 80));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public String getUserName(){
        return textName.getText();
    }
    
    public String getPassWord(){
        return String.valueOf(textPass.getPassword());
    }
    
    public void closeFrame(){
        dispose();
        setVisible(false);
    }
    
//    public static void main(String[] args) {
//        (new FormLogin(null)).createUI();
//        
//    }
    
    
}
