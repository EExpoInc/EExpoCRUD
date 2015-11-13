package eexpocrud.cfg;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eexpocrud.CrudfyUtils;
import eexpocrud.action.ReadAction;
import eexpocrud.cfg.EExpoButtonCfg.ActionableI;
import eexpocrud.cfg.EExpoButtonCfg.ButtonBootstrapCssClass;
import eexpocrud.dao.impl.jpa.two.JpaDAO;


@SuppressWarnings("serial")
public class EExpoCrudCfg<E extends Serializable, ID extends Comparable<ID>> implements Serializable {
	
	public static int maxCfgPerSession = 100;
	public String id;
	protected ListPageCfg<E, ID> listPageCfg;
	private transient JpaDAO<E, ID> jpaDao;
	protected transient HttpServletRequest req;
	protected transient HttpServletResponse resp;
	
	public EExpoCrudCfg(HttpServletRequest req, HttpServletResponse resp, JpaDAO<E, ID> jpaDao) {
		System.out
				.println("public EExpoCrudCfg(HttpServletRequest req, HttpServletResponse resp, JpaDAO<E, ID> jpaDao) {");
		this.req = req;
		this.resp = resp;
		this.jpaDao = jpaDao;
		setupDefaultCfg();
		setupDefaultId();
	}
	
	private void setupDefaultId() {
		this.id = genDefaultId(req);
	}
	
	public static String genDefaultId(HttpServletRequest req) {
		String id = EExpoCrudCfg.class.getSimpleName() + "_";
		id += CrudfyUtils.urlfy(req.getRequestURI());
		return id;
	}
	
	public ListPageCfg<E, ID> listPageCfg() {
		return this.listPageCfg;
	}
	
	public void refresh(HttpServletRequest req, HttpServletResponse resp, JpaDAO<E, ID> jpaDao) {
		this.req = req;
		this.resp = resp;
		this.jpaDao = jpaDao;
	}
	
	// o listAction pega as config, gera a listagem, e manda p de volta p o
	// taghelper ou p o dispatch jsp
	
	
	
	private void setupDefaultCfg() {
		setupDefaultBtn();
//		this.listPageCfg.queryCfg;
		this.jpaDao.queryCfg(this.listPageCfg.queryCfg);
	}
	
	
	

	private void setupDefaultBtn() { 
		this.listPageCfg = new ListPageCfg<>(jpaDao.clone());
//		this.listPageCfg.queryCfg.listLimitPerPage(10);
		
		this.listPageCfg.groupBtn.createBtn(new EExpoButtonCfg<E>("create", "glyphicon glyphicon-plus",
				new ActionableI<E>() {
					@Override
					public E action() {
						ReadAction<E, ID> act = new ReadAction<>(req, resp, jpaDao);
						act.execute();
						return null;
					}
				}).buttonCssClass(ButtonBootstrapCssClass.success));
		
		this.listPageCfg.groupBtn.readBtn(new EExpoRowButtonCfg<E>("read", new ActionableI<E>() {
			@Override
			public E action() {
				ReadAction<E, ID> act = new ReadAction<>(req, resp, jpaDao);
				act.execute();
				return null;
			}
		}));
		
		final EExpoRowButtonCfg<E> updateBtn = new EExpoRowButtonCfg<>("update", "glyphicon glyphicon-edit",
				new ActionableI<E>() {
					@Override
					public E action() {
						return null;
					}
				});
		updateBtn.buttonCssClass(ButtonBootstrapCssClass.warning);
		
		this.listPageCfg.groupBtn.updateBtn(updateBtn);
		
		this.listPageCfg.groupBtn.deleteBtn(
				new EExpoRowButtonCfg<E>("delete", "glyphicon glyphicon-remove-sign", new ActionableI<E>() {
					@Override
					public E action() {
						return null;
					}
				})).buttonCssClass(ButtonBootstrapCssClass.danger);
	}
}
