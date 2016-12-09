package eexpocrud.cfg;

import java.io.Serializable;

import eexpocrud.dao.impl.jpa.two.JpaDAO;
import eexpocrud.dao.impl.jpa.two.QueryCfg;

@SuppressWarnings("serial")
public class ListPageCfg<E extends Serializable, K extends Comparable<K>> implements Serializable{
	

//	private String jspPath;
	public EExpoGroupBtn<E> groupBtn;
	public QueryCfg<E, K> queryCfg;
	
	
	public ListPageCfg(JpaDAO<E, K> jpaDAO, EExpoCrudCfg<E, K> cfg) {		
		this.groupBtn = new EExpoGroupBtn<E>(cfg.id);
		this.queryCfg = new QueryCfg<E, K>();
	}
	
	public ListPageCfg<E, K> limitList(int limitList){
		this.queryCfg.listLimitPerPage(limitList);
		return this;
	}

	
	/**dispatch com a lista de obj e a lista de entities*/
//	public void dispatch(String jspPath){ 
//		this.jspPath = jspPath;
//	}
	
}
