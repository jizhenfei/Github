
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class HttpServer {
	static String[] jsplist;
	// 关闭服务命令
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

	public static void main(String[] args) throws IOException {
		XMLParse xml = new XMLParse();
		ArrayList<ServletInstance> servlist = xml.getServlist();

		JSPParse jsp = new JSPParse();
		jsp.parse();
		jsplist = jsp.ReadJspFile();
		int port = 8080;
		ServerSocket server = new ServerSocket(port);
		System.out.println("server start at " + port + "...........");
		while (true) {
			Socket client = server.accept();
			invoke(client, servlist);
		}

	}

	private static void invoke(final Socket socket, final ArrayList<ServletInstance> servlist) throws IOException {
		new Thread(new Runnable() {
			public void run() {
				// 循环等待请求
				InputStream input = null;
				OutputStream output = null;
				try {
					// 等待连接，连接成功后，返回一个Socket对象
					input = socket.getInputStream();
					output = socket.getOutputStream();

					// 创建Request对象并解析
					Request request = new Request(input);
					request.parse();
					Response response = new Response(output);
					String uri = request.getUri().toString();
					if (!request.getUri().equals("")) {

						if (request.getUri().contains(".html")) {
						
							response.setRequest(request);

							StaticResourceProcessor processor = new StaticResourceProcessor();
							processor.process(request, response);
						} else if (request.getUri().contains(".jsp")) {
							if (jsplist.length != 0) {
								for (int i = 0; i < jsplist.length; i++) {
									if (jsplist[i].equals(uri.substring(1, uri.indexOf(".jsp")))) {
										request.setServletName(uri.substring(1, uri.indexOf(".jsp")));
										response.setRequest(request);
										JspProcessor processor = new JspProcessor();
										processor.process(request, response);
									}

								}
							}
	
						} else {

							for (int i = 0; i < servlist.size(); i++) {
								if (servlist.get(i).GetUrl().equals(uri)) {
									request.setServletName(servlist.get(i).GetName());
								}
							} // 创建 Response 对象
							response.setRequest(request);
							ServletProcessor processor = new ServletProcessor();
							processor.process(request, response);
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				} finally {
					try {
						input.close();
					} catch (Exception e) {
					}
					try {
						output.close();
					} catch (Exception e) {
					}
					try {
						socket.close();
					} catch (Exception e) {
					}
				}
			}

		}).start();
	}

}
