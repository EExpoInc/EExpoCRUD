package eexpocrud.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eexpocrud.CrudfyUtils;
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
	
	
	
	
	public CrudfyData(HttpServletRequest request, HttpServletResponse response) {
		
		this.request = request;
		this.response = response;
		
		
//		System.out.println(request.getQueryString());
//		System.out.println(request.getParameterMap());
		CrudfyUtils.populate(request.getParameterMap(), this);
		this.bo = new CrudfyBO(resolveEExpoCrudCfg(request, response).jpaDao.em, resolveEExpoCrudCfg(request, response).jpaDao.entityClass);
		
		this.entityIdObj = CrudfyUtils.parseFromString(this.entityId, resolveEExpoCrudCfg(request, response).jpaDao.idClass);
		this.entityClass = eexpoCrudCfg.jpaDao.entityClass;

		EExpoButtonCfg<?> btnCfg =  eexpoCrudCfg.listPageCfg.groupBtn.actionName_Btn.get(act);
		
		ActionPrepared<? extends Object> ap ;
		btnCfg.invokable.bo = bo;
		btnCfg.invokable.entityId = entityIdObj;
		btnCfg.invokable.entity(bo.read(btnCfg.invokable.entityId));
		
		if(prepare){
			ap =  (ActionPrepared<?>) btnCfg.invokable; 	
			this.entity = ap.prepare();
			request.setAttribute(ActionPrepared.ENTITY_ATTR, this.entity);
			request.setAttribute(ActionPrepared.ACTION_FORM_ATTR, btnCfg.link(ap.entityId, request));  
			CrudfyUtils.simpleDispatch(ap.jspToDispatch, request, response);
//			CrudfyUtils.simpleDispatch("./editPrepare.jsp", request, response);
		}else{
//			this.eexpoCrudCfg.
			
			btnCfg.invokable.entity(request.getParameterMap(), btnCfg.invokable.entity);
			this.entity = btnCfg.invokable.action();
			
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
