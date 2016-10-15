
import java.io.File; 
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory; 
import javax.xml.parsers.ParserConfigurationException; 
import org.w3c.dom.Document; 
import org.w3c.dom.Element; 
import org.w3c.dom.Node; 
import org.w3c.dom.NodeList; 
import org.xml.sax.SAXException; 
public class XMLParse {

	Document document = null;
	NodeList Node = null;
	
	ArrayList<ServletInstance> servlist = new ArrayList<ServletInstance>();
	public XMLParse()
	{
		//指定File
		File file = new File("web.xml");
		//建立DocumentBuilderFactory对象
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder builder;
		
		try
		{
			//建立DocumentBuilder对象
			builder = builderFactory.newDocumentBuilder();
			//用DocumentBuilder对象的parse方法引入文件建立Document对象
			document = builder.parse(file);
			Node = document.getChildNodes();
			
			NodeList servletList = document.getElementsByTagName("servlet-mapping");
			showByCondition(servletList);
		} catch (ParserConfigurationException e) 
	    { 
		      e.printStackTrace(); 
		    } 
		    catch (SAXException e) 
		    { 
		      e.printStackTrace(); 
		    } 
		    catch (IOException e) 
		    { 
		      System.err.println("找不到你指定的文件！"); 
		      e.printStackTrace(); 
		    } 
		}
	
	public void showByCondition(NodeList Node)
	{
	    Element element; 
	    
	    for(int i=0;i<Node.getLength();i++)
	    {
	    	int n=0;
	    	ServletInstance servlet = new ServletInstance();
	    	//获得一个元素
	    	element = (Element) Node.item(i);
	    	//遍历所有子节点
	    	NodeList servletList = element.getChildNodes();
	    	for(int j=0;j<servletList.getLength();j++)
	    	{
	    		
	    		 if (!servletList.item(j).getNodeName().equals("#text")) 
	    	        { 
	    			 if(servletList.item(j).getNodeName().equals("url-pattern")){
		    			 String url = servletList.item(j).getTextContent();
		    			 servlet.setUrl(url);
		    			 n++;
		    		 }
		    		 if(servletList.item(j).getNodeName().equals("servlet-name")){
		    			 String name = servletList.item(j).getTextContent();
		    			 servlet.setName(name);
		    			 n++;
		    		 }
	    	      }
	    		 
	    		 if(n==2){
	    			 servlist.add(servlet);
	    		 }

	    	}
	    	
	    	 
	    }
	}
	
	public ArrayList<ServletInstance> getServlist(){
		return servlist;
	}
}
