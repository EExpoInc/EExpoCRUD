package eexpocrudOLD;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eexpocrud.CrudfyUtils;
import eexpocrudOLD.Crudfy.CrudTableToShow;

public class CrudfyTagHelper<T, K> {

	 
	
	public HttpServletRequest req;
//	public HttpServletResponse resp;
	private Class<T> beanClazz;
	private String crudId;
	private DaoEntityAbs<T, K> daoManager;
	private Crudfy<T, K> _crudfy;

	public static enum params{
		page, itemId, daoManager, crudId, startId, jump, idLastItemPage, after, before  
	}
	
	public static enum att{
		obj
	}
	
	public CrudfyTagHelper(HttpServletRequest req, HttpServletResponse resp, Class<T> beanClazz, String crudId,
			DaoEntityAbs<T, K> daoManager) throws ServletException, IOException {
		this.req = req;
//		this.resp = resp;
		this.beanClazz = beanClazz;
		this.crudId = crudId;
		this.daoManager = daoManager;
	}
	

	
	public CrudTableToShow resolveCrudfy() throws ClassNotFoundException {
		
//		if (crudId == null) {
//			String _id = genId(req, beanClazz);
//			Object objSession = req.getSession().getAttribute(_id);
//			if (objSession != null) {
//				_crudfy = (Crudfy<T, K>) (objSession);
//			} else {
//				_crudfy = createNewCrudfy(beanClazz);
//				registerInSession(req, _crudfy);
//			}
//		} else {
			_crudfy = createNewCrudfy(beanClazz);
//		}
		
		if (daoManager != null) {
			_crudfy.daoManager(daoManager);
		}
		
		if(daoManager.estimated){
			Long idAfter = CrudfyUtils.getParam(req, params.after.name(), Long.class);
//			Long idAfter = Long.parseLong(req.getParameter(params.after.name()));
//			Long idBefore = Long.parseLong(req.getParameter(params.before.name()));
			Long idBefore = CrudfyUtils.getParam(req, params.before.name(), Long.class);
			if(idAfter != null){
				return _crudfy.listAfter(idAfter);
			}else if(idBefore != null){
				return _crudfy.listBefore(idBefore);
			}else{
				return _crudfy.initialList();
			}
			
		}else{
			Integer offset = getNavInfo();
			if (offset == null) {
				return _crudfy.initialList();
			} else {
				return _crudfy.nav(offset);
			}
			
		}
	}
	
	public String genReadLink(String idItem) {
		String result = "";
		// result = this.req.getRequestURI();
		// result += "?"+PAGE_PARAM+ "="+pageOffset;
		
		result = this.req.getContextPath();
		result += "&" + params.itemId + "=" + idItem;
		
		result += "&" + params.daoManager + "=" + this.daoManager.getClass();
		return result;
	}
	
	public String genNavLink(int pageOffset, boolean firstLastComposition) {
		if (firstLastComposition) {
			return "#";
		} else {
			return genNavLink(pageOffset);
		}
	}
	
	public String genEstimatedNavLink(long idLastItemPage, int jump) {
		String result = "";
		result = this.req.getRequestURI();
		result += "?" + params.idLastItemPage + "=" + idLastItemPage;
		result += "&" + params.jump + "=" + jump;		
		return result;
	}

	
	
	public String genNavLink(int pageOffset) {
		String result = "";
		result = this.req.getRequestURI();
		result += "?" + params.page + "=" + pageOffset;
//		result += 
		
		return result;
	}
	
	
	private Integer getJump() {		
		int jump;
		try {
			jump = Integer.parseInt(req.getParameter(params.jump.toString()));			
		} catch (Exception e) {
			return null;
		}		
		return jump;		
	}
	
	private Integer getNavInfo() {
		int page;
		try {
			page = Integer.parseInt(req.getParameter(params.page.toString()));
		} catch (Exception e) {
			return null;
		}
		
		return page;
	}
	
	private static <T, K> Crudfy<T, K> createNewCrudfy(Class beanClazz) {
		Crudfy<T, K> crudfy = Crudfy.beanClass(beanClazz);
		return crudfy;
	}
	
	private static void registerInSession(HttpServletRequest req, Crudfy<?, ?> crudfy) {
		// crudfy.registeredInSession = true;
		if (crudfy.id == null) {
			crudfy.id = crudfy.beanClazz.getSimpleName() + genId(req, crudfy.beanClazz);
		}
		
		req.getSession().setAttribute(crudfy.id, crudfy);
	}
	
	
	private static String genId(HttpServletRequest req, Class<?> clazz) {
		return req.getRequestURI() + "___" + clazz.getSimpleName();
	}
	
}
