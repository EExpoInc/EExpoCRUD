package eexpocrud.cfg;

import java.io.Serializable;

import eexpocrud.dao.impl.jpa.two.JpaDAO;
import eexpocrud.dao.impl.jpa.two.QueryCfg;

@SuppressWarnings("serial")
public class ListPageCfg<E extends Serializable, K extends Comparable<K>> implements Serializable{
	

	private String jspPath;
	public EExpoGroupBtn<E> groupBtn;
	public QueryCfg<E, K> queryCfg;
	
	public ListPageCfg(JpaDAO<E, K> jpaDAO) {
		this.groupBtn = new EExpoGroupBtn<E>();
		this.queryCfg = new QueryCfg<E, K>();
	}
	

	
	/**dispatch com a lista de obj e a lista de entities*/
	public void dispatch(String jspPath){ 
		this.jspPath = jspPath;
	}
	
}