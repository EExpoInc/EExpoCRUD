package eexpocrud.cfg;

import java.io.Serializable;

import eexpocrud.bo.CrudfyBO;

/**
 * Passar a entidade em questao ou deixar o programador se virar?
 */
@SuppressWarnings("serial")
public abstract class ActionableI<E extends Object> implements Serializable {
	public E entity;
	public Object entityId;
	public CrudfyBO<E> bo;
	
	public abstract E action();
	
	@SuppressWarnings("unchecked")
	public ActionableI<E> entity(Object entityObj){
		this.entity = (E) entityObj;
		return this;
	}
}