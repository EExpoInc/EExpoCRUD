<!DOCTYPE html>
<%@page import="java.util.List"%>
<%@page import="test.AdmFiltersAction"%>
<%@page import="test.FilterImgUrlEntity"%>
<%@page import="java.util.ArrayList"%> 
<%@page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
    
<%List<FilterImgUrlEntity> filtros = AdmFiltersAction.list(); %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
   	<link   href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <title>Crud Filter</title>
</head>
<body>

<div class="container">
    <div class="row">
	    <h3>Crud Filter</h3>
    </div>

    <div class="row">


		<%if(filtros == null){%>
			<h4>Sem filtros Cadastrados</h4>
		<%}else{%>
		
		 <div class="form-group pull-right">
			 <p><a href="#" class="btn btn-success btn-s" data-toggle="modal" data-target="#detailModal" data-iframe-src="./add.jsp" title="Add"><span class="glyphicon glyphicon-plus"></span>Add</a></p>
		 </div>
		
		<table class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th class="text-center">Adm</th>
					<th>Nome</th>
					<th>Regra</th>
					<th>Remove?</th>
					<th>Replace</th>					
					<th>Id</th>
				</tr>	
			</thead>
			<tbody>
				<%for(FilterImgUrlEntity e: filtros){ %>
					<tr class="small">
						<td class="text-center  col-lg-1 col-md-2 col-sm-2">						
							<a href="#" class="btn btn-primary btn-xs" data-toggle="modal" data-target="#detailModal" data-iframe-src="./read.jsp?id=<%=e.id%>" title="Read"><span class="glyphicon glyphicon-info-sign"></span> </a>							
							<a href="#" class="btn btn-warning btn-xs" data-toggle="modal" data-target="#detailModal" data-iframe-src="./edit.jsp?id=<%=e.id%>"  title="Update"><span class="glyphicon glyphicon-edit"></span> </a>							
							<a href="#" class="btn btn-danger btn-xs" data-toggle="modal" data-target="#detailModal" data-iframe-src="./exclude.jsp?id=<%=e.id%>"  title="Delete"><span class="glyphicon glyphicon-remove-sign"></span> </a>								
						 </td>
					  
						<td><%=e.id%></td>
						<td><%=e.nome%></td>
						<td><%=e.filter%></td>
						<td><%=e.remove%></td>
						<td><%=e.replace%></td>
					</tr>
				<%}%>
			</tbody>
		</table>
		<%}%>
	</div>
	
	<div class="modal fade " tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" id="detailModal">
  		<div class="modal-dialog ">
	    	<div class="modal-content">
			   	<div class="modal-header">			   	
			   		<button type="button" class="btn btn-danger btn-xs pull-right" data-dismiss="modal" aria-label="Close">
			   			<span aria-hidden="true" class="glyphicon glyphicon-remove"></span> 
			   		</button>			   		
		        	<h4 class="modal-title" id="modal-title"></h4>
		      	</div>
				<div class="modal-body">	    			    	    
			    	<div class="embed-responsive embed-responsive-4by3">		    		
		  				<iframe class="embed-responsive-item" id="iframeDetail"  ></iframe>
					</div>
				</div>
	   	 	</div>
	  	</div>
	</div>
	
	
	<script>
	
		$('#detailModal').on('show.bs.modal', function (event) {
			  var button = $(event.relatedTarget); // Button that triggered the modal
			  var recipient = button.data('iframe-src'); // Extract info from data-* attributes
			  var modalTitle = button.attr('title');
			  var modal = $(this);
//			  modal.find('#iframeDetail').attr('src', modal.find('#iframeDetail').attr('data-srcTemplate')+ recipient);
			  modal.find('#iframeDetail').attr('src',  recipient);
			  modal.find('#modal-title').html(modalTitle);
			  
/* 			  modal.on('shown', function () {
				  modal.find('input:text')[0].focus();
				    //$('#textareaID').focus();
				});
 */			 
			  
 				/* $('iframeDetail').contents().find('input:text')[0].focus(); */
 
 				//alert($('iframeDetail').contents().first('input:text').text());
 				
 				  /* $('#iframeDetail').ready(function() {
 					 alert(  $('#iframeDetail').contents().find('#nome').text());
					});   */
 				  
 				  
 				 /* $('#iframeDetail').on('load',function(){
 				      jQuery(this).contents().find('#nome').attr('value', 'Some text');
 				      jQuery(this).contentWindow.document.body.focus();
 				     jQuery(this).contents().find('#nome').focus();
 				     setTimeout(function(){jQuery(this).contents().find('#nome').focus();}, 100);
 				}); */
 				
/*  				$('#iframeDetail').ready(function() {
 					this.contents().find('body').html('<div> blah </div>');
 				});
 */					
		});
	</script>
	
	
</div>
</body>
</html>