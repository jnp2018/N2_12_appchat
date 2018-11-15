/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ver4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author VietAnh
 */
public class CreateFile {
    private FileWriter out;
    private BufferedReader in;

    public CreateFile() throws IOException {
//        Path path = Paths.get("historychat\\test2.txt");
//        Files.createDirectories(path.getParent());
//        try {
//            Files.createFile(path);
//        } catch (Exception e) {
//            System.err.println("already exists: " + e.getMessage());
//        }
//        out = new FileWriter(new File("historychat\\test1.txt"),true);
//        in = new BufferedReader(new FileReader(new File("historychat\\test1.txt")));
//        
//        out.write("hi" + "\r\n");
//        out.write("hello" + "\r\n");
//        out.write("bey" + "\r\n");
//        
//        out = new FileWriter(new File("historychat\\test2.txt"),true);
//        
//        out.write("hi" + "\r\n");
//        out.write("hello" + "\r\n");
//        out.write("bey" + "\r\n");
//        
//        out.close();
        
//        File folder = new File("historychat");
//        File[] listOfFiles = folder.listFiles();
//
//        for (int i = 0; i < listOfFiles.length; i++) {
//          if (listOfFiles[i].isFile()) {
//            System.out.println(listOfFiles[i].getName());
//          } else if (listOfFiles[i].isDirectory()) {
//            System.out.println("Directory " + listOfFiles[i].getName());
//          }
//        }
        
        try (FileReader in = new FileReader("historychat\\user1_user2.txt")){
            
            BufferedReader br = new BufferedReader(in);
            String line;
            
            System.out.println("historychat\\user1_user2.txt");
            
            while((line = br.readLine()) != null){
                System.out.println(line);
            }
        
            
        } catch (Exception e) {
        }
    }
    
    
    
    
    public static void main(String[] args) throws IOException {
        new CreateFile();
    }
}
