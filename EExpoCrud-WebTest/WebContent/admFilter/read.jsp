<!DOCTYPE html>
<%@page import="test.FilterImgUrlEntity"%>
<%@page import="test.UtilsBean"%>
<%@page import="test.AdmFiltersAction"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
	FilterImgUrlEntity e = new FilterImgUrlEntity();
	Long id = Long.parseLong(request.getParameter("id"));
	e = AdmFiltersAction.read(id);
%>



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

<title>Crud Filter</title>
</head>
<body>

	<div class="container">
		<div class="row-sm-4">
			<h3>Crud Filter</h3>
		</div>

		<div class="row-sm-4">

			<form role="form" action="" method="get">

				

				<div class="form-group">
					<label for="id">Id:</label> <input type="text" id="id" name="id" value="<%=e.id%>" readonly="readonly" class="form-control" />
				</div>


				<div class="form-group">
					<label for="nome">Nome:</label> <input type="text" readonly="readonly" 
						class="form-control" id="nome" name="nome" value="<%=e.nome%>">
				</div>
				<div class="form-group">
					<label for="filter">Filter:</label> <input type="text" readonly="readonly" 
						class="form-control" id="filter" name="filter"
						value="<%=e.filter%>">
				</div>

				<div class="checkbox">
					<label for="remove"> <input type="checkbox" id="remove" disabled="disabled"
						name="remove" <%=(e.remove ? "checked" : "")%> value="true">
						To Remove?
					</label>
				</div>

				<div class="form-group">
					<label for="replace">Replace:</label> <input type="text" readonly="readonly" 
						class="form-control" id="replace" name="replace"
						value="<%=e.replace%>">
				</div>


			</form>

		</div>
	</div>
</body>
</html>