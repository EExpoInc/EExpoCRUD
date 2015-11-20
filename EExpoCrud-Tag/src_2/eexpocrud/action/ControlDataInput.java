package eexpocrud.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eexpocrud.action.CrudfyServlet.ACT;
import eexpocrud.action.CrudfyServlet.PARAMS;


public class ControlDataInput {
	Map<String,String[]> params;
	public String entityId;
	public String entityType;
	public String crudCfgId;
	public ACT act;
	public HttpServletRequest req;
	public HttpServletResponse resp;
//	String persistenceUnit;
	public int qtde;
	public Object obj;
	
	public ControlDataInput(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
		this.params = this.req.getParameterMap();
		extractDataControl();
		populateObj();
	}
	
	private void populateObj() {
	}

	private void extractDataControl() {
		// DataControl result = new DataControl(req, resp);
		this.act = ACT.list;
		this.entityId = null;
		
//		this.type = this.req.getParameter(PARAMS.type.name());
		this.entityType = params.get(PARAMS.type.name())!=null? params.get(PARAMS.type.name())[0] : null;
		this.crudCfgId = params.get(PARAMS.CRUD_CFG_ID.name())!=null? params.get(PARAMS.CRUD_CFG_ID.name())[0] : null;
		for (ACT a : ACT.values()) {
			this.entityId = params.get(a.name())!=null? params.get(a.name())[0] : null;
			if (this.entityId != null) {				
				this.act = a;
//				this.persistenceUnit = params.get(PARAMS.pu.name())[0];
				break;
			}
		} 
	}
	
	

	
}