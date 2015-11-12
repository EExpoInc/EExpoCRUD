package eexpocrud.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import test.AdmFiltersAction;
import test.FilterImgUrlEntity;
import eexpocrud.CrudfyUtils;
import eexpocrudOLD.DaoGlobalAbs;

public class CrudfyDaoGlobalMem   extends DaoGlobalAbs {
	private Map<?,?> entityMap;
	
	public CrudfyDaoGlobalMem(Class<?> entityClass, Map<?,?> entityMap) {
		super(entityClass, true);
		this.entityMap = entityMap;
	}

	@Override
	public boolean isPermissive() {
		return true;
	}
	
	@Override
	public long total() {
		return 0;
	}
	
	@Override
	public <E> List<E> initialList(int size) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public <E> List<E> nav(long initialPos, long finalPos) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public <K, E> List<E> list(K idLastItemPage, int jump) {
		List<?> result;
		List<?> memList = new ArrayList<>(entityMap.values()) ;
		E e= (E)entityMap.get(idLastItemPage);
		
		int idx = memList.indexOf(e);
		int idxResult = idx + (jump*this.displaySizeList);
		idxResult = idxResult > 0 ? idxResult: 0;
		try{
			/**
			 * EM PROD, CORRER O CURSOR MANUALMENTE, P TENTAR PEGAR OS ULTIMOS REGISTROS. AFINAL
			 * O INDICE SERA SCANEADO DE QUALQUER FORMA. MELHOR Q ACOMPANHE NA MAO. 
			 */
			result = memList.subList(idxResult, idxResult+this.displaySizeList);
		}catch(IndexOutOfBoundsException ex){
			result = memList.subList(memList.size() - displaySizeList, memList.size());
		}
		return (List<E>) result;
	}

	
	@Override
	public <E> void create(E e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public <E> void update(E e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public <K> void delete(K id) {
		// TODO Auto-generated method stub
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <K, E> E read(K id) {		
		Object _id =  CrudfyUtils.parseFromString(id.toString(), idClass);
		return (E) entityMap.get(_id);
	}
	
	
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		AdmFiltersAction.list();
		CrudfyDaoGlobalMem dao = new CrudfyDaoGlobalMem(FilterImgUrlEntity.class, AdmFiltersAction.dao);
		
//		FilterImgUrlEntity fe = dao.read("2");
		FilterImgUrlEntity fe2 = dao.read(2L);
		System.out.println(fe2);
		
		System.err.println(dao.list(150L, 3));
		
	}
	
}
