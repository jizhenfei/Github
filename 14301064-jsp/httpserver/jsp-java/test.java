import java.text.*;import java.util.*;import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class
test
implements Servlet {
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");PrintWriter out = response.getWriter();
		out.println("HTTP/1.1 200 OK\r\n");
		out.println(" <html>      <head>      <title>Show time</title>      </head>      <body>        <h1>this is a simple test</h1>         Hello :            ");               SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");               String str = format.format(new Date());            
out.println("            ");out.println(str );
out.println("                ");for(int i = 0 ; i < 10; i++)           {             out.println(i);         
out.println("        <br>         ");}         
out.println("     </body>      </html> 		");
}

	
	public void destroy() {

	}

	
	public ServletConfig getServletConfig() {

		return null;
	}

	
	public String getServletInfo() {

		return null;
	}


	public void init(ServletConfig arg0) throws ServletException {

	}
}