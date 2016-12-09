package eexpocrud.cfg;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eexpocrud.CrudfyUtils;
import eexpocrud.cfg.EExpoButtonCfg.ButtonBootstrapCssClass;
import eexpocrud.cfg.EExpoGroupBtn.DuplicateNameActionButtonException;
import eexpocrud.dao.impl.jpa.two.JpaDAO;


@SuppressWarnings("serial")
public class EExpoCrudCfg<E extends Serializable, ID extends Comparable<ID>> implements Serializable {
	
	public static int maxCfgPerSession = 100;
	public String id;
	public ListPageCfg<E, ID> listPageCfg;
	public JpaDAO<E, ID> jpaDao;
//	private String persistenceUnit; 
	protected transient HttpServletRequest req;
	protected transient HttpServletResponse resp;
	
	public Class<? extends E> viewClass;
	
	//TODO: colocar o actionable da row aqui
	
	
	public EExpoCrudCfg(HttpServletRequest req, HttpServletResponse resp, JpaDAO<E, ID> jpaDao) throws DuplicateNameActionButtonException {
		
		this.req = req;
//		this.persistenceUnit = jpaDao.persistenceUnit;
		setupDefaultId();
		this.resp = resp;
		this.jpaDao = jpaDao;
		setupDefaultCfg();
		
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
	
	public void refresh(HttpServletRequest req, HttpServletResponse resp){ //,  JpaDAO<E, ID> jpaDao) {
		this.req = req;
		this.resp = resp;
		this.jpaDao.refresh();
//		this.jpaDao = jpaDao;
	}
	
	// o listAction pega as config, gera a listagem, e manda p de volta p o
	// taghelper ou p o dispatch jsp
	
	
	public Object showAsView(Object entity){
		if(viewClass != null){
			E obj = null;
			try {
				obj = viewClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {		
				e.printStackTrace();
			}
			CrudfyUtils.beanTransfusion(obj, entity);
			return obj;			
		}else{
			return entity;
		}
	}
	
	private void setupDefaultCfg() throws DuplicateNameActionButtonException {
		setupDefaultBtn();
//		this.listPageCfg.queryCfg;
		this.jpaDao.queryCfg(this.listPageCfg.queryCfg);
	}
	

	private void setupDefaultBtn() throws DuplicateNameActionButtonException { 
		this.listPageCfg = new ListPageCfg<>(jpaDao.clone(), this);
		this.listPageCfg.queryCfg.listLimitPerPage(10);
//		final CrudfyBO<E> bo = new CrudfyBO<>(jpaDao.em, jpaDao.entityClass);
		
		this.listPageCfg.groupBtn.createBtn(new EExpoButtonCfg<E>("create", Glyphicon.plus,
				new ActionEntityPrepared<E>("./crudfyCreatePrepare.jsp") {
				@Override
				public E action() {
					
					bo.create(entity);
					return entity;
				} 
				}).buttonCssClass(ButtonBootstrapCssClass.success));
		
		
		this.listPageCfg.groupBtn.readBtn(new EExpoRowButtonCfg<E>("read", new ActionEntityPrepared<E>("./crudfyRead.jsp") {
			@Override
			public E action() {
				return null;
//				return bo.read(entityId);x
			}
		}));
		
		
		
		final EExpoRowButtonCfg<E> updateBtn = new EExpoRowButtonCfg<>("update", Glyphicon.edit,
				new ActionEntityPrepared<E>("./crudfyUpdatePrepare.jsp") {
					@Override 
					public E action() {
						this.bo.update(this.entity);
						return this.entity;
					}
				});

		updateBtn.buttonCssClass(ButtonBootstrapCssClass.warning);
		
		this.listPageCfg.groupBtn.updateBtn(updateBtn);
		
		this.listPageCfg.groupBtn.deleteBtn(
				new EExpoRowButtonCfg<E>("delete", Glyphicon.remove_sign, new ActionEntityPrepared<E>(
						"./crudfyDeletePrepare.jsp") {
					@Override
					public E action() { 
						this.bo.delete(this.entity);
						return this.entity;
					}
				})).buttonCssClass(ButtonBootstrapCssClass.danger);
	}
	
	
	
	
	
	
}
