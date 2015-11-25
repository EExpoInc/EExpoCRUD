package eexpocrud.cfg;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import eexpocrud.action.CrudfyServlet;

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
	public Glyphicon cssIcon;
	public String preMsg;
	public String successMsg;
	public String failureMsg;
	public String linkPathToDispatch;
	protected String eexpoCrudCfgId; 
//	public ACT act;
	
	public ActionableI<? extends E> invokable;
	boolean modal;
	ShowAt position = ShowAt.row;
	public ButtonBootstrapCssClass buttonCssClass = ButtonBootstrapCssClass.info;
//	ConditionalI disableCond;
//	ConditionalI visibleCond;
//	E actualRow;
	
	public EExpoButtonCfg(String name, Glyphicon cssIcon, String preMsg, String successMsg, String failureMsg,
			ShowAt position, ActionableI<E> invokable) {
		this.name = name;
		this.cssIcon = cssIcon;
		this.preMsg = preMsg;
		this.successMsg = successMsg;
		this.failureMsg = failureMsg;
		this.invokable = invokable;
//		this.act = ACT.CUSTOM;
		
	}

	
	/*
	 * botao inline ou usado de outras formas. Ideal p cadastrar uma action facilitada
	 */
	public EExpoButtonCfg(String name,  ActionableI<E> invokable) {
		this(name, null, true, false, ShowAt.row, invokable);
//		this.visible(false);
	}
	public EExpoButtonCfg(ActionableI<E> invokable) {
		this(null, null, true, false, ShowAt.row, invokable);
//		this.name = null;
//		this.visible(false);
	}

	
	public EExpoButtonCfg(String name, Glyphicon cssIcon, ActionableI<E> invokable) {
		this(name, cssIcon, true, false, ShowAt.row, invokable);
	}
	
	public EExpoButtonCfg(String name, Glyphicon cssIcon, boolean modal, boolean target_blank, ShowAt position,
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
		String crudCfgIdParam = CrudfyServlet.PARAMS.crudCfgId + "="+this.eexpoCrudCfgId;
		String actNameParam = CrudfyServlet.PARAMS.act + "="+this.name;
		
		return req.getContextPath()+"/"+CrudfyServlet.servletName()+"?" + crudCfgIdParam +"&" +actNameParam;
	}
	
	public String linkPrepare(Object id, HttpServletRequest req){
		String result = this.link(id, req);
		
		if (this.invokable instanceof ActionEntityPrepared || this.invokable instanceof ActionPrepared) {
			result += "&" + CrudfyServlet.PARAMS.prepare + "="+true;
		}
		
		return result;
	}
	public String link(Object id,HttpServletRequest req){
		return this.link(req) + "&" + CrudfyServlet.PARAMS.entityId + "=" + id;
	}


	
}
