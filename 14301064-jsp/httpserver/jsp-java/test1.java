import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class
test1
implements Servlet {
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=gb2312");PrintWriter out = response.getWriter();
		out.println("HTTP/1.1 200 OK\r\n");
		out.println("       <HTML>     <HEAD>     <TITLE>第一个JSP页面</TITLE>     </HEAD> 	     <BODY>     "); for(int i = 0 ; i < 10; i++)     {     out.println(i);     
out.println("     <br>     ");}
out.println("     </BODY>     </HTML>		");
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