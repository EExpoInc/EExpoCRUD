package eexpocrud.cfg;




@SuppressWarnings("serial")
public class EExpoRowButtonCfg<E> extends EExpoButtonCfg<E> {
	
	ConditionalI disableCond;
	ConditionalI visibleCond;
	public E actualEntityRow;
	

	
	

	public EExpoRowButtonCfg(ActionableI<E> invokable) {
		super(invokable);
//		this.name = getClass().getName();
		this.setup();
	}

	public EExpoRowButtonCfg(String name, ActionableI<E> invokable) {
		super(name, invokable);
		this.setup();
	}

	
	public EExpoRowButtonCfg(String name, Glyphicon cssIcon, String preMsg, String successMsg,
			String failureMsg, ShowAt position, ActionableI<E> invokable) {
		super(name, cssIcon, preMsg, successMsg, failureMsg, position, invokable);
		this.setup();
		
	}
	
	public EExpoRowButtonCfg(String name, Glyphicon cssIcon, ActionableI<E> invokable) {
		super(name, cssIcon, true, false, ShowAt.row, invokable);
		this.setup();
	}
	
	public EExpoRowButtonCfg(String name, Glyphicon cssIcon, boolean modal, boolean target_blank,
			ShowAt position, ActionableI<E> invokable) {
		super(name, cssIcon, modal, target_blank, position, invokable);
		this.setup();
	}
	
	private void setup(){
		this.disable(false);
		this.visible(true);
	}
	
	
	
	public EExpoRowButtonCfg<E> disable(ConditionalI cond) {
		this.disableCond = cond;
		return this;
	}
	
	public EExpoRowButtonCfg<E> disable(boolean b) {
		this.disableCond = new ConditionalDefault(b);
		return this;
	}
	
	public EExpoRowButtonCfg<E> visible(ConditionalI cond) {
		this.visibleCond = cond;
		return this;
	}
	
	public EExpoRowButtonCfg<E> visible(boolean b) {
		this.visibleCond = new ConditionalDefault(b);
		return this;
	}
	

	
	
	
	public E actualEntityRow(){
		return actualEntityRow;
	}
	
	
	
}
