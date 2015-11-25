package eexpocrud.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eexpocrud.action.CrudfyServlet2.ACT;
import eexpocrud.action.CrudfyServlet.PARAMS;
import eexpocrud.cfg.EExpoCrudCfg;
import eexpocrud.cfg.EExpoCrudCfgManager;


public class ControlDataInputOLD {
	Map<String,String[]> params;
	public String entityId;
	public String entityType;
	public String crudCfgId;
//	public ACT act;
	public String act;
	public HttpServletRequest req;
	public HttpServletResponse resp;
	public int qtde;
	public Object obj;
	public EExpoCrudCfg< ?, ?>  crudCfg;
	
	
	public ControlDataInputOLD(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
		this.params = this.req.getParameterMap();
		extractDataControl();
		populateObj();
//		this.crudCfg = resolveCrudCfg();
	}
	
	private void populateObj() {
	}

	private void extractDataControl() {
////		this.act = ACT.list;
//		this.entityId = null;		
//		for (ACT a : ACT.values()) {
//			this.entityId = params.get(a.name())!=null? params.get(a.name())[0] : null;
//			if (this.entityId != null) {				
//				this.act = a;
////				this.persistenceUnit = params.get(PARAMS.pu.name())[0];
//				break;
//			}
//		} 
	}
//	private EExpoCrudCfg< ?, ?>  resolveCrudCfg(){
//		String cfgId =  this.req.getParameter(PARAMS.CRUD_CFG_ID.name());
//		EExpoCrudCfgManager<?,?> man = new EExpoCrudCfgManager<>();
//		EExpoCrudCfg< ?, ?>  cfg =man.getFromSession(this.req, cfgId); 
//		cfg.refresh(this.req, this.resp);
//		return cfg;
//		return 
//		return (EExpoCrudCfg<?, ?>) req.getSession().getAttribute(cfgId);
//	}

	

	
}