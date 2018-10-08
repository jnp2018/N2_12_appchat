/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ver4;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author VietAnh
 */
public class FormUser extends JFrame{
    private JLabel userPicture;
    private JLabel userName;
    private JTable friendTable;
    private JComboBox status;
    private MouseListener listener;
    
    public FormUser(String image,String username,ArrayList<Friend> friend,MouseListener listener){
        this.listener = listener;
     
        String stt[] = {"online","offline","busy","be right back"};
        status = new JComboBox(stt);

        ImageIcon icon = new ImageIcon(image);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        userPicture = new JLabel(icon);
    
        userName = new JLabel(username);

        String[] colums = {"username","status"};
        DefaultTableModel tablemodel = new DefaultTableModel(colums, 0);
        friendTable = new JTable(tablemodel);
        for(int i = 0 ; i < friend.size(); i++){
            String name = friend.get(i).getName();
            String status = friend.get(i).getStatus();
            Object[] data = {name,status};
            tablemodel.addRow(data);
        }
    }
    
    public void createUI(){
        
        // Create container
        Container pane = getContentPane();      
        status.setPreferredSize(new Dimension(1, 20));
        // Create main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        userPicture.setAlignmentX(Component.CENTER_ALIGNMENT);    
        userName.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(userPicture);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(userName);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(status);     
        
        friendTable.setDefaultEditor(Object.class, null);
        friendTable.addMouseListener(listener);
        JScrollPane scroll = new JScrollPane(friendTable);
        mainPanel.add(Box.createVerticalStrut(20)); // This will expand/contract as needed.
        mainPanel.add(scroll);
        
        pane.add(mainPanel);

        pack();
        setSize(300, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
