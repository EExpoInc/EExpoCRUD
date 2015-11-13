<!DOCTYPE html>

<%@page import="eexpocrud.dao.impl.jpa.two.JpaDAO"%>
<%@page import="eexpocrud.cfg.EExpoButtonCfg.ActionableI"%>
<%@page import="eexpocrud.cfg.ConditionalI"%>
<%@page import="eexpocrud.cfg.EExpoRowButtonCfg"%>
<%@page import="eexpocrud.cfg.EExpoButtonCfg"%>
<%@page import="eexpocrud.cfg.EExpoCrudCfg"%>
<%@page import="eexpocrud.dao.impl.jpa.test.UserEntity"%>
<%@page import="eexpocrud.dao.impl.jpa.test.PostEntity"%>
<%@page import="eexpocrud.action.ListActionData"%>
<%@page import="eexpocrud.tag.CrudfyTagHelper"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.Persistence"%>

<%@page import="test.FilterImgUrlEntityDAOImpl"%>
<%@page import="test.FilterImgUrlEntity"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>

<%@taglib prefix="crudfy" uri="http://eexponews.com/crudfy"%>

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
		<div class="row">
			<h3>Crud Filter</h3>
		</div>

		<%
			String persistenceUnit = "EExpoCrud-JPA2.0";
			
				JpaDAO<UserEntity, Integer> jpaDAO = new JpaDAO<UserEntity, Integer>(UserEntity.class, Integer.class, persistenceUnit);
				
				final EExpoCrudCfg<UserEntity, Integer> cfg = new EExpoCrudCfg<UserEntity, Integer>(request, response, jpaDAO); 
			
			 
			final EExpoRowButtonCfg<UserEntity> myCustomBtn = new EExpoRowButtonCfg<UserEntity>("custom",
					"glyphicon glyphicon-chevron-up", new ActionableI<UserEntity>() {
						@Override
						public UserEntity action() {
							
							return null;
						}
					}); 
			
			myCustomBtn.visible(new ConditionalI() {
				@Override 
				public boolean execute() {
					return myCustomBtn.actualEntityRow.id < 13 ? true : false;
				}
			}).disable(new ConditionalI() {
				@Override
				public boolean execute() {
					return myCustomBtn.actualEntityRow.id > 9 ? true : false;
				}
			});			 
			cfg.listPageCfg().groupBtn.rowBtns.add(myCustomBtn);
			
			cfg.listPageCfg().groupBtn.deleteBtn().disable(new ConditionalI() {
				@Override
				public boolean execute() {
					return cfg.listPageCfg().groupBtn.deleteBtn().actualEntityRow.id < 4 ? true : false;
				}
			} );
		%>

  		 <crudfy:list jpaDao="<%=jpaDAO%>" crudCfg="<%=cfg%>">
		</crudfy:list>   
 




	</div>
</body>
</html>