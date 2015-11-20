package eexpocrud.tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eexpocrud.action.ListAction;
import eexpocrud.action.ListActionData;
import eexpocrud.cfg.EExpoCrudCfg;
import eexpocrud.cfg.EExpoCrudCfgManager;
import eexpocrud.dao.ObjectfyDAO;
import eexpocrud.dao.impl.jpa.two.JpaDAO;


@SuppressWarnings("unused")
public class CrudfyTagHelper <E extends Serializable, K extends Comparable<K> >{

	private enum NavParam{
		begin, end, before, after
	}
	
	private ObjectfyDAO<E, K> objDao;
	private JpaDAO<E, K> jpaDao;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	public EExpoCrudCfg<E, K> cfg;
	
	private ListAction<E, ?> act;
	private ListActionData<E, ?> data; 
	
	 
	
	public CrudfyTagHelper(HttpServletRequest req, HttpServletResponse resp, EExpoCrudCfg<E, K> cfg)  {
		this(req, resp, cfg.jpaDao, false);
		this.cfg = cfg;
		saveCrudCfg();
		
	}
	
	public CrudfyTagHelper(HttpServletRequest req, HttpServletResponse resp, JpaDAO<E, K> dao, boolean resolveCrudCfg) {
		this.req = req;
		this.resp = resp;
		this.jpaDao = dao;
		this.act = (ListAction<E, K>) new ListAction<>(req, resp, dao);
		this.data = this.act.genTable();  
		if(resolveCrudCfg){
			resolveCrudCfg();	
		}
		
	}
	
	public CrudfyTagHelper(HttpServletRequest req, HttpServletResponse resp, JpaDAO<E, K> dao)  {
		this(req, resp, dao, true);
	}
	
	private void saveCrudCfg(){
		String id = EExpoCrudCfg.genDefaultId(req);					
		EExpoCrudCfgManager<E,K> man = new EExpoCrudCfgManager<>();
		man.saveCfg(cfg);	
	}
	
	private void resolveCrudCfg(){
		String id = EExpoCrudCfg.genDefaultId(req);					
		EExpoCrudCfgManager<E,K> man = new EExpoCrudCfgManager<>();

		if(this.cfg == null){
			
			EExpoCrudCfg<E, K> _cfg = man.getFromSession(req, id);
			if(_cfg != null){
				this.cfg = _cfg; 
				this.cfg.refresh(req, resp);//, jpaDao);
			}else{
				this.cfg = new EExpoCrudCfg<E, K>(req, resp, this.jpaDao);
				man.saveCfg(cfg); 
			}		

		}
	}
	
	
	
	public String genAfterLink(){
		Map<String, String[]>  params = cleanUrlCrudfyParams();
		params.put(NavParam.after.toString(), new String[] {this.data.getBottomIdEscaped()});
		return buildRelUrlParams(params);
	}
	public String genBeforeLink(){
		Map<String, String[]>  params = cleanUrlCrudfyParams();
		params.put(NavParam.before.toString(), new String[] {this.data.getTopIdEscaped()});
		return buildRelUrlParams(params);

		
	}
	public String genBeginLink(){
		Map<String, String[]>  params = cleanUrlCrudfyParams();
		params.put(NavParam.begin.toString(), null);
		return buildRelUrlParams(params);

		
	}
	public String genEndLink(){
		Map<String, String[]>  params = cleanUrlCrudfyParams();
		params.put(NavParam.end.toString(), null);
		return buildRelUrlParams(params);

	}

	
	

	// para preservar outros parametros passados na request
	private Map<String, String[]> cleanUrlCrudfyParams(){
		
		Map<String, String[]> params = new TreeMap<>();
		params.putAll(this.req.getParameterMap());
		
		Set<String> keys = params.keySet();
		for (NavParam np : NavParam.values()) {
			if(params.containsKey(np.toString())){
				params.remove(np.toString());
			}
		}
		return params;
	}
	
	private String buildRelUrlParams(Map<String, String[]> params){
		String result = "?";
		List<String> resultList  = new ArrayList<>();
		for(String k : params.keySet()){
//			result += k ;
			resultList.add(k);
			if(params.get(k) != null){
				for(String v : params.get(k)){
					resultList.add("="+v);
					resultList.add("&"+k);
				}
				resultList.remove(resultList.size()-1);				
			}
			resultList.add("&");
		}
		resultList.remove(resultList.size()-1);
		
		for(String s : resultList){
			result += s;
		}
		return result;
		
	}
	
	
	
	
	public ListActionData<?, ?> getTable(){
		return this.data;	
	}
	
	

	
	
	
}
