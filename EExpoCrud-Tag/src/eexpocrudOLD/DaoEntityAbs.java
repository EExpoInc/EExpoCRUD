package eexpocrudOLD;

import java.util.List;

public abstract class DaoEntityAbs <E, K> {
	
	//TIRAR ESSAS CONFIGS DE EXIBICAO DO DAO, E BOTAR NO CRUDFYCFG
	public int qtdeDisplay=10, pageAtual=1;
	public boolean estimated = false;
	
	public DaoEntityAbs(boolean estimated) {
		this.estimated = estimated;
	}

	public CrudfyCfg crudfyCfg = new CrudfyCfg();

	/**CUIDADO: as vzs pegar o total pode ser custoso p alguns BD, como o datastore */
	public abstract long total();

	public abstract List<E> initialList();
	//public List<B> nav(int page); 
//	public List<B> nav(int offset, int size); /** P NAVEGAR PELAS PAGS */
	public abstract List<E> nav( long initialPos, long finalPos);
	
	
	public abstract List<E> list(K startId, int jump);  
	public abstract  void create(E e);
	public abstract  void update(E e);
	public abstract  void delete(K id);
	public abstract E read(K id);	 
	public boolean permissive;

	public abstract List<E>  listAfter(K idAfter);
	public abstract List<E>  listBefore(K idBefore);

	


}
