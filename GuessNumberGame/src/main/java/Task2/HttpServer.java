package Task2;
import java.io.*;
import java.net.*;
import java.security.KeyStore;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class HttpServer{
    
    public static void main(String[] args) throws IOException{
        SSLServerSocketFactory ssf; // = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
        SSLSocket s = null;
        SSLServerSocket ss = null;
        try{
            KeyStore ks = null;
            ks = KeyStore.getInstance("JKS", "SUN");
            InputStream is = null;
            //is = new FileInputStream(new File("C:/Program Files/Java/jdk1.8.0_271/jre/lib/security/cacerts"));
            is = new FileInputStream(new File("C:/users/ritch/.keystore"));
            char[] pwd = "rootroot".toCharArray();
            ks.load(is,pwd);
            
            SSLContext ctx = SSLContext.getInstance("TLS");
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, pwd);
            ctx.init(kmf.getKeyManagers(), null, null);
            //ctx.init(null, null, null);
            ssf = ctx.getServerSocketFactory();

            ss = (SSLServerSocket)ssf.createServerSocket(8585);
            String[] cipher = {"TLS_RSA_WITH_AES_128_CBC_SHA"};
            ss.setEnabledCipherSuites(cipher);
            
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
	
        System.out.println("Serversocket Created");
        int cookieNumber = 0;
        Vector<Guess_Bean> vector = new Vector<Guess_Bean>();
	while(true){
            int indexOfCookie = -1;
            int cookieCurrentNumber = 0;
            String str;
            String hint = "";
	    s = (SSLSocket)ss.accept();
            BufferedReader request =
		new BufferedReader(new InputStreamReader(s.getInputStream()));
            String firstLine = request.readLine();
            System.out.println(firstLine);
            while( (str = request.readLine()) != null && str.length() > 0){
                indexOfCookie = str.indexOf("Cookie");
                if (indexOfCookie != -1){
                    StringTokenizer tokensForCookie =
                        new StringTokenizer(str," =");
                    tokensForCookie.nextToken();
                    tokensForCookie.nextToken();
                    cookieCurrentNumber = Integer.parseInt(tokensForCookie.nextToken());
                    break;
                }
	    }
//            s.shutdownInput();
            PrintStream response = new PrintStream(s.getOutputStream());
            
            if (indexOfCookie == -1){
                response.println("HTTP/1.1 200 OK");
                response.println("Server: Trash 0.1 Beta");
                response.println("Content-Type: text/html");
                response.println("Set-Cookie: clientId="+cookieNumber+"; expires=Wednesday,31-Dec-20 21:00:00 GMT");
                response.println();
                cookieNumber++;
            }

            StringTokenizer tokens =
                new StringTokenizer(firstLine," ?=");
            tokens.nextToken(); // The word GET
            String requestedDocument = tokens.nextToken();
            if("/favicon.ico".equals(requestedDocument)){
                requestedDocument = "C:/Users/ritch/Desktop/ttt/GuessNumberGame/src/main/java/Task2" + requestedDocument;
                File file = new File(requestedDocument);
                FileInputStream infil = new FileInputStream(file);
                byte[] b = new byte[1024];
                while( infil.available() > 0){
                    response.write(b,0,infil.read(b));
                }
                s.shutdownOutput();
                s.close();
                continue;
            }
            requestedDocument = "C:/Users/ritch/Desktop/ttt/GuessNumberGame/src/main/java/Task2" + requestedDocument;
            int userNumberIndex = firstLine.indexOf("userNumber");
            if (userNumberIndex==-1){
                if (indexOfCookie != -1){
                    response.println("HTTP/1.1 200 OK");
                    response.println("Server: Trash 0.1 Beta");
                    response.println("Content-Type: text/html");
                    response.println();
                }
                else{
                    Guess_Bean gb = new Guess_Bean();
                    vector.add(gb);
                    cookieCurrentNumber = vector.size() - 1;
                }
                File fi = new File(requestedDocument);
                FileInputStream fis = new FileInputStream(fi);
                byte[] buf = new byte[1024];
                int len;
                while((len=fis.read(buf)) != -1) {
                        response.write(buf, 0, len);
                }
                vector.get(cookieCurrentNumber).refresh();
            }
            else{
                tokens.nextToken();
                int userNumber = Integer.parseInt(tokens.nextToken());
                Guess_Bean gb = vector.get(cookieCurrentNumber);
                gb.setUsernumber(userNumber);
//                int cheatNumber = gb.getSercert();
//                int guessNumber = gb.getGuesstimes();
                if (userNumber > gb.getSercert()){
                    hint = "lower";
                }
                else if (userNumber < gb.getSercert()){
                    hint = "higher";
                }
                else{
                    requestedDocument = "C:/Users/ritch/Desktop/ttt/GuessNumberGame/src/main/java/Task2" + "/successPage.html";
                }
                File f = new File(requestedDocument);
                response.println("HTTP/1.1 200 OK");
                response.println("Server: Trash 0.1 Beta");
                response.println("Content-Type: text/html");
                response.println();
                BufferedReader reader = new BufferedReader(new FileReader(f));
                String line = null;
                StringBuilder fd_str = new StringBuilder();
                while( (line=reader.readLine())!=null){
                    fd_str.append(line);
                }
                if ("higher".equals(hint) || "lower".equals(hint)){
                    fd_str.insert(477, hint);
                    fd_str.insert(526, gb.getGuesstimes());
                    fd_str.append("Cheat:"+gb.getSercert());
                }
                else{
                    fd_str.insert(482, gb.getGuesstimes());
                }
                response.println(fd_str);
            }
            s.shutdownOutput();
            s.close();
        }
    }
}