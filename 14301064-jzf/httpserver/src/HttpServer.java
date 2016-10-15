
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class HttpServer {
    XMLParse xml = new XMLParse();
	ArrayList<ServletInstance> servlist = xml.getServlist();
	// 关闭服务命令
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
 
        //等待连接请求
        server.await();
    }

    public void await() {
        ServerSocket serverSocket = null;
        
        int port = 8888;
        try {
            //服务器套接字对象
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // 循环等待请求
        while (true) {
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;
            try {
                //等待连接，连接成功后，返回一个Socket对象
                socket = serverSocket.accept();
                input = socket.getInputStream();
                output = socket.getOutputStream();

                // 创建Request对象并解析
                Request request = new Request(input);
                request.parse();
                if(!request.getUri().equals("")){
                	String uri = request.getUri().toString();
                	
                	for(int i=0;i<servlist.size();i++){
                		if(servlist.get(i).GetUrl().equals(uri)){
                			request.setServletName(servlist.get(i).GetName());
                		}
                	}
                	 // 创建 Response 对象
                    Response response = new Response(output);
                    response.setRequest(request);
                	ServletProcessor processor = new ServletProcessor();
                    processor.process(request, response);
                }
                
                // 关闭 socket
                socket.close();

            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

}
