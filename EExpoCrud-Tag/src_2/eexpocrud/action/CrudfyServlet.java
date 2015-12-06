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
import eexpocrud.cfg.EExpoCrudCfgManager;
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

@SuppressWarnings("serial")
@WebServlet("/CrudfyServlet")
public class CrudfyServlet extends HttpServlet {
	
	   
	
	public static enum PARAMS {
		act, crudCfgId, entityId, prepare
	};
	
	public static String servletName(){

		
		return CrudfyServlet.class.getSimpleName();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		
		doControl(new CrudfyData(request, response)); 
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		
		doControl(new CrudfyData(request, response));
	}
	
	
	
	private void doControl(CrudfyData cd) {
		EExpoCrudCfg< ?, ?>  cfg = cd.eexpoCrudCfg();
//		Object entityId = CrudfyUtils.parseFromString(cd.entityId, cfg.jpaDao.idClass);
//		CrudfyBO bo = new CrudfyBO(cfg.jpaDao.em, cfg.jpaDao.entityClass);
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
