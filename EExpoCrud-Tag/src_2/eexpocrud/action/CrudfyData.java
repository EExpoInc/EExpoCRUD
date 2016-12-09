package eexpocrud.action;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eexpocrud.CrudfyUtils;
import eexpocrud.CrudfyUtils.GenericParseException;
import eexpocrud.bo.CrudfyBO;
import eexpocrud.cfg.ActionPrepared;
import eexpocrud.cfg.EExpoButtonCfg;
import eexpocrud.cfg.EExpoCrudCfg;
import eexpocrud.cfg.EExpoCrudCfgManager;

public class CrudfyData {
	String act;
	String entityId;
	Object entityIdObj;
	Object entity;
	Class<?> entityClass;
	Boolean prepare = false;
	String crudCfgId;
	HttpServletRequest request;
	HttpServletResponse response;
	private EExpoCrudCfg< ?, ?> eexpoCrudCfg;
	CrudfyBO bo;
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CrudfyData(HttpServletRequest request, HttpServletResponse response) {
		
		this.request = request;
		this.response = response;
		
		try {
			request.setCharacterEncoding(StandardCharsets.UTF_8.name());

		} catch (UnsupportedEncodingException e2) {

//			e2.printStackTrace();
		}
		
		CrudfyUtils.populate(request.getParameterMap(), this);
		this.bo = new CrudfyBO(resolveEExpoCrudCfg(request, response).jpaDao.em, resolveEExpoCrudCfg(request, response).jpaDao.entityClass);
		this.entityClass = eexpoCrudCfg.jpaDao.entityClass;
		EExpoButtonCfg<?> btnCfg =  eexpoCrudCfg.listPageCfg.groupBtn.actionName_Btn.get(act);
		ActionPrepared<? extends Object> ap ;
		btnCfg.invokable.bo = bo;
		
		
		
		
		try{
			this.entityIdObj = CrudfyUtils.parseFromString(this.entityId, resolveEExpoCrudCfg(request, response).jpaDao.idClass);
			btnCfg.invokable.entityId = entityIdObj;
			btnCfg.invokable.entity(bo.read(btnCfg.invokable.entityId));

		}catch(GenericParseException e){
			 this.entityIdObj = null;
			 try {
				this.entity = this.entityClass.newInstance();
				btnCfg.invokable.entityId = null;
				btnCfg.invokable.entity(this.entity);
			} catch (InstantiationException | IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		

		
		

		
		
		if(prepare){
			ap =  (ActionPrepared<?>) btnCfg.invokable; 	
			this.entity = ap.prepare();
			request.setAttribute(ActionPrepared.ENTITY_ATTR, eexpoCrudCfg.showAsView(this.entity));
			request.setAttribute(ActionPrepared.ACTION_FORM_ATTR, btnCfg.link(ap.entityId, request));  
			CrudfyUtils.simpleDispatch(ap.jspToDispatch, request, response);
		}else{
			btnCfg.invokable.entity(request.getParameterMap(), btnCfg.invokable.entity);
			this.entity = btnCfg.invokable.action();
			CrudfyUtils.simpleDispatch("./crudfyRefresh.jsp", request, response);
			 
		}
	}

 
	public EExpoCrudCfg< ?, ?> eexpoCrudCfg(){
		return this.eexpoCrudCfg;
	}

	public static EExpoCrudCfg< ?, ?> eexpoCrudCfg(String crudCfgId, HttpServletRequest request, HttpServletResponse response) {
		EExpoCrudCfgManager<?,?> man = new EExpoCrudCfgManager<>();
		EExpoCrudCfg< ?, ?>  cfg =man.getFromSession(request, crudCfgId); 
		cfg.refresh(request, response);
		return cfg;			

		
	}
	public EExpoCrudCfg< ?, ?> resolveEExpoCrudCfg(HttpServletRequest request, HttpServletResponse response) {
		if(eexpoCrudCfg == null){
			return this.eexpoCrudCfg = eexpoCrudCfg(crudCfgId, request, response);
		}else{
			return this.eexpoCrudCfg;
		}
	}
	
}
