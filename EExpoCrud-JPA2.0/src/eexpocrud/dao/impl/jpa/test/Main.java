package eexpocrud.dao.impl.jpa.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import eexpocrud.dao.impl.jpa.JpaDAO;

public class Main {
	private static final String PERSISTENCE_UNIT_NAME = "EExpoCrud-JPA2.0";
	private static EntityManagerFactory factory;
	

	
	public static void main_(String[] args) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	    EntityManager em = factory.createEntityManager();
	    em.getTransaction().begin();
	    for(int i=0; i<42; i++){
	    	PostEntity pe = new PostEntity();
	    	pe.id=i;
	    	pe.title = "Title "+i;
	    	long r = (long)(Math.random()*100000000000L );
	    	pe.body = "body "+ r;
			pe.dateCreate =  new Date(System.currentTimeMillis() - (r));
			em.persist(pe);
			
	    }
	    em.getTransaction().commit();
	    
	    
	}

	
	
	public static void main(String[] args) {
//		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
//	    EntityManager em = factory.createEntityManager();
	    
	    JpaDAO<UserEntity, Integer> userDao = new JpaDAO<>(UserEntity.class, Integer.class, PERSISTENCE_UNIT_NAME, 42);
	    JpaDAO<PageEntity, String> pageDao = new JpaDAO<>(PageEntity.class, String.class, PERSISTENCE_UNIT_NAME, 10);
	    
	    for (int j = 0; j < 100; j++) {
	    	try{
			    pageDao.em.getTransaction().begin();
			    for (int i = 0; i < 100000; i++) {
			    	pageDao.em.persist(genPage());
			    	if(i%10000 == 0){
			    		System.out.println("Inserindo : "+ i);	
			    	}
			    	
				}
			    pageDao.em.getTransaction().commit();	
	    	}catch(Exception e){
	    		System.err.println("pageDao.em.getTransaction().rollback();");
//	    		pageDao = new JpaDAO<>(PageEntity.class, String.class, PERSISTENCE_UNIT_NAME, 10);
	    		//pageDao.em.getTransaction().rollback();
	    	}finally{
	    		
	    	}
		    			
		}
//	    pageDao.create(genPage());
//	    pageDao.create(genPage());
//	    pageDao.create(genPage());
//	    pageDao.create(genPage());
	    
	    List<PageEntity> lp = pageDao.listAfter("");
	    Collections.reverse(lp);
	    for(PageEntity e: lp){
	    	System.out.println( e.url + " - "+e.title+ " dataCadastro:"+e.dataCadastro);
	    }

	    
	    
	    
	    
//	    List<UserEntity> l = dao.listBefore(10);
//	    List<UserEntity> l = userDao.listAfter(10);
//	    Collections.reverse(l);
//	    
//	    
//	    userDao.create(genUsers().get(0));
//	    
//	    
//	    
//	    
//	    for(UserEntity e: l){
//	    	System.out.println( e.id + " - "+e.nome + " anoNasc:"+e.anoNasc);
//	    }
	    
	    
	    
	    
	    


	}
	
	
	public static void main4(String[] args) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	    EntityManager em = factory.createEntityManager();
	    List<UserEntity> list = genUsers();
	     em.getTransaction().begin();
//	    EntityTransaction  et = em.getTransaction();
	    
	    for(UserEntity ue: list){
	    	em.persist(ue);
	    }
	    em.getTransaction().commit();
	    
	    CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<UserEntity> cq =  cb.createQuery(UserEntity.class);
	    
	    Root<UserEntity> r = cq.from(UserEntity.class);
//	    cq.orderBy(cb.desc(r.get("id")));
//	    ParameterExpression<Integer> p = cb.parameter(Integer.class);
	    Predicate predicate = cb.gt(r.<Integer> get("id"), 100);
	    cq.where(predicate);
	    
//	    cq.from(UserEntity.class);
//	    Query q = em.createQuery("select t from UserEntity t");
	    TypedQuery<UserEntity> tq = em.createQuery(cq);
	    tq.setMaxResults(10);
	    List<UserEntity> list2 = tq.getResultList();

	    
	    
	    for(UserEntity e: list2){
	    	System.out.println( e.id + " - "+e.nome);
	    }
	    em.close();
	}
	
	
	
	private static PageEntity genPage(){
		PageEntity pe = new PageEntity();
		long r = (long)(Math.random()*10000000L );
		long r2 = (long)(Math.random()*1000000000000000L );
		pe.url = "http://eexponews.org/url/id_"+ r +"-"+ r2;
		pe.title = "Titulo "+r;
		pe.dataCadastro =  new Date(System.currentTimeMillis() - (r));
		return pe;
		
	}
	
	private static List<UserEntity> genUsers(){
		List<UserEntity> result = new ArrayList<>();
		for(int i=0; i<200; i++){
			UserEntity ue = new UserEntity();
			ue.id = i;
			ue.anoNasc = (int) (Math.random()*100);
			ue.nome = "Nome Usuario " + ue.id;
			ue.dataCadastro = new Date((long)(Math.random()*100000000000L ));
			ue.end = "rua "+ ue.dataCadastro;
			ue.bio = "minha bio" +ue.dataCadastro;
			result.add(ue);
		}
		return result; 
		
	}
	
}
