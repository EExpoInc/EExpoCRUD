<!DOCTYPE html>

<%@page import="org.apache.tomcat.util.http.ContentType"%>
<%@page import="eexpocrud.dao.impl.jpa.test.UserEntity"%>
<%@page import="eexpocrud.cfg.EExpoCrudCfg"%>
<%@page import="eexpocrud.dao.impl.jpa.test.PostEntity"%>
<%@page import="eexpocrud.action.ListActionData"%>
<%@page import="eexpocrud.tag.CrudfyTagHelper"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="eexpocrud.dao.impl.jpa.JpaDAO"%>
<%@page import="test.FilterImgUrlEntityDAOImpl"%>
<%@page import="test.FilterImgUrlEntity"%>
<%@ page language="java" contentType="text/plain; charset=US-ASCII"
	pageEncoding="US-ASCII"%>

<%@taglib prefix="crudfy" uri="http://eexponews.com/crudfy"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Crud Filter</title>
</head>
<body>

		<%
			String persistenceUnit = "EExpoCrud-JPA2.0";
				response.setHeader("EExpoTimestamp", System.currentTimeMillis()+"");
		%>
		
		
  		 	<crudfy:list jpaDao="<%=new JpaDAO<UserEntity, Integer>(UserEntity.class, Integer.class, persistenceUnit, 1000)%>">
			</crudfy:list>   
  		




</body>
</html>