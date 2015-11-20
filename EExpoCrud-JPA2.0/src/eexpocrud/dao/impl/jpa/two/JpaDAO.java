package eexpocrud.dao.impl.jpa.two;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import eexpocrud.CrudfyUtils;
import eexpocrud.dao.impl.jpa.test.PostEntity;
import eexpocrud.dao.impl.jpa.two.QueryCfg.OrderCriteria;
import eexpocrud.dao.impl.jpa.two.QueryCfg.OrderCriteriaEnum;
import eexpocrud.dao.impl.jpa.two.QueryCfg.Where;
import eexpocrud.dao.impl.jpa.two.QueryCfg.WhereEnum;

@SuppressWarnings("serial")
public class JpaDAO<E extends Serializable, K extends Comparable<K>> implements Serializable {
	
	public final Class<E> entityClass;
	public final String persistenceUnit;
	public final Class<K> idClass;
	transient public  EntityManager em;
	private static Map<String, EntityManagerFactory> emfMap = new TreeMap<>();
	private QueryCfg<E, K> queryCfg;
	private final String idName;
	
	transient private CriteriaBuilder criteriaBuilder;
	transient private CriteriaQuery<E> criteriaQuery;
	transient private Root<E> rootEntity;
	transient private List<Predicate> predicateList = new ArrayList<>();
	transient private Where navParam;

	
	
	/**
	 * Falta:
	 * - a clausula OR <br>
	 * - suporte a construcao encadeada da query (WHERE (id=10 AND (status=2 OR state='NY')) OR (id>11 AND status=1))<br>
	 * - uso de uma StringQuery prepadara e navegar por ela
	 *   
	 * @param entityClass
	 * @param idClass
	 * @param persistenceUnit
	 */
	public JpaDAO(Class<E> entityClass, Class<K> idClass, String persistenceUnit) {
		this.entityClass = entityClass;
		this.idClass = idClass;
		this.queryCfg = new QueryCfg<>();
		
		this.em = getOrAddEMFactory(persistenceUnit).createEntityManager();
		this.persistenceUnit = persistenceUnit;
		this.idName = CrudfyUtils.findIdField(entityClass).getName();
		
		this.criteriaBuilder = em.getCriteriaBuilder();
		this.criteriaQuery = criteriaBuilder.createQuery(entityClass);
		this.rootEntity = criteriaQuery.from(entityClass);
		
	}
	
	public void refresh(){
		this.em = getOrAddEMFactory(persistenceUnit).createEntityManager();
	}
	
	public JpaDAO<E, K> queryCfg(QueryCfg<E, K> queryCfg) {
		this.queryCfg = queryCfg;
		return this;
	}
	
	public int limitList() {
		return this.queryCfg.limitList;
	}
	
	public JpaDAO<E, K> clone() {
		JpaDAO<E, K> clone = new JpaDAO<E, K>(this.entityClass, this.idClass, this.persistenceUnit);
		clone.queryCfg = this.queryCfg;
		return clone;
	}
	
	public JpaDAO(Class<E> entityClass, Class<K> idClass, String persistenceUnit, int limitList) {
		this(entityClass, idClass, persistenceUnit);
		this.queryCfg.limitList = limitList;
	}
	
	public void create(E e) {
		EntityTransaction et = em.getTransaction();
		et.begin();
		em.persist(e);
		et.commit();
	}
	
	public void update(E e) {
		
	} 
	
	public void delete(K id) {
		
	}
	
