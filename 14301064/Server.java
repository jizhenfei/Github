import java.io.*;
import java.net.*;

public class Server {  
    public static void main(String[] args) throws IOException {  
        ServerSocket server = new ServerSocket(5555);  
          
        while (true) {  
        	System.out.println("Listening...(Enter 'quit' to quit)"); 
            Socket socket = server.accept();  
            invoke(socket);  
        }  
    }  
      
    private static void invoke(final Socket client) throws IOException {  
        new Thread(new Runnable() {  
            public void run() {  
                BufferedReader in = null;  
                PrintWriter out = null;  
                try {  
                	
                    in = new BufferedReader(new InputStreamReader(client.getInputStream()));  
                    out = new PrintWriter(client.getOutputStream());  
  
                    while (true) {  
                        String msg = in.readLine(); 
                        StringBuffer rev = new StringBuffer(msg);
                        String revMsg = rev.reverse().toString();
                        out.println("The reverse message :" + revMsg);  
                        out.flush();  
                        if (msg.equals("quit")) {  
                            break;  
                        } 
                       
                    }  
                } catch(IOException ex) {  
                    ex.printStackTrace();  
                } finally {  
                    try {  
                        in.close();  
                    } catch (Exception e) {}  
                    try {  
                        out.close();  
                    } catch (Exception e) {}  
                    try {  
                        client.close();  
                    } catch (Exception e) {}  
                }  
            }  
        }).start();  
    }  
}  