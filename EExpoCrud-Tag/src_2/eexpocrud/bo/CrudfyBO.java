package eexpocrud.bo;

import java.io.Serializable;

import javax.persistence.EntityManager;

import eexpocrud.action.CrudfyNavDataInput;

@SuppressWarnings("serial")
public class CrudfyBO <E> implements Serializable{
	transient EntityManager em;
	public Class<E> entityClass;
	
	public CrudfyBO(EntityManager em, Class<E> entityClass) {
		this.em = em;
		this.entityClass = entityClass;
	}
	
	public void create(Object obj) {
		
	}
	
	public void list(CrudfyNavDataInput nav) {
		// TODO Auto-generated method stub
		
	}
	
	public void delete(Object obj) {
		// TODO Auto-generated method stub
		
	}
	
	public void update(Object obj) {
		this.em.getTransaction().begin();
		this.em.merge(obj);
		this.em.getTransaction().commit();
		// TODO Auto-generated method stub
		
	}
	
	public E read(Object id) {
		return em.find(this.entityClass, id);
		// TODO Auto-generated method stub
		
	}
	
}
