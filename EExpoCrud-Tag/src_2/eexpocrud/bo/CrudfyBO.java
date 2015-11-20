package eexpocrud.bo;

import javax.persistence.EntityManager;

import eexpocrud.action.CrudfyNavDataInput;

public class CrudfyBO <E>{
	EntityManager em;
	Class<E> entityClass;
	
	public CrudfyBO(EntityManager em, Class<E> entityClass) {
		this.em = em;
		this.entityClass = entityClass;
	}
	
	void create(Object obj) {
		
	}
	
	public void list(CrudfyNavDataInput nav) {
		// TODO Auto-generated method stub
		
	}
	
	void delete(String id) {
		// TODO Auto-generated method stub
		
	}
	
	void update(Object obj) {
		// TODO Auto-generated method stub
		
	}
	
	public E read(Object id) {
		return em.find(this.entityClass, id);
		// TODO Auto-generated method stub
		
	}
	
}
