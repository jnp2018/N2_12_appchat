/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ver3;

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
public class LoginForm {
    
    private ActionListener listener;
    private JFrame frameLogin;
    private JTextField textName;
    private JPasswordField textPass;
    private JButton buttonLogin;

    public LoginForm(ActionListener listener) {
        this.listener = listener;
        frameLogin = new JFrame();
        
        JPanel panel = new JPanel();
        JLabel labelName = new JLabel("UserName");
        JLabel labelPass = new JLabel("PassWord");
        textName = new JTextField(15);
        textPass = new JPasswordField(15);
        JButton buttonLogin = new JButton("Login");
        buttonLogin.setActionCommand("login");
        buttonLogin.addActionListener(this.listener);
        
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
    
    public String getUserName(){
        return textName.getText();
    }
    
    public String getPassWord(){
        return String.valueOf(textPass.getPassword());
    }
    
    public void closeFrame(){
        frameLogin.dispose();
        frameLogin.setVisible(false);
    }
    
    
}
