package eexpocrudOLD;

import java.util.List;

import eexpocrud.CrudfyUtils;


/**@DaoGlobal unico e generico no projeto p poder acessar tds as entities*/
public abstract class DaoGlobalAbs {
	public int displaySizeList=10, pageAtual=1;
	public Class<?> entityClass;
	public Class<?> idClass;
	public boolean estimated = false;
	 
	/**
	 * @param estimated - define se a nav sera estimada, p evitar de fazer um full scan index 
	 * (e n gastar alguns ciclos de cpu a toa).    
	 */
	public DaoGlobalAbs(Class<?> entityClass, boolean estimated){
		this.estimated = estimated;
		this.entityClass = entityClass;
		this.idClass = CrudfyUtils.findIdField(entityClass).getType();		
	}
 


	/** @permissive pode pegar qq entity pelo classname, sem precisar ser cad explicito */	
	public abstract boolean isPermissive();
	
	public abstract long total();
	public abstract <E> List<E> initialList(int size);
	//public List<B> nav(int page); 
//	public List<B> nav(int offset, int size); /** P NAVEGAR PELAS PAGS */
	public abstract <E> List<E> nav(long initialPos, long finalPos); 
	
	/**p ajudar a navegar com o cursor (p frente e p tras) em uma tabela q n se sabe o tamanho  */
	public abstract <K, E> List<E> list(K idLastItemPage, int jump); 	
	
	public abstract <E> void create(E e);
	public abstract <E> void update(E e);
	public abstract <K> void delete(K id);
	public abstract <K, E> E read(K id);	 



}
