package test.deleteme.fw;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class ActionBase {
	
	protected HttpServletRequest req; 
	protected HttpServletResponse resp;
	
	
	
	
	protected abstract void executeBase();
	
	
	public final String link() {
		// link construido por reflection
		return "./MyController.MyActionLink/ + data.id" + "?uf="; // + data.uf;
	}
	
	public <B> B populateBean(Class<B> beanClass){
		
		this.req.getParameterMap(); // popula um bean com os dados do param esperados
		return null;
	}
	
	
}
