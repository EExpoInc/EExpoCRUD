package eexpocrud.dao.impl.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import eexpocrud.CrudfyUtils;
import eexpocrud.dao.impl.jpa.two.QueryCfg;

@SuppressWarnings("serial")
public class JpaDAO<E extends Serializable, K extends Comparable<K>> implements Serializable{
	
	public  int amountList = 10;
	
	public final Class<E> entityClass;
	public final String persistenceUnit;
	public final Class<K> idClass;
	public final  EntityManager em;
	private static Map<String, EntityManagerFactory> emfMap = new TreeMap<>();
	private QueryCfg<E, K> queryCfg;
	private final String idName;
	
	
	
	public JpaDAO(Class<E> entityClass, Class<K> idClass, String persistenceUnit) {
		this.entityClass = entityClass;
		this.idClass = idClass; 
		
		this.em = getOrAddEMFactory(persistenceUnit).createEntityManager();
		this.persistenceUnit = persistenceUnit;
		this.idName = CrudfyUtils.findIdField(entityClass).getName();
	}
	
	public JpaDAO<E, K> queryCfg(QueryCfg<E, K> queryCfg){
		this.queryCfg  =queryCfg;
		return this;
	}
	
	
	
	public JpaDAO<E, K> clone(){
		return new JpaDAO<E, K>(this.entityClass, this.idClass, this.persistenceUnit);
	}

	public JpaDAO(Class<E> entityClass, Class<K> idClass, String persistenceUnit, int amountList) {
		this(entityClass, idClass, persistenceUnit);
		this.amountList = amountList;
	}
	
	public void create(E e) {
		EntityTransaction et =  em.getTransaction();
		et.begin();
		em.persist(e);
		et.commit();		
	}
	
	public void update(E e) {
		
	}
	
	public void delete(K id) {
		
	}
	
	public E read(K id) {
		return null;
		
	}
	
	public List<E> listAfter(K idAfter) {
//		return tq.setMaxResults(amountList).getResultList();
		return prepareQuery(idAfter, true).getResultList();
	}
	
	public List<E> listBefore(K idBefore) {
		return prepareQuery(idBefore, false).getResultList();
	}
	
	public List<E> listBegin() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<E> cq = cb.createQuery(entityClass);
//		
		Root<E> root = cq.from(entityClass);
		cq.orderBy(cb.asc(root.get(idName)));

//		return prepareQuery(null, false).getResultList();
		return  em.createQuery(cq).setMaxResults(amountList).getResultList();
	}

	public List<E> listEnd() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<E> cq = cb.createQuery(entityClass);
//		
		Root<E> root = cq.from(entityClass);
		cq.orderBy(cb.desc(root.get(idName)));

//		return prepareQuery(null, false).getResultList();
		return  em.createQuery(cq).setMaxResults(amountList).getResultList();

		
//		return prepareQuery(null, false).getResultList();
	}

	
	
	
	
	private TypedQuery<E> prepareQuery(K start, boolean after) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<E> cq = cb.createQuery(entityClass);
//		
		Root<E> root = cq.from(entityClass);
		
//		if(idClass.isInstance("") && start == null && after){
//			start = (K) "";
//		}
//		
//		
//		if(start == null){
//			this.queryCfg.addOrderBy("id");
//		}
		
		
		
//		if(start == null){
//			if(!after){				
//				cq.orderBy(cb.desc(root.get(idName)));
//			}
//		}else{
			if(after){ 
				Predicate predicate = cb.greaterThan(root.<K>get(idName), start);
				cq.where(predicate);
				cq.orderBy(cb.asc(root.get(idName)));
			}else{
				Predicate predicate = cb.lessThan(root.<K>get(idName), start);
				cq.orderBy(cb.desc(root.get(idName)));
				cq.where(predicate);
			}
//		}
		
		TypedQuery<E> tq = em.createQuery(cq);
	
		tq.setMaxResults(amountList);
		return tq;
	}
	
	private EntityManagerFactory getOrAddEMFactory(String persistenceUnit){
		if(emfMap.containsKey(persistenceUnit)){
			return emfMap.get(persistenceUnit);
		}else{
			EntityManagerFactory result = Persistence.createEntityManagerFactory(persistenceUnit);
			emfMap.put(persistenceUnit, result);
			return result;
		}
	}
	
	
	
}
