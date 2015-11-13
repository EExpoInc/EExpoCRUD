<!DOCTYPE html>

<%@page import="eexpocrud.dao.impl.jpa.two.QueryCfg.WhereEnum"%>
<%@page import="eexpocrud.dao.impl.jpa.two.QueryCfg.Where"%>
<%@page import="eexpocrud.dao.impl.jpa.two.JpaDAO"%>
<%@page import="eexpocrud.cfg.EExpoButtonCfg.ButtonBootstrapCssClass"%>
<%@page import="eexpocrud.cfg.EExpoRowButtonCfg"%>
<%@page import="eexpocrud.cfg.EExpoButtonCfg.ActionableI"%>
<%@page import="eexpocrud.cfg.ConditionalI"%>
<%@page import="eexpocrud.CrudfyUtils"%>
<%@page import="eexpocrud.form.PostEntityForm"%>
<%@page import="eexpocrud.cfg.EExpoCrudCfg"%>
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
				JpaDAO<PostEntity, Integer> jpaDAO = new JpaDAO<PostEntity, Integer>(PostEntity.class, Integer.class, persistenceUnit);
				 
				final EExpoCrudCfg<PostEntity, Integer> cfg = new EExpoCrudCfg<PostEntity, Integer>(request, response, jpaDAO); 
				
				/*** SUPORTE A CLASSE Q ESTENDE O OBJ ORIGINAL P MELHORAR A APRESENTACAO ***/
				final EExpoRowButtonCfg<PostEntity> coracaoBtn = new EExpoRowButtonCfg<PostEntity>("custom2",
				"glyphicon glyphicon-heart", null);
				
				coracaoBtn.action(new ActionableI<PostEntityForm>() {
			@Override
			public PostEntityForm action() {
				PostEntityForm pef = new PostEntityForm();
				CrudfyUtils.beanTransfusion(pef, coracaoBtn.actualEntityRow);
				return pef;
			}
				});
				
				coracaoBtn.visible(new ConditionalI() {
			@Override
			public boolean execute() {
				return coracaoBtn.actualEntityRow.id < 8 ? true : false;
			}
				});
				
				cfg.listPageCfg().groupBtn.rowBtns.add(coracaoBtn);
				
				
				
				final EExpoRowButtonCfg<PostEntity> fogoBtn = new EExpoRowButtonCfg<PostEntity>("eh fogo",
				"glyphicon glyphicon-fire", null);
				
				fogoBtn.action(new ActionableI<PostEntityForm>() {
			@Override
			public PostEntityForm action() {
				PostEntityForm pef = new PostEntityForm();
				CrudfyUtils.beanTransfusion(pef, fogoBtn.actualEntityRow);
				return pef;
			}
				}).buttonCssClass(ButtonBootstrapCssClass.success);
				
				fogoBtn.visible(new ConditionalI() {
			@Override
			public boolean execute() { 
				return fogoBtn.actualEntityRow.id > 4  & fogoBtn.actualEntityRow.id < 20 ? true : false;
			}
				}).disable(new ConditionalI(){
			public boolean execute(){
				return fogoBtn.actualEntityRow.id > 9 ? true : false;
			}
				});
				
				cfg.listPageCfg().groupBtn.rowBtns.add(fogoBtn);
				cfg.listPageCfg().queryCfg.addWhere("id", WhereEnum.gt, 3);
				cfg.listPageCfg().queryCfg.addWhere("id", WhereEnum.le, 33); 
				cfg.listPageCfg().queryCfg.addWhere("status", WhereEnum.notIn, PostEntity.STATUS.restrito, PostEntity.STATUS.castigo);
				
				cfg.listPageCfg().limitList(5);
		%>

  		 <crudfy:list jpaDao="<%=jpaDAO%>" crudCfg="<%=cfg %>">
		</crudfy:list>   
 




	</div>
</body>
</html>