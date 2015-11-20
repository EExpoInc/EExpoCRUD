package test.deleteme.fw;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class ActionBase {
	
	protected HttpServletRequest req; 
	protected HttpServletResponse resp;
	protected String registerName;
	
	public ActionBase() {
		registerName = callerActionMethod();
//		System.out.println(callerActionMethod());
	} 

	
	/**
	 * O contador comeca em 2, pq o 0 eh getStackTrace e o 1 eh callerActionMethod
	 * @return ClassnameController.methodnameAction
	 */
	 String callerActionMethod(){
		StackTraceElement[]  stes = Thread.currentThread().getStackTrace();
		for(int i =2; i<stes.length; i++){
			if(!stes[i].getMethodName().equals("<init>")){
				int l = stes[i].getClassName().split("[\\W]").length;
				String clazzName = stes[i].getClassName().split("[\\W]")[l-1];
				return clazzName+ "."+stes[i].getMethodName();
			}
		}
		return "method Caller n encontrado";
	}
	
	
	protected abstract void executeBase();
	
	
	public final String link() {
		return this.registerName;
	}

	/**
	 * pega um obj qualquer e transforma em parametros p url
	 * @param params
	 * @return
	 */
	public final String link(Object params) {
			
		return this.registerName;
	}
	
	
	
	
	public <B> B populateBean(Class<B> beanClass){
		
		this.req.getParameterMap(); // popula um bean com os dados do param esperados
		return null;
	}
	
	
}
