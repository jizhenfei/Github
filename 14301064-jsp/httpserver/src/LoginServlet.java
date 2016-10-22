
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class LoginServlet implements Servlet {
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String username = "";
		username = request.getParameter("account");
		String password = "";
		password = request.getParameter("password");
		
		PrintWriter pw = response.getWriter();
		if(username.equals("123") && password.equals("456")){
			pw.println("HTTP/1.1 200 OK\r\n");
			pw.println("<html>");
			pw.println("<head>");
			pw.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
			pw.println("</head>");
			pw.println("<html>");
			pw.println("<body>");
			pw.println("<h1>Login Success</h1>");
			pw.println("</body>");
			pw.println("</html>");
			pw.flush();
		}else{
			pw.println("HTTP/1.1 200 OK\r\n");
			pw.println("<html>");
			pw.println("<head>");
			pw.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
			pw.println("</head>");
			pw.println("<html>");
			pw.println("<body>");
			pw.println("<h1>Login Fail</h1>");
			pw.println("</body>");
			pw.println("</html>");
			pw.flush();
		}
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
}
