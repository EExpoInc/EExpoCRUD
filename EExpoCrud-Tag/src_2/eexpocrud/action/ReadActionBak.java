package eexpocrud.action;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eexpocrud.CrudfyUtils;
import eexpocrud.dao.ObjectfyDAO;
import eexpocrud.dao.impl.jpa.two.JpaDAO;

public class ReadActionBak<E extends Serializable, ID extends Comparable<ID>> {
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
	
	public ReadActionBak(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
		System.err.println("public ReadAction(HttpServletRequest req, HttpServletResponse resp) {");
		// extractParams();
	}
	
	public ReadActionBak(HttpServletRequest req, HttpServletResponse resp, JpaDAO<E, ID> jpaDao) {
		this(req, resp);
		this.jpaDao = jpaDao;
		extractParams();
	}
	
	public ReadActionBak(HttpServletRequest req, HttpServletResponse resp, ObjectfyDAO<E, ID> dao) {
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

		
		
		return null;
		
	}

	public void execute() {
		// TODO Auto-generated method stub
		
	}
	
}
