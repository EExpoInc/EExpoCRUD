package eexpocrud.cfg;

import java.io.Serializable;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import eexpocrud.CrudfyUtils;

@SuppressWarnings("serial")
public class EExpoCrudCfgManager <E extends Serializable,ID extends Comparable<ID>> implements Serializable{
	private LinkedHashMap<String, EExpoCrudCfg<E,ID>> cfgsOnSession;
	
	private final static String  CFGS_ON_SESSION_ID = "CFGS_ON_SESSION_ID"; 

	
	
	
	void resolveCfgsOnSession(HttpServletRequest req){
		this.cfgsOnSession = CrudfyUtils.getFromSession(req, CFGS_ON_SESSION_ID);
		if(this.cfgsOnSession == null){
			this.cfgsOnSession = new LinkedHashMap<String, EExpoCrudCfg<E,ID>>(){
				@Override
				protected boolean removeEldestEntry(@SuppressWarnings("rawtypes") java.util.Map.Entry eldest) {
					return size() > EExpoCrudCfg.maxCfgPerSession;
				}
			};			
		}

	}
	
	public void  saveCfg(EExpoCrudCfg<E,ID> cfg){
		resolveCfgsOnSession(cfg.req);
		this.cfgsOnSession.put(cfg.id, cfg);
		CrudfyUtils.saveOnSession(cfg.req, CFGS_ON_SESSION_ID, this.cfgsOnSession);
	}
	
	
	public EExpoCrudCfg<E,ID> getFromSession(HttpServletRequest req, String eexpoCrudCfgId){
		resolveCfgsOnSession(req);
//		this.cfgsOnSession = CrudfyUtils.getFromSession(req, CFGS_ON_SESSION_ID);
//		if(this.cfgsOnSession == null){
//			this.cfgsOnSession = new LinkedHashMap<>();
//		} 
		return this.cfgsOnSession.get(eexpoCrudCfgId);		
	}
	
	
}
