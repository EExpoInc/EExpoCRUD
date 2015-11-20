package eexpocrud.cfg;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import eexpocrud.action.CrudfyServlet;
import eexpocrud.action.CrudfyServlet.ACT;

/**
 * EExpoButtonCfg é usada para configurar a exibicao do botao, e tb para
 * direcionar a request p a action desejada atraves do invokable no momento q o
 * click for recebido pelo servidor.
 * 
 * @author @fulvius
 * @param <E>
 * 
 * 
 */
@SuppressWarnings("serial")
public class EExpoButtonCfg<E> implements Serializable{
	
	public static interface ActionableI<E> extends Serializable{

		 E action();
		
	}

	
	public static enum ShowAt {
		row, top
	}
	
	public static enum ButtonBootstrapCssClass {
		primary, success, info, warning, danger;
		String toPrint = "btn btn-xs btn-";
		private ButtonBootstrapCssClass() {
			this.toPrint += this.name();
		}
		
		@Override
		public String toString() {			
			return this.toPrint;
		}
	}
	
	public String name;
	public String cssIcon;
	public String preMsg;
	public String successMsg;
	public String failureMsg;
	public String linkPathToDispatch;
	protected String eexpoCrudCfgId; 
	public ACT act;
	
	public ActionableI<? extends E> invokable;
	boolean modal;
	ShowAt position = ShowAt.row;
	public ButtonBootstrapCssClass buttonCssClass = ButtonBootstrapCssClass.info;
//	ConditionalI disableCond;
//	ConditionalI visibleCond;
//	E actualRow;
	
	public EExpoButtonCfg(String name, String cssIcon, String preMsg, String successMsg, String failureMsg,
			ShowAt position, ActionableI<E> invokable) {
		this.name = name;
		this.cssIcon = cssIcon;
		this.preMsg = preMsg;
		this.successMsg = successMsg;
		this.failureMsg = failureMsg;
		this.invokable = invokable;
		this.act = ACT.CUSTOM;
		
	}

	
	/*
	 * botao inline ou usado de outras formas. Ideal p cadastrar uma action facilitada
	 */
	public EExpoButtonCfg(String name,  ActionableI<E> invokable) {
		this(name, "", true, false, ShowAt.row, invokable);
//		this.visible(false);
	}
	public EExpoButtonCfg(ActionableI<E> invokable) {
		this(null, "", true, false, ShowAt.row, invokable);
		this.name = "";
//		this.visible(false);
	}

	
	public EExpoButtonCfg(String name, String cssIcon, ActionableI<E> invokable) {
		this(name, cssIcon, true, false, ShowAt.row, invokable);
	}
	
	public EExpoButtonCfg(String name, String cssIcon, boolean modal, boolean target_blank, ShowAt position,
			ActionableI<E> invokable) {
		this(name, cssIcon, null, null, null, position, invokable);
		
//		this.name = name;
//		this.cssIcon = cssIcon;
		this.modal = modal; 
	}
	
	public EExpoButtonCfg<? extends E> action(ActionableI<? extends E> invokable) {
		this.invokable = invokable;
		return this;
	}
	
	public EExpoButtonCfg<E> buttonCssClass(ButtonBootstrapCssClass buttonCssClass) {
		this.buttonCssClass = buttonCssClass;
		return this;
		
	}
	
	
	public String link(HttpServletRequest req){
		String params = this.act.name();
		if(this.act == ACT.CUSTOM){
			if(invokable != null){
				params =  this.invokable.getClass().getName();	
			}
		}
		String crudCfgId = CrudfyServlet.PARAMS.CRUD_CFG_ID + "="+this.eexpoCrudCfgId;
		
		return req.getContextPath()+"/"+this.act.servletName()+"?" + crudCfgId +"&" +params;
	}
	
	public String link(Object id,HttpServletRequest req){
		return this.link(req)+"="+id;
	}


	
}
