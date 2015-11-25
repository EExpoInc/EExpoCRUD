package eexpocrud.bo;

import javax.persistence.EntityManager;

import eexpocrud.action.CrudfyNavDataInput;

public class CrudfyBO <E>{
	EntityManager em;
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
		// TODO Auto-generated method stub
		
	}
	
	public E read(Object id) {
		return em.find(this.entityClass, id);
		// TODO Auto-generated method stub
		
	}
	
}
