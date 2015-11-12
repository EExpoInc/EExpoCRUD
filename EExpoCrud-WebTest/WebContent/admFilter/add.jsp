<!DOCTYPE html>
<%@page import="test.FilterImgUrlEntity"%>
<%@page import="test.UtilsBean"%>
<%@page import="test.AdmFiltersAction"%>
<%@page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>

<%
	boolean haveAct = false;
	request.setCharacterEncoding("UTF-8");
	if (request.getParameter("act") != null && request.getParameter("act").equals("ok")) {
		AdmFiltersAction.create(request);
		haveAct = true;
		//response.sendRedirect("./index.jsp");
	} 
	int tabIndex= 1;
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

			<form  role="form" action="" method="post" accept-charset="UTF-8" >



				<div class="form-group">
					<label for="nome">Nome:</label> <input type="text"  autofocus="autofocus"
						class="form-control" id="nome" name="nome" placeholder="nome" tabindex="<%=tabIndex++%>">
						
				</div>

				<div class="form-group">
					<label for="filter">Filter:</label> <input type="text"
						class="form-control" id="filter" name="filter" tabindex="<%=tabIndex++%>">
				</div>

				
				<div class="checkbox">
					<label for="remove">
						<input type="checkbox" id="remove" name="remove" value="true" tabindex="<%=tabIndex++%>" /> To Remove? 
					</label> 
				</div>

				<div class="form-group">
					<label for="replace">Replace:</label> <input type="text"
						class="form-control" id="replace" name="replace" tabindex="<%=tabIndex++%>">
				</div>
				

				<!-- <button type="submit" class="btn btn-default" name="act" 
					value="ok">Confirm</button> -->
					
				<div class="form-group pull-right">
					<button type="submit" class="btn btn-success" name="act" tabindex="<%=tabIndex++%>"
						value="ok"><span class="glyphicon glyphicon-ok-sign"></span> Ok</button>
				</div>
					
			</form>

		</div>
	</div>
</body>
</html>