package eexpocrud.cfg;

import java.io.Serializable;
import java.util.Map;

import eexpocrud.CrudfyUtils;
import eexpocrud.bo.CrudfyBO;

/**
 * Passar a entidade em questao ou deixar o programador se virar?
 */
@SuppressWarnings("serial")
public abstract class ActionableI<E extends Object> implements Serializable {
	public static final String ENTITY_ATTR = "entity";
	public E entity;
	public Object entityId;
	public CrudfyBO<E> bo;
	
	
	public abstract E action();
	
	@SuppressWarnings("unchecked")
	public ActionableI<E> entity(Object entityObj){
		this.entity = (E) entityObj;
		return this;
	}

	@SuppressWarnings("unchecked")
	public ActionableI<E> entity(Map<String, String[]> parameterMap, Object oldEntity){
		this.entity = (E) CrudfyUtils.populate(parameterMap, (E) oldEntity);
		return this;
	}

		
	
	@SuppressWarnings("unchecked")
	public ActionableI<E> entity(Map<String, String[]> parameterMap, Class<?> clazz){
		this.entity = (E) CrudfyUtils.populate(parameterMap, clazz);
//		this.entity = (E) entityObj;
		return this;
	}
}