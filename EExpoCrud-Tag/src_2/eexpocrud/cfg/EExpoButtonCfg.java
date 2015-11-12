package eexpocrud.cfg;

import java.io.Serializable;

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
	public String jspPathToDispatch;
	ActionableI<? extends E> invokable;
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
		
	}

	
	/*
	 * botao inline ou usado de outras formas. Ideal p cadastrar uma action facilitada
	 */
	public EExpoButtonCfg(String name,  ActionableI<E> invokable) {
		this(name, "", true, false, ShowAt.row, invokable);
//		this.visible(false);
	}

	
	public EExpoButtonCfg(String name, String cssIcon, ActionableI<E> invokable) {
		this(name, cssIcon, true, false, ShowAt.row, invokable);
	}
	
	public EExpoButtonCfg(String name, String cssIcon, boolean modal, boolean target_blank, ShowAt position,
			ActionableI<E> invokable) {
		this.name = name;
		this.cssIcon = cssIcon;
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

	
//	public EExpoButtonCfg<E> disable(ConditionalI cond) {
//		this.disableCond = cond;
//		return this;
//	}
//	
//	public EExpoButtonCfg<E> disable(boolean b) {
//		this.disableCond = new ConditionalDefault(b);
//		return this;
//	}
//	
//	public EExpoButtonCfg<E> visible(ConditionalI cond) {
//		this.visibleCond = cond;
//		return this;
//	}
//	
//	public EExpoButtonCfg<E> visible(boolean b) {
//		this.visibleCond = new ConditionalDefault(b);
//		return this;
//	}
//	
//	public EExpoButtonCfg<E> buttonCssClass(ButtonCssClass buttonCssClass) {
//		this.buttonCssClass = buttonCssClass;
//		return this;
//		
//	}
	
}
