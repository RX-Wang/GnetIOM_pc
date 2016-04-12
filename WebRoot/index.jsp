<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
	
  </head>

	<body>
		<a href="<%= basePath%>web/userController/userLogin">aaaaaaaaaaaaaaaa</a>
	<%-- 	<jsp:forward page="/WEB-INF/jsp/login.jsp">
	
</jsp:forward> --%>
	</body>
</html>
