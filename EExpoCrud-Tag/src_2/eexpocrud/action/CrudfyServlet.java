package eexpocrud.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eexpocrud.bo.CrudfyBO;



/****
 * 
 * @author fulviolonghi 
 * 
 * url = /CrudfyServlet?(?(?[ACT]=[int])&(?type=[String]))
 *         /CrudfyServlet?edit=987&type=UsuarioEntity
 * 
 * CrudfyController: 	pega os dados Strings da request, cria um ControlData e 
 * 						repassa p o BO. Sabe tb como formatar e lidar com o retorno do BO.
 * 						
 * 
 * ControlData:			parse dos param da request p dados uteis p o BO. 
 *         
 */  

@WebServlet("/CrudfyServlet")
public class CrudfyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
	
	private CrudfyBO bo = new CrudfyBO();
	
	public static enum PARAMS {
		type, act
	};
	
	public static String servletName(){
		return CrudfyServlet.class.getSimpleName();
	}

	
	public static enum ACT {
		create, read, update, delete, preCreate, preUpdate, preDelete, list;
		
		public String toString(){
			return servletName()+"?"+this.name()+"=";
		}
	};
	
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		
		
		doControl(new ControlDataInput(request));
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
	}
	
	
	
	private void doControl(ControlDataInput dc) {
		switch (dc.act) {
			case create: this.create(dc.obj); break;
			case read: this.read(dc.id); break;
			case update: this.update(dc.obj); break;
			case delete: this.delete(dc.id); break;
			case preCreate: this.preCreate(); break;
			case preUpdate: this.preUpdate(dc.id); break;
			case preDelete: this.preDelete(dc.id); break;
		
			default: this.bo.list(dc.nav); break;
		}
	}

	private void delete(String id) {

		
	}

	private void update(Object obj) {
		// TODO Auto-generated method stub
		
	}

	private void read(String id) {
		// TODO Auto-generated method stub
		
	}

	private void create(Object obj) {
		// TODO Auto-generated method stub
		
	}

	private void preDelete(String id) {
		CrudfyBO bo = new CrudfyBO();
		bo.read(id); 
		// TODO Auto-generated method stub
		
	}

	private void preUpdate(String id) {
		CrudfyBO bo = new CrudfyBO();
		bo.read(id); 
		
		// TODO Auto-generated method stub
		
	}

	private void preCreate() {

		// TODO Auto-generated method stub
		
	}
	
	
	

	

	
	
}
