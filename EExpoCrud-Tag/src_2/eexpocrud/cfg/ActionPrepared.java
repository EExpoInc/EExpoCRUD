package eexpocrud.cfg;


@SuppressWarnings("serial")
public abstract class ActionPrepared<E> extends ActionableI<E>{
	public static final String ACTION_FORM_ATTR = "actionForm";
	
	public final String jspToDispatch;
	
	public ActionPrepared(String jspToDispatch) {
		this.jspToDispatch = jspToDispatch;
	}
	
	 public abstract E prepare();
 }