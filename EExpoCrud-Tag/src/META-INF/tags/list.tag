<%@tag import="eexpocrud.action.CrudfyServlet"%>
<%@tag import="eexpocrud.cfg.EExpoRowButtonCfg"%>
<%@tag import="eexpocrud.cfg.EExpoButton"%>
<%@tag import="eexpocrud.cfg.EExpoButtonCfg"%>
<%@tag import="eexpocrud.action.ListAction"%>
<%@tag import="java.security.SecureRandom"%>
<%@tag import="java.util.Random"%>
<%@tag import="eexpocrud.action.ListActionData.CrudObj"%>
<%@tag import="java.lang.reflect.Field"%>
<%@tag import="eexpocrud.action.ListActionData"%>
<%@tag import="eexpocrud.dao.impl.jpa.two.JpaDAO"%>
<%@tag import="eexpocrud.tag.CrudfyTagHelper"%>
<%@tag import="java.util.HashMap"%>
<%@tag import="java.util.List"%>
<%@tag language="java" pageEncoding="UTF-8" %>
 <%@ attribute name="jpaDao" required="true" type="eexpocrud.dao.impl.jpa.two.JpaDAO"%>
 <%@ attribute name="crudCfg" required="false" type="eexpocrud.cfg.EExpoCrudCfg"%>
 
 <!-- START EExPOCRUD  -->
  
 <script src="//cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js" > </script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css" rel="stylesheet" />
 

<style>

	td.readAct:hover{
		cursor: pointer;
	}
	tr.crudfyRow button.crudfyRowBtn{
		margin-bottom: 2px;
	}
	

</style>
 
 
 <%
 	CrudfyTagHelper<?,?> tagHelper;
 	if(crudCfg != null){
 		tagHelper = new CrudfyTagHelper(request, response, crudCfg);
 	}else{
 		tagHelper = new CrudfyTagHelper(request, response, jpaDao);
 	}
 		
		ListActionData<?, ?> data = tagHelper.getTable();
		String modalId = "modalId_"+(new SecureRandom()).nextInt(1000);
		String pu = jpaDao.persistenceUnit;
%>



  
  

	<div class="panel panel-default">
		<div class="panel-heading clearfix">
			<div class="">
				<div class="btn-group  pull-left">					
					<h3 class="panel-title pull-left"> <%=jpaDao.entityClass.getSimpleName()%> </h3>												
				</div>
				
				
				<div class="pull-right">
					
