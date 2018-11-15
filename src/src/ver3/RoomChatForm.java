/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ver3;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author VietAnh
 */
public class RoomChatForm {
    
    private ActionListener listener;
    private JFrame frame;
    private JButton userButtonA,userButtonB,userButtonC,groupButtonA;
    private JLabel userLabelA,userLabelB,userLabelC,groupLabelA;

    public RoomChatForm(ActionListener listener) {
        this.listener = listener;
        frame = new JFrame();
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,2));
        
        userButtonA = createButton("User A", "A Chat");
        userButtonB = createButton("User B", "B Chat");
        userButtonC = createButton("User C", "C Chat");
        groupButtonA = createButton("Group A", "GroupA Chat");
        userLabelA = new JLabel("online");
        userLabelB = new JLabel("offline");
        userLabelC = new JLabel("online");
        groupLabelA = new JLabel("offline");
        
        panel.add(userButtonA);
        panel.add(userLabelA);
        panel.add(userButtonB);
        panel.add(userLabelB);
        panel.add(userButtonC);
        panel.add(userLabelC);
        panel.add(groupButtonA);
        panel.add(groupLabelA);
        
        frame.add(panel);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true); 
    }
    
    public JButton createButton(String name,String Actioncommand){
        JButton btn = new JButton(name);
        btn.addActionListener(listener);
        btn.setActionCommand(Actioncommand);
        return btn;
    }
    
    
}
