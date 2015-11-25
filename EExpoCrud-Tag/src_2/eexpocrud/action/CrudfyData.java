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
	Boolean prepare = false;
	String crudCfgId;
	HttpServletRequest request;
	HttpServletResponse response;
	private EExpoCrudCfg< ?, ?> eexpoCrudCfg;
	CrudfyBO bo;
	
	
	
	
	public CrudfyData(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		CrudfyUtils.populate(request.getParameterMap(), this);
		
		this.entityIdObj = CrudfyUtils.parseFromString(this.entityId, eexpoCrudCfg().jpaDao.idClass);
		this.bo = new CrudfyBO(eexpoCrudCfg().jpaDao.em, eexpoCrudCfg().jpaDao.entityClass);

		EExpoButtonCfg<?> btnCfg =  eexpoCrudCfg.listPageCfg.groupBtn.actionName_Btn.get(act);
		
		ActionPrepared<? extends Object> ap ;
		btnCfg.invokable.bo = bo;
		btnCfg.invokable.entityId = entityIdObj;
		btnCfg.invokable.entity(bo.read(btnCfg.invokable.entityId));
		
		if(prepare){
			ap =  (ActionPrepared<?>) btnCfg.invokable;
			this.entity = ap.prepare();
		}else{
			
			btnCfg.invokable.action();
		}
	}




	public EExpoCrudCfg< ?, ?> eexpoCrudCfg(){
		if(eexpoCrudCfg == null){
			EExpoCrudCfgManager<?,?> man = new EExpoCrudCfgManager<>();
			EExpoCrudCfg< ?, ?>  cfg =man.getFromSession(request, crudCfgId); 
			cfg.refresh(request, response);
			this.eexpoCrudCfg = cfg;
			return cfg;			
		}else{
			return this.eexpoCrudCfg;
		}
	}
	
}
