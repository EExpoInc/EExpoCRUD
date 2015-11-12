package eexpocrud.dao;

import java.util.List;

public class ObjectfyDAO<E, K> {
	
	private int amountList = 10;
	
	public final Class<E> entityClass;
	public final Class<K> idClass;
	
	public ObjectfyDAO(Class<E> entityClass, Class<K> idClass) {
		this.entityClass = entityClass;
		this.idClass = idClass;
	}
	
	public ObjectfyDAO(Class<E> entityClass, Class<K> idClass, int amountList) {
		this(entityClass, idClass);
		this.amountList = amountList;
	}
	
	public void create(E e) {
		
	}
	
	public void update(E e) {
		
	}
	
	public void delete(K id) {
		
	}
	
	public E read(K id) {
		return null;
		
	}
	
	public List<E> listAfter(K idAfter) {
		return null;
	}
	
	public List<E> listBefore(K idBefore) {
		return null;
	}
	
}
