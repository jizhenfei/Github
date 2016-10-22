
    <%@ page contentType="text/html; charset=gb2312" language="java"%>

    <HTML>
    <HEAD>
    <TITLE>第一个JSP页面</TITLE>
    </HEAD>
	
    <BODY>
    <% for(int i = 0 ; i < 10; i++)
    {
    out.println(i);
    %>
    <br>
    <%}%>
    </BODY>
    </HTML>