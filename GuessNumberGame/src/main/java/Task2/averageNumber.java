/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Task2;

/**
 *
 * @author qzz
 */

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import java.util.Vector;

public class averageNumber {
    
    public int singleConnect(String address){
        HttpURLConnection httpConnection = null;
        int indexOfSuccess = -1;
        try{
            URL url = new URL(address);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
            httpConnection.setRequestProperty("Content-Type", "text/html");
            httpConnection.setRequestProperty("Cookie", "clientId=0");
            httpConnection.connect();
            OutputStream out = httpConnection.getOutputStream();
//            String params = parameter;
//            out.write(params.getBytes());
            out.flush();
            out.close();

            int code = httpConnection.getResponseCode();
            if (code == 200) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(httpConnection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.indexOf("higher") != -1){
                        indexOfSuccess = 2;
                    } 
                    else if (line.indexOf("lower") != -1){
                        indexOfSuccess = 1;
                    }
                    else if (line.indexOf("Success") != -1){
                        indexOfSuccess = 0;
                    }
                    if (line.indexOf("</html>") != -1){
                        break;
                    }
                }
                reader.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        httpConnection.disconnect();
        return indexOfSuccess;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        int[] guessTimes = new int[100];
        int sum = 0;
         for (int i=0;i<100;i++){
            int flag = -1;
            int min = 1;
            int max = 100;
            int guessNumber = min + (int)(Math.random() * (max-min+1));
            String url="http://localhost:8585/StartPage.html";
            averageNumber an = new averageNumber();
            flag = an.singleConnect(url);
            url = "http://localhost:8585/GuessPage.html?userNumber=" + guessNumber;
            guessTimes[i] = 0;
            while(true){
                flag = an.singleConnect(url);
                guessTimes[i]++;
                 if (flag == 0){
                     break;
                 }
                 else if (flag == 1){
                     max = guessNumber;
                     guessNumber = min + (int)(Math.random() * (max-min+1));
                     url = "http://localhost:8585/GuessPage.html?userNumber=" + guessNumber;
                 }
                 else{
                     min = guessNumber;
                     guessNumber = min + (int)(Math.random() * (max-min+1));
                     url = "http://localhost:8585/GuessPage.html?userNumber=" + guessNumber;
                 } 
            }
            sum += guessTimes[i];
         }
         System.out.println((float)sum/100);
    }
}
