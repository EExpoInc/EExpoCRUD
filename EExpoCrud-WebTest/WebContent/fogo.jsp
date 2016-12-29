<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%= new Date(System.currentTimeMillis()) %>
<br />

<%
int r = (int) (Math.random() * 888);
r += 100;


%>
<div style="padding: 10px; background: orange; border: red 3px solid;">
	<img alt="" src="http://loremflickr.com/320/<%=r %>" >
	
</div>
</body>
</html>