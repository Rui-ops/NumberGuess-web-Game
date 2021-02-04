/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Task2;

import java.io.FileOutputStream;
import java.io.*;
import java.io.PrintStream;

/**
 *
 * @author qzz
 */
public class compareLogic {
    
    public compareLogic(){
        
    }
    
    public static void createHtml(String filePath){
            StringBuilder stringHtml = new StringBuilder();
            PrintStream printStream = null;
            try{       
                printStream = new PrintStream(new FileOutputStream(filePath));
            } catch(IOException e){
                e.printStackTrace();
            }
            stringHtml.append("<html><head>\t\n");
            stringHtml.append("<title>GuessPage</title>\t\n");
            stringHtml.append("<meta charset=\"UTF-8\">\t\n");
            stringHtml.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\t\n");
            stringHtml.append("</head>\t\n");
            stringHtml.append("<body>\t\n");
            stringHtml.append("<h1>Hello!</h1>\t\n");
            stringHtml.append("</body></html>\t\n");
            printStream.println(stringHtml.toString());
    }
}
