<!DOCTYPE html>
<%@page import="test.FilterImgUrlEntity"%>
<%@page import="test.UtilsBean"%>
<%@page import="test.AdmFiltersAction"%>
<%@page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>

<%
	boolean haveAct = false;
	FilterImgUrlEntity e = new FilterImgUrlEntity();  
	if (request.getParameter("act") != null && request.getParameter("act").equals("ok")) {
		haveAct = true;
		Long id = Long.parseLong(request.getParameter("id"));
		AdmFiltersAction.delete(id);
		//response.sendRedirect("./index.jsp");		
	} else{
		Long id = Long.parseLong(request.getParameter("id"));
		e = AdmFiltersAction.read(id);
	}
%>


<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
   	<link   href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

	<%if(haveAct){ %>
		<script>
			if(window.parent.location != window.location ){				
				window.parent.location = window.parent.location;	
			}else{
				window.location = './';
			}			 
		</script>
	<%}%>

<title>Crud Filter</title>
</head>
<body>

	<div class="container">
		<div class="row">
			<h3>Crud Filter</h3>
		</div>

		<div class="row">

			<form role="form" action="" method="get">

				<input type="hidden" id="id" name="id" value="<%=e.id%>" />

				<div class="form-group">
					<label for="nome">Nome:</label> <input type="text"
						class="form-control" id="nome" name="nome" value="<%=e.nome%>" disabled="disabled" >
				</div>
				<div class="form-group">
					<label for="filter">Filter:</label> <input type="text"
						class="form-control" id="filter" name="filter" value="<%=e.filter%>" disabled="disabled" >
				</div>

				<div class="form-group pull-right">
				
					<button type="submit" class="btn btn-danger" name="act" value="ok"><span class="glyphicon glyphicon-remove"></span> Delete</button>					
					<!-- <button type="submit" class="btn btn-success" name="act" value="ok"><span class="glyphicon glyphicon-ok-sign"></span> Delete</button> -->
				</div>

			</form>

		</div>
	</div>
</body>
</html>