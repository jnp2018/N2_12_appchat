/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ver3;

/**
 *
 * @author VietAnh
 */
public class EncodeMessage {
    private String sender;
    private String receiver;
    private String content;
    
    public EncodeMessage(String sender,String receiver, String content){
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }
    
    public String getMessage(){
        if(sender.equals("") || receiver.equals("") || content.equals(""))
            return "";
        return sender + "@" + receiver + "@" + content;
    }
}
