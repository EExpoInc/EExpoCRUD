package eexpocrud.cfg;

public class EExpoButton<E > {
	public EExpoRowButtonCfg<E> cfg;
	private E entity;
	
	public EExpoButton(EExpoRowButtonCfg<E> cfg, E entity) {		
		this.cfg = cfg;
		this.entity = entity;		
	}
	
	public boolean visible(){
		this.cfg.actualEntityRow= entity;
		return this.cfg.visibleCond.execute();
	}
	
	public boolean disabled(){
		this.cfg.actualEntityRow =  entity;
		return this.cfg.disableCond.execute();
	}
	
	
	
	
	
	
	
	
	
}