	public E read(K id) {
		return em.find(this.entityClass, id);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<E> listAfter(K idAfter) {
		this.navParam = this.queryCfg.createWhere(idName, WhereEnum.gt, idAfter);
		return finalizeQuery().getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<E> listBefore(K idBefore) {
		this.navParam = this.queryCfg.createWhere(idName, WhereEnum.lt, idBefore);
		revertOrder();
		return finalizeQuery(true).getResultList();
	}
	
	public List<E> listBegin() {
		this.navParam = null;
		return finalizeQuery().getResultList();
	}
	
	public List<E> listEnd() {
		this.navParam = null;
		revertOrder();
		return finalizeQuery(true).getResultList();
	}
	
	public static EntityManagerFactory getOrAddEMFactory(String persistenceUnit) {
		if (emfMap.containsKey(persistenceUnit)) {
			return emfMap.get(persistenceUnit);
		} else {
			EntityManagerFactory result = Persistence.createEntityManagerFactory(persistenceUnit);
			emfMap.put(persistenceUnit, result);
			return result;
		}
	}
	
	private void revertOrder() {
		List<OrderCriteria> orderCriteriaListRev = new ArrayList<>();
		for (OrderCriteria oc : this.queryCfg.orderCriteriaList) {
			OrderCriteria ocRev = new OrderCriteria();			
			if (oc.order == OrderCriteriaEnum.asc) {
				ocRev.order = OrderCriteriaEnum.desc;
			} else {
				ocRev.order = OrderCriteriaEnum.asc;
			}
			
			ocRev.field = oc.field;
			orderCriteriaListRev.add(ocRev);
		}
		this.queryCfg.orderCriteriaList = orderCriteriaListRev;
		
	}
	
	private TypedQuery<E> finalizeQuery() {
		return finalizeQuery(false);
	}
	
	private TypedQuery<E> finalizeQuery(boolean reverted) {
		
		for (Where w : this.queryCfg.whereList) {
			concreteWhere(w);
		}
		
		if(this.navParam != null){
			concreteWhere(this.navParam);
		}
		
		this.criteriaQuery.where(this.predicateList.toArray(new Predicate[]{}));
		
		// SEMPRE ORDENAR NO FINAL PELO ID, PARA PROMOVER A NAVEGACAO NAS PAGINAS
		if (reverted) {
			this.queryCfg.addOrderBy(idName, OrderCriteriaEnum.desc);
		} else {
			this.queryCfg.addOrderBy(idName);
		}
		
		for (OrderCriteria oc : this.queryCfg.orderCriteriaList) {
			concreteOrderBy(oc);
		}
		TypedQuery<E>  result = em.createQuery(this.criteriaQuery); 
		result.setMaxResults(this.queryCfg.limitList);
		return result;
	}
	
	private void concreteOrderBy(OrderCriteria orderCriteria) {
		if (orderCriteria.order == OrderCriteriaEnum.desc) {
			this.criteriaQuery.orderBy(this.criteriaBuilder.desc(rootEntity.get(orderCriteria.field)));
		} else {
			this.criteriaQuery.orderBy(this.criteriaBuilder.asc(rootEntity.get(orderCriteria.field)));
		}
	}
	
	
	private <T extends Comparable<T>> void concreteWhere(Where cond) {
		
		Predicate pred = createPredicate(cond);
		if (pred != null) {
			this.predicateList.add(pred);
		}
	}
		
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T extends Comparable<T>> Predicate createPredicate(Where where){
		Predicate pred = null;
		Expression<String> exp = rootEntity.get(where.fieldName);
		switch (where.cond) {
		case eq:
			pred = criteriaBuilder.equal(rootEntity.<T> get(where.fieldName), where.values.get(0));
			break;
		case neq:
			pred = criteriaBuilder.notEqual(rootEntity.<T> get(where.fieldName), where.values.get(0));
			break;
		case lt:
			pred = criteriaBuilder.lessThan(rootEntity.<Comparable> get(where.fieldName), where.values.get(0));
			break;
		case le:
			pred = criteriaBuilder.lessThanOrEqualTo(rootEntity.<Comparable> get(where.fieldName),where.values.get(0)); 
			break;
		case gt:
			pred = criteriaBuilder.greaterThan(rootEntity.<Comparable> get(where.fieldName), where.values.get(0));
			break;
		case ge:
			pred = criteriaBuilder.greaterThanOrEqualTo(rootEntity.<Comparable> get(where.fieldName),where.values.get(0));
			break;
		case like:
			pred = criteriaBuilder.like(exp, where.values.get(0) + "");
			break;
		case notLike:
			pred = criteriaBuilder.notLike(exp, where.values.get(0) + "");
			break;
		case in:
			pred = rootEntity.get(where.fieldName).in( where.values);
//			pred = criteriaBuilder.in(rootEntity.<Comparable> get(where.fieldName).in( where.values));
			break;
		case notIn:
			pred = criteriaBuilder.not(rootEntity.<Comparable> get(where.fieldName).in(where.values));			
			break;
		default:
			break;
		}
		return pred;
	}
	
	
	public static void main(String[] args) {
		JpaDAO<PostEntity, Integer> jpaDAO = new JpaDAO<>(PostEntity.class, Integer.class, "EExpoCrud-JPA2.0");
		
		String ejbQL = "Select PostEntity where id > 10";
		Query query = jpaDAO.em.createQuery(ejbQL);
	}
	
	
}
