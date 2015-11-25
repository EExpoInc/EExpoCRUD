<!DOCTYPE html>

<%@page import="eexpocrud.dao.impl.jpa.two.JpaDAO"%>
<%@page import="eexpocrud.dao.impl.jpa.test.PageEntity"%>
<%@page import="eexpocrud.cfg.EExpoButtonCfg.ButtonBootstrapCssClass"%>
<%@page import="eexpocrud.cfg.EExpoRowButtonCfg"%>

<%@page import="eexpocrud.cfg.ConditionalI"%>
<%@page import="eexpocrud.CrudfyUtils"%>
<%@page import="eexpocrud.form.PostEntityForm"%>
<%@page import="eexpocrud.cfg.EExpoCrudCfg"%>
<%@page import="eexpocrud.dao.impl.jpa.test.PostEntity"%>
<%@page import="eexpocrud.dao.impl.jpa.test.PostEntity"%>
<%@page import="eexpocrud.action.ListActionData"%>
<%@page import="eexpocrud.tag.CrudfyTagHelper"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="test.FilterImgUrlEntityDAOImpl"%>
<%@page import="test.FilterImgUrlEntity"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="crudfy" uri="http://eexponews.com/crudfy"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="//code.jquery.com/jquery-2.1.1.min.js"></script>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<title>Crud Filter</title>
</head>
<body>

	<div class="container">
		<div class="row">
			<h3>EExpoCRUD - eexponews</h3> 
		</div> 
  
			<% String persistenceUnit = "EExpoCrud-JPA2.0"; %>

  		 <crudfy:list jpaDao="<%=new JpaDAO<PageEntity, String>(PageEntity.class, String.class, persistenceUnit, 30)%>">
  		 </crudfy:list>
	

  	 




	</div>
</body>
</html>