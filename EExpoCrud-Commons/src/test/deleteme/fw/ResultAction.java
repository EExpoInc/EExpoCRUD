package test.deleteme.fw;

import javax.servlet.http.HttpServletRequest;



public abstract class ResultAction<R> extends ActionBase {

	R result;
	
	
	
	protected final void executeBase(){  
		//evita a chamada por fora
	}
	
	protected abstract R execute();
	
	
	public R result(){
		return result;
	}
	
	@SuppressWarnings("unchecked") 
	public static <R> R result(HttpServletRequest req){
		return (R) req.getAttribute(IoAction.class.getCanonicalName());
	}

	
	
	
}
