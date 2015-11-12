package eexpocrud.action;

import javax.servlet.http.HttpServletRequest;

import app.controller.CrudfyServlet.ACT;
import app.controller.CrudfyServlet.PARAMS;

public class ControlDataInput {
	public HttpServletRequest req;
//	public HttpServletResponse resp;
	public String id;
	public String type;
	public ACT act;
	public CrudfyNavDataInput nav;
	public int qtde;
	public Object obj;
	
	public ControlDataInput(HttpServletRequest req) {
		this.req = req;
		this.nav = new CrudfyNavDataInput();
		extractDataControl();
		populateObj();
	}
	
	private void populateObj() {
		if(id != null){
			
		}
		
	}

	private void extractDataControl() {
		// DataControl result = new DataControl(req, resp);
		this.act = ACT.list;
		this.id = null;
		this.type = this.req.getParameter(PARAMS.type.name());
		for (ACT a : ACT.values()) {
			if (this.req.getParameter(a.name()) != null) {				
				this.id = this.req.getParameter(a.name());
				this.act = a;
				break;
			}
		}
	}
	

	
}