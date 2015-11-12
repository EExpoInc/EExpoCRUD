package eexpocrud.dao.impl.jpa.two;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SuppressWarnings("serial")
public class QueryCfg <E extends Serializable, K extends Comparable<K>> implements Serializable{
	
	
	public static  enum WhereEnum{
		eq, neq, lt, le, gt, ge, like, notLike, in, notIn;
	}
	
	public static  class Where{
		protected WhereEnum cond;
		protected String fieldName;
		protected List<Comparable<?>> values;
	}
	
	public static enum OrderCriteriaEnum{
		asc, desc;		
	}
	
	public static class OrderCriteria{
		protected OrderCriteriaEnum order;
		protected String field;
	}
	
	protected  int limitList = 10;
	
	protected List<OrderCriteria> orderCriteriaList = new ArrayList<>();
	protected List<Where> whereList = new ArrayList<>();
	
	
	@SuppressWarnings("unchecked")
	public <T extends Comparable<T>> QueryCfg<E,K> addWhere(String fieldName, WhereEnum cond, T... value) {
//		cond.fieldName = fieldName;
//		cond.values = value;
		whereList.add(createWhere(fieldName, cond, value));
		return this;
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends Comparable<T>> Where createWhere(String fieldName, WhereEnum cond, T... value) {
		Where w= new Where();
		w.fieldName = fieldName;
		w.values = (List<Comparable<?>>) Arrays.asList(value);
		w.cond = cond;
		return w;
	}
	

	
	
	public QueryCfg<E,K> addOrderBy(String field, OrderCriteriaEnum orderCriteriaE ){
		OrderCriteria oc = new OrderCriteria();
		oc.field = field;
		oc.order = orderCriteriaE;
		orderCriteriaList.add(oc);
		
		return this;
	}
 
	public QueryCfg<E,K> addOrderBy(String field){
		addOrderBy(field, OrderCriteriaEnum.asc);
		return this; 
	}
	

	public QueryCfg<E,K> listLimitPerPage(int limitList){
		this.limitList= limitList;
		return this;
	}
	
	
	
}
