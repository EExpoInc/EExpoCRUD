package test.deleteme.fw;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class ActionBase {
	
	protected HttpServletRequest req; 
	protected HttpServletResponse resp;
	
	
	
	
	protected abstract void execute();
	
	
	public final String link() {
		// link construido por reflection
		return "./MyController.MyActionLink/ + data.id" + "?uf="; // + data.uf;
	}
	
	

	
	
}
