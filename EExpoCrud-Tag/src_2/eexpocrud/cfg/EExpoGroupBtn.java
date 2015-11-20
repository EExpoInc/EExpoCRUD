package eexpocrud.cfg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import eexpocrud.action.CrudfyServlet.ACT;

@SuppressWarnings("serial")
public class EExpoGroupBtn<E> implements Serializable {
	EExpoButtonCfg<E> createBtn;
	EExpoRowButtonCfg<E> readBtn, updateBtn, deleteBtn;
	public Map<String, EExpoButtonCfg<E>> actionName_Btn = new LinkedHashMap<String, EExpoButtonCfg<E>>();
	
	protected ArrayList<EExpoButtonCfg<E>> addtionalBtns = new ArrayList<>();
	protected ArrayList<EExpoButtonCfg<E>> headerBtns = new ArrayList<>();
	protected ArrayList<EExpoRowButtonCfg<E>> rowBtns = new ArrayList<>();
	EExpoButtonCfg<E> lineBtn;
	E rowEntity;
	protected String eexpoCrudCfgId;
	
	public EExpoGroupBtn(String eexpoCrudCfgId) {
		this.eexpoCrudCfgId = eexpoCrudCfgId;
	}
	
	public static class DuplicateNameActionButtonException extends Exception {
		String nameAction;
		
		public DuplicateNameActionButtonException(String nameAction) {
			super("nameAction = " + nameAction);
			this.nameAction = nameAction;
		}
	}
	
	
	private void addBtns(ArrayList<? super EExpoRowButtonCfg<E>> listToAdd, EExpoButtonCfg<E> btn) throws DuplicateNameActionButtonException {
		if (actionName_Btn.containsKey(btn.name)) {			
			throw new DuplicateNameActionButtonException(btn.name);
		} else {
			btn.eexpoCrudCfgId = this.eexpoCrudCfgId;
			this.actionName_Btn.put(btn.name, btn);			
			listToAdd.add((EExpoRowButtonCfg<E>) btn);
		}
	}
	
	public void addHeaderBtns(EExpoButtonCfg<E> btn) throws DuplicateNameActionButtonException {
		this.addBtns(headerBtns, btn); 
	}
	public void addRowBtns(EExpoButtonCfg<E> btn) throws DuplicateNameActionButtonException {
		this.addBtns(rowBtns, btn); 
	}
	
	
	public ArrayList<EExpoRowButtonCfg<E>> rowBtns(){
		return this.rowBtns;
	}
	
	
	
	
	public EExpoButtonCfg<E> createBtn() {
		return createBtn;
	}
	
	public EExpoRowButtonCfg<E> readBtn() {
		return readBtn;
	}
	
	public EExpoRowButtonCfg<E> updateBtn() {
		
		return this.updateBtn;
	}
	
	public EExpoRowButtonCfg<E> deleteBtn() {
		return deleteBtn;
	}
	
	public EExpoButtonCfg<E> createBtn(EExpoButtonCfg<E> createBtnNew) {
		createBtnNew.eexpoCrudCfgId = this.eexpoCrudCfgId;
		createBtnNew.act = ACT.createPrepare;
		this.createBtn = createBtnNew;
		this.headerBtns.add(createBtnNew);
		return this.createBtn;
	}
	
	public EExpoRowButtonCfg<E> readBtn(EExpoRowButtonCfg<E> readBtnNew) {
		readBtnNew.eexpoCrudCfgId = this.eexpoCrudCfgId;
		readBtnNew.act = ACT.read;
		this.readBtn = readBtnNew;
		this.lineBtn = readBtnNew;
		return this.readBtn;
	}
	
	public EExpoRowButtonCfg<E> updateBtn(EExpoRowButtonCfg<E> updateBtnNew) {
		updateBtnNew.eexpoCrudCfgId = this.eexpoCrudCfgId;
		updateBtnNew.act = ACT.updatePrepare;
		this.updateBtn = updateBtnNew;
		this.rowBtns.add(updateBtnNew);
		return this.updateBtn;
	}
	
	public EExpoRowButtonCfg<E> deleteBtn(EExpoRowButtonCfg<E> deleteBtnNew) {
		deleteBtnNew.eexpoCrudCfgId = this.eexpoCrudCfgId;
		deleteBtnNew.act = ACT.deletePrepare;
		this.deleteBtn = deleteBtnNew;
		this.rowBtns.add(deleteBtnNew);
		return this.deleteBtn;
	}
	
}
