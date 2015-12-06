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
		
	}
	
	public void delete(Object obj) {
		this.em.getTransaction().begin();
		this.em.remove(obj);
		this.em.getTransaction().commit();
	}
	
	public void update(Object obj) {
		this.em.getTransaction().begin();
		this.em.persist(obj);
		this.em.getTransaction().commit();
		
	}
	
	public E read(Object id) {
		return em.find(this.entityClass, id);
		
	}
	
}
