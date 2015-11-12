<!DOCTYPE html>
<%@page import="eexpocrudOLD.CrudfyTagHelper"%>
<%@page import="test.FilterImgUrlEntity"%>
<%@page import="test.UtilsBean"%>
<%@page import="test.AdmFiltersAction"%>
<%@page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>


<%
	request.setCharacterEncoding("UTF-8");
	Object obj =  request.getAttribute(CrudfyTagHelper.att.obj+"");
	FilterImgUrlEntity e = null;
%>



<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
   	<link   href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

	<title>Crud Filter</title>
	
	<%if(true){ %>
		<script>
			window.parent.location = window.parent.location; 
		</script>
	<%}%>
</head>
<body>

	<div class="container">
		<div class="row">
			<h3>Edit</h3>
		</div>
 
		<div class="row"> 

			<form role="form" action="" method="post" accept-charset="UTF-8" class="editForm">

				<input type="hidden" id="id" name="id" value="<%=e.id%>" />

				<div class="form-group">
					<label for="nome">Nome:</label> <input type="text"
						class="form-control" id="nome" name="nome" value="<%=e.nome%>">
				</div>
				<div class="form-group">
					<label for="filter">Filter:</label> <input type="text"
						class="form-control" id="filter" name="filter" value="<%=e.filter%>">
				</div>

				<div class="checkbox">
					<label for="remove">
						<input type="checkbox" id="remove"  name="remove"   <%=(e.remove ? "checked" : "")%> value="true"> To Remove? 
					</label> 
				</div>

				<div class="form-group">
					<label for="replace">Replace:</label> <input type="text"
						class="form-control" id="replace"  name="replace" value="<%=e.replace%>">
				</div>
				
				
				<div class="form-group pull-right">
					<button type="submit" class="btn btn-success" name="act"
						value="ok"><span class="glyphicon glyphicon-ok-sign"></span> Ok</button>
				</div>
			</form>
			<script>
				$("form.editForm :input").each(
					function(){
						$(this).clone().attr({
							id: this.id+"_ORIGINAL",
							name: this.name+"_ORIGINAL"
						}).hide().appendTo($(this.closest("form")));
					}
				);
			</script>
		</div>
	</div>
</body>
</html>