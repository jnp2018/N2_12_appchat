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
public class DecodeMessage {
//    private String massage;
//    
//    public DecodeMessage(String massage){
//        this.massage = massage;
//        
//    }
    public String getSender(String massage){
        try {
            if(massage == null || massage.equals(""))
            return "";
            String[] decode = massage.split("@");
            return decode[0];
        } catch (Exception e) {
            System.out.println("error getSender" + e.getMessage());
            return null;
        }
        
    } 
    public String getReceiver(String massage){
        try {
            if(massage == null || massage.equals(""))
            return "";
            String[] decode = massage.split("@");
            return decode[1];
        } catch (Exception e) {
            System.out.println("error getReceiver" + e.getMessage());
            return null;
        }
        
    }
    public String getContent(String massage){
        try {
            if(massage == null || massage.equals(""))
            return "";
            String[] decode = massage.split("@");
            return decode[2];
        } catch (Exception e) {
            System.out.println("error getContent " + e.getMessage());
            return null;
        }
        
    }
    
    public static void main(String[] args) {
        DecodeMessage decode = new DecodeMessage();
        String massage = "nguyen van a@12345";
        System.out.println(decode.getSender(massage) + "\r\n" + decode.getReceiver(massage) + "\r\n" + decode.getContent(massage));
    }
            
}
