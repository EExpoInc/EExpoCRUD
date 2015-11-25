package eexpocrud.action;

import java.io.IOException;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eexpocrud.CrudfyUtils;
import eexpocrud.bo.CrudfyBO;
import eexpocrud.cfg.EExpoButtonCfg;
import eexpocrud.cfg.EExpoCrudCfg;
import eexpocrud.dao.impl.jpa.two.JpaDAO;



/****
 * 
 * @author fulviolonghi 
 * 
 * url = /CrudfyServlet?(?(?[ACT]=[int])&(?type=[String]))
 *         /CrudfyServlet?edit=987
 *         		&type=UsuarioEntity
 *         		&crudfyCfgId=id
 * 
 * CrudfyController: 	pega os dados Strings da request, cria um ControlData e 
 * 						repassa p o BO. Sabe tb como formatar e lidar com o retorno do BO.
 * 						
 * 
 * ControlData:			parse dos param da request p dados uteis p o BO. 
 *         
 */  

@WebServlet("/CrudfyServlet2")
public class CrudfyServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
	
	
	
	
	public static enum PARAMS {
		type, act, 	pu, CRUD_CFG_ID
	};
	
	public static String servletName(){
		return CrudfyServlet2.class.getSimpleName();
	}

	
	public  enum ACT {
		
		create, read, update, delete, updatePrepare, deletePrepare, list, createPrepare, CUSTOM;
		
		
		public String toString(){
			if(this == CUSTOM){
				return servletName()+"?";
			}else{
				return servletName()+"?"+this.name();	
			}			
		}
		
		public String servletName(){
			return CrudfyServlet2.servletName();
		}
		
		
//		protected String link(HttpServletRequest req){
//			return req.getContextPath()+"/" +this.toString();
//		}
//		protected String link(Object id,HttpServletRequest req){
//			return this.link(req)+"="+id;
//		}
	};
	
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		
		doControl(new ControlDataInputOLD(request, response)); 
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		
		doControl(new ControlDataInputOLD(request, response));
	}
	
	
	
	private void doControl(ControlDataInputOLD dc) {
//		switch (dc.act) {
//			case create: this.create(dc.obj); break;
//			case read: this.read(dc.entityId); break;
//			case update: this.update(dc.obj); break;
//			case delete: this.delete(dc.entityId); break;
//			case createPrepare: this.createPrepare(); break;
//			case updatePrepare: this.updatePrepare(dc); break; 
//			case deletePrepare: this.deletePrepare(dc.entityId); break;
//		
//			default: break;
//		}
	}

	private void delete(Object id) {

		
	}

	private void update(Object obj) {
		// TODO Auto-generated method stub
		
	}

	private void read(Object id) {
		// TODO Auto-generated method stub
		
	}

	private void create(Object obj) {
		// TODO Auto-generated method stub
		
	}

	private void deletePrepare(Object id) {
//		CrudfyBO bo = new CrudfyBO();
//		bo.read(id); 
		// TODO Auto-generated method stub
		 
	}

	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void updatePrepare(ControlDataInputOLD dc) {
//		resolveCrudCfg(dc).
		
		
		
		EExpoCrudCfg< ?, ?>  cfg = dc.crudCfg;
		
		Object idEntity = CrudfyUtils.parseFromString(dc.entityId, cfg.jpaDao.idClass);
		
		CrudfyBO bo = new CrudfyBO(cfg.jpaDao.em, cfg.jpaDao.entityClass);
		Object obj= bo.read(idEntity);
//		UserEntity ue = (UserEntity) obj;
//		System.out.println(ue.nome);
		
		Map<String, ?> map = cfg.listPageCfg.groupBtn.actionName_Btn;
		for(Object v : map.values()){
			EExpoButtonCfg<?> btn = (EExpoButtonCfg<?>) v;
//			btn.invokable.action(); 
		}
		
//		System.out.println(cfg.listPageCfg.groupBtn.actionName_Btn); 
		
		// TODO Auto-generated method stub
		
	}

	private void createPrepare() {
 
		// TODO Auto-generated method stub
		
	}
	
	
	private EntityManager resolveEntityManager(String pu){
		return JpaDAO.getOrAddEMFactory(pu).createEntityManager();
	}
	
	

	

	
	
}
