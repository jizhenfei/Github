import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ServletProcessor {

	public void process(Request req, Response resp) {

		String servletName = req.getServletName();
		try {
			URLStreamHandler streamHandler = null;

			URL url = new URL(null,"file:"+ System.getProperty("user.dir")+"/bin",streamHandler);
			URLClassLoader loader = null;
			loader = new URLClassLoader(new URL[] {url});
			
			Class<?> myClass = null;
			myClass = loader.loadClass(servletName);
			
			Servlet servlet = (Servlet) myClass.newInstance();
			servlet.service((ServletRequest) req, (ServletResponse) resp);
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}