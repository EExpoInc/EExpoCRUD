package test.deleteme.fw;

import javax.servlet.http.HttpServletRequest;


public abstract class SimpleAction<I, O> extends ActionBase {
	I input;
	O output;
	
	
	protected SimpleAction(I input) {
		this.input = input;
	}
	
	protected final void execute(){  
		
	}
	
	protected abstract O execute(I obj);
	
	
	public O output(){
		return output;
	}
	 
	protected SimpleAction<I, O> output(O output){
		this.output = output;
		return this;
	}
	
	
	@SuppressWarnings("unchecked") 
	public static <O> O output(HttpServletRequest req){
		return (O) req.getAttribute(SimpleAction.class.getCanonicalName());
	}
	
	
}