<%-- 					<div class="btn-group <%= table.totalRecords== 0 ? "hidden" : ""%>"> 
						<button class="btn btn-primary" type="button">Total <span class="btn-primary badge" style="background-color: #FFF; color:#428BCA; ">
							<%=table.totalRecords%></span>
						</button>					
					</div>
 --%>					
					<div class="btn-group  <%=data.id_objMap.isEmpty()?"hidden": "" %>">
						<a href="#" class="btn btn-info" data-toggle="modal" data-target="#detailModal" data-iframe-src="./add.jsp" title="Get by Id"><span class="glyphicon glyphicon-screenshot"></span> Id</a> 
					</div>

					<div class="btn-group">
						<a href="#" class="btn btn-success" data-toggle="modal" data-target="#detailModal" data-iframe-src="./add.jsp" title="Add"><span class="glyphicon glyphicon-plus"></span> Add</a> 
					</div>
				</div>
			</div>
		</div> 
		
		<%if(data.id_objMap.isEmpty()){ %>
			<div class="panel-body text-center">	
				<h2>No Data here!</h2>
			</div>
		 
		<%}else{ %>
		
			<div class="panel-body">	
				<table class="table table-striped table-bordered table-hover " data-card-view="true"> 
					<thead> 
						<tr>
							<th class="text-center">Adm</th>
							<%for(Field f: data.fields){  %>
							<th><%=f.getName() %></th>
							<%} %>
						</tr>	
					</thead>
					<tbody>		
						<%for(CrudObj<?> obj: data.id_objMap.values()){%>
																		 
																		 
							<tr class="small crudfyRow" title="Read" data-iframe-src= "${index}" >
								<td class="text-center col-lg-1  col-md-1  col-xs-2 ">
																							  
									<% for(EExpoRowButtonCfg<?> btnCfg : tagHelper.cfg.listPageCfg().groupBtn.rowBtns()){
										EExpoButton<?> btn = new EExpoButton((EExpoRowButtonCfg)btnCfg, data.id_entityMap.get(obj.id));
										if(btn.visible()){%> 											
 											<button class="<%=btn.cfg.buttonCssClass%> <%= btn.disabled()?"disabled": "" %> crudfyRowBtn" 
 												<%if(!btn.disabled()){ %>
	 												data-toggle="modal" 
													data-target="#<%=modalId%>"
													data-iframe-src="<%=btn.cfg.linkPrepare(obj.id, request) %>"  													  
												<%} %>
												title="<%=btn.cfg.name%>">
												<span class="<%=btn.cfg.cssIcon.cssClass()%>"></span> 
											</button>
										<% }
									}
								%> 
								
															
									<%-- <a href="#" class="btn btn-primary btn-xs" data-toggle="modal" data-target="#<%=modalId% >" data-iframe-src="<=helper.genReadLink(obj.id)%>" title="Read"><span class="glyphicon glyphicon-info-sign"></span> </a> --%>							
									<%-- <a href="#" class="btn btn-primary btn-xs" data-toggle="modal" data-target="#<%=modalId% >" data-iframe-src="./admFilter/read.jsp?id=<%=obj.id%>" title="Read"><span class="glyphicon glyphicon-info-sign"></span> </a>--%>					
<%-- 									<a href="#" class="btn btn-warning btn-xs" data-toggle="modal" data-target="#<%=modalId% >" data-iframe-src="./?id=<%=obj.id%>"  title="Update">
										<span class="glyphicon glyphicon-edit"></span> </a>							
									<a href="#" class="btn btn-danger btn-xs" data-toggle="modal" data-target="#<%=modalId%>" data-iframe-src="./?id=<%=obj.id%>"  title="Delete">
										<span class="glyphicon glyphicon-remove-sign"></span> </a>								
 --%>								 </td>
							  <%for(Field f: data.fields){ %>
							  		<td class="readAct"><%=obj.field_ValueMap.get(f.getName())%></td> 
							  <%} %>
							</tr>
						<%}%>
					</tbody>
				</table>
			
			</div>
			
			
	<!-- Navegacao entre pags -->
			<div class="panel-footer text-center">
				
	 			<nav>
					<ul class="pagination pagination-lg">
					 	<li>
<%-- 					    	<a href="?<%=ListAction.params.begin.name()%>"> --%>
					    	<a href="<%=tagHelper.genBeginLink()%>">
					    		<span aria-hidden="true"> &Lang;</span>
					    	</a>
					    </li>
					
					
					<li >
<%-- 	 				    	<a href="?before=<%=data.getTopIdEscaped()%>"> --%>
	 				    	<a href="<%=tagHelper.genBeforeLink()%>">
					    		<span aria-hidden="true">&lang;</span>
					    	</a> 
					    </li>
	<!-- 				    <li >
					    	<a href="http://eexponews.com">
					    		<span aria-hidden="true">&exponentiale;&xscr;</span>
					    	</a>
					    </li>
	 --> 				    
	 					<li>	 						
					    	<%-- <a href="?after=<%=data.getBottomIdEscaped()%>"> --%>
					    	<a href="<%=tagHelper.genAfterLink()%>">
					    		<span aria-hidden="true">&rang;</span>
					    	</a>
					    </li>
					    
	 					<li>
					    	<%-- <a href="?<%=ListAction.params.end.name()%>"> --%>
					    	<a href="<%=tagHelper.genEndLink()%>">
					    		<span aria-hidden="true">&Rang;</span>
					    	</a>
					    </li>
					    
				    </ul>
				  </nav> 			
			</div>
	</div> 
	
	
	
	
	
	
	
	
	
	
		<div class="modal fade " tabindex="-1" 
				role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" id="<%=modalId%>" data-setted="false">
  		<div class="modal-dialog">
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
	
		//interrompe a chamada do modal e seta os valores novos da div.
		$('#<%=modalId%>').on('show.bs.modal', function (event) {
			  var button = $(event.relatedTarget);
			  //alert($(this).data('setted'));
			  if($(this).data('setted') == false){
				  var recipient = button.data('iframe-src'); 
				  var modalTitle = button.attr('title');
				  var modal = $(this);
				  modal.find('#iframeDetail').attr('src',  recipient);
				  modal.find('#modal-title').html(modalTitle);						  
			  }
		});
		$('#<%=modalId%>').on('hidden.bs.modal', function () {
			var modal = $(this);
			modal.find('#iframeDetail').attr('src',  'about:blank');
		})


		$("table.table-hover").on("click", "td.readAct", function(event) {
			var button = $(this);
			var modalSrc = button.parent().data('iframe-src');
			button.data('iframe-src', modalSrc);
			var modalTitle = button.parent().attr('title');
			button.attr('title', modalTitle);
			var modal = $('#<%=modalId%>');
			
			modal.data('setted', true); 
			modal.find('#iframeDetail').attr('src',  modalSrc);
			modal.find('#modal-title').html(modalTitle);
			$('#<%=modalId%>').modal('show');
			modal.data('setted', false);
		});

			function showAlert(msg) {
				toastr.options = {
					"closeButton" : false,
					"debug" : false,
					"newestOnTop" : false,
					"progressBar" : false,
					"positionClass" : "toast-top-center",
					"preventDuplicates" : true,
					"onclick" : null,
					"showDuration" : "300",
					"hideDuration" : "1000",
					"timeOut" : "3000",
					"extendedTimeOut" : "1000",
					"showEasing" : "swing",
					"hideEasing" : "linear",
					"showMethod" : "fadeIn",
					"hideMethod" : "fadeOut"
				}
				toastr.warning(msg, "EExpoCrud");
			}
		<%if(data.hitBegin){%>
			showAlert('Begin Hitted.');
		<%}%>

		<%if(data.hitEnd){%>
			showAlert('End Hitted.');
		<%}%>

		
	</script>
	 <%} %> 
		
	

