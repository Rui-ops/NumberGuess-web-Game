package Task2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author qzz
 */
import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class HttpServerThread extends Thread{
    final private Socket s;
    
    public HttpServerThread(Socket s){
        this.s = s;
    }
    @Override
    public void run(){
        try{
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();
            BufferedReader request =
		new BufferedReader(new InputStreamReader(is));
            
            String str = request.readLine();
            System.out.println(str);
            StringTokenizer tokens =
                new StringTokenizer(str," ?=");
            tokens.nextToken(); // The word GET
            String requestedDocument = tokens.nextToken();
            System.out.println(requestedDocument);
            String parameter = tokens.nextToken();
            System.out.println(parameter);
//            String filePath = "D:/学习资料/KTH/Network Programming/GuessNumberGame/src/main/java/Task2/test.html";
//            compareLogic.createHtml(filePath);
//            StringBuilder stringHtml = new StringBuilder();
//            String filePath = "D:/学习资料/KTH/Network Programming/GuessNumberGame/src/main/java/Task2/test.html";
//            PrintStream printStream = new PrintStream(new FileOutputStream(filePath));
//
//            stringHtml.append("<html><head>");
//            stringHtml.append("<title>GuessPage</title>");
//            stringHtml.append("<meta charset=\"UTF-8\">");
//            stringHtml.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
//            stringHtml.append("</head>");
//            stringHtml.append("<body>");
//            stringHtml.append("<h1>Hello!</h1>");
//            stringHtml.append("</body></html>");
//            printStream.println(stringHtml.toString());
            requestedDocument = "C:/Users/ritch/Desktop/ttt/GuessNumberGame/src/main/java/Task2" + requestedDocument;
            File fi = new File(requestedDocument);
            FileInputStream fis = new FileInputStream(fi);
            byte[] buf = new byte[1024];
                int len;
                while((len=fis.read(buf)) != -1) {
                        os.write(buf, 0, len);
                }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
