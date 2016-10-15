
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
	// �رշ�������
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
 
        //�ȴ���������
        server.await();
    }

    public void await() {
        ServerSocket serverSocket = null;
        
        int port = 8888;
        try {
            //�������׽��ֶ���
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // ѭ���ȴ�����
        while (true) {
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;
            try {
                //�ȴ����ӣ����ӳɹ��󣬷���һ��Socket����
                socket = serverSocket.accept();
                input = socket.getInputStream();
                output = socket.getOutputStream();

                // ����Request���󲢽���
                Request request = new Request(input);
                request.parse();
                if(!request.getUri().equals("")){
                	String uri = request.getUri().toString();
                	
                	for(int i=0;i<servlist.size();i++){
                		if(servlist.get(i).GetUrl().equals(uri)){
                			request.setServletName(servlist.get(i).GetName());
                		}
                	}
                	 // ���� Response ����
                    Response response = new Response(output);
                    response.setRequest(request);
                	ServletProcessor processor = new ServletProcessor();
                    processor.process(request, response);
                }
                
                // �ر� socket
                socket.close();

            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

}
