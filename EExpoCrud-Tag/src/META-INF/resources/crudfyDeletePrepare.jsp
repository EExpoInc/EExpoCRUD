<%@page import="eexpocrud.cfg.ActionPrepared"%>
<%@page import="eexpocrud.action.CrudfyServlet"%>
<%@page import="eexpoform.FormBase"%>
<%@page import="eexpoforms.test.Main"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%-- <%@taglib prefix="ex" uri="http://eexponews.com/eexpoform"%> --%>
<%@taglib prefix="ex" uri="http://eexponews.com/eexpoform"%>

<%Object entity = request.getAttribute(ActionPrepared.ENTITY_ATTR);%>
<%String actionForm =  (String) request.getAttribute(ActionPrepared.ACTION_FORM_ATTR);  %>


<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<link
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
 -->	
 
 <%@include file="resources.jsp" %>
 
 <title>Delete</title>
</head>
<body> 

	<div class="container">
		<div class="row">
			<h3>Delete</h3>
		</div>
		<ex:formBase bean="<%=entity%>" action="<%=actionForm%>" readOnly="<%=true %>"/>
		
	</div>
</body>
</html>