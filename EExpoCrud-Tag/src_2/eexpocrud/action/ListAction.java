package eexpocrud.action;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eexpocrud.CrudfyUtils;
import eexpocrud.dao.ObjectfyDAO;
import eexpocrud.dao.impl.jpa.two.JpaDAO;


public class ListAction<E extends Serializable, ID extends Comparable<ID>> {
	public static enum params {
		after, before, begin, end
	}
	
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private ObjectfyDAO<E, ID> dao;
	private JpaDAO<E, ID> jpaDao;
	private ID afterId;
	private ID beforeId;
	
	private boolean begin, end;
	
	public ListAction(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
		// extractParams();
	}
	
	public ListAction(HttpServletRequest req, HttpServletResponse resp, JpaDAO<E, ID> jpaDao) {
		this(req, resp);
		this.jpaDao = jpaDao;
		extractParams();
	}
	
	public ListAction(HttpServletRequest req, HttpServletResponse resp, ObjectfyDAO<E, ID> dao) {
		this(req, resp);
		this.dao = dao;
		extractParams();
	}
	
	public String getIdClass() {
		return beforeId.getClass() + "";
	}
	
	private void extractParams() {
		beforeId = CrudfyUtils.getParam(req, params.before.name(), jpaDao.idClass);
		afterId = CrudfyUtils.getParam(req, params.after.name(), jpaDao.idClass);
		begin  = req.getParameter(params.begin.name()) != null ? true : false;
		end  = req.getParameter(params.end.name()) != null ? true : false;
	}
	
	
	public ListActionData<E, ID> genTable() {
		return genTable(true);
	}
	
	public ListActionData<E, ID> genTable(boolean closeConnAtEnd) {
		this.extractParams();
		ListActionData<E, ID> result;
		boolean hitBegin = false;
		boolean hitEnd = false;

		
		List<E> list;
		if(begin){
			list = jpaDao.listBegin();	
		}else if(end){
			list = jpaDao.listEnd();
		}else if (beforeId != null) {
			list = jpaDao.listBefore(beforeId);
			if (list.size() < jpaDao.limitList()) {
				jpaDao = jpaDao.clone();
				list = jpaDao.listBegin();
				hitBegin = true;
			}
		} else if (afterId != null) {
			list = jpaDao.listAfter(afterId);
			if (list.size() < jpaDao.limitList()) {
				jpaDao = jpaDao.clone();
				list = jpaDao.listEnd();
				hitEnd = true;
			}
			
		} else {
			list = jpaDao.listBegin();
		}
		
		Collections.reverse(list);
		result = new ListActionData<E, ID>(list, jpaDao.entityClass);
		result.hitBegin = hitBegin;
		result.hitEnd = hitEnd;
		if(closeConnAtEnd){
			jpaDao.em.close();
		}
		
		
		return result;
		
	}
	
	
	
}
