package test.deleteme.fw;

import javax.servlet.http.HttpServletRequest;


public abstract class NoInputAction<R> extends ActionBase {

	R result;
	
	
	
	protected final void execute(){  
		
	}
	
	protected abstract R executeNoInput();
	
	
	public R result_(){
		return result;
	}
	
	
	@SuppressWarnings("unchecked") 
	public static <R> R result(HttpServletRequest req){
		return (R) req.getAttribute(NoInputAction.class.getCanonicalName());
	}
	
	
}
