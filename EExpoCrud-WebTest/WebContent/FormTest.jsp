<!DOCTYPE html>

<%@page import="eexpoforms.test.Main"%>
<%@page import="test.FilterImgUrlEntity"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@taglib prefix="ex" uri="http://eexponews.com/eexpoform"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<link
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<title>EExpoCRUD</title>
</head>
<body>

<%
Object entity = request.getAttribute("entity");

if(entity == null){
	entity = Main.genEntity();	
}%>


	<div class="container">
		<div class="row">
			<h3>EExpoForm</h3>
		</div>


		
		<ex:formBase bean="<%=entity%>" /> 




	</div>
</body>
</html>
