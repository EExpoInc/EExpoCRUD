package eexpocrud.cfg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class EExpoGroupBtn<E> implements Serializable {
	EExpoButtonCfg<E> createBtn;
	EExpoRowButtonCfg<E> readBtn, updateBtn, deleteBtn;
	public Map<String, EExpoButtonCfg<E>> actionName_Btn = new LinkedHashMap<String, EExpoButtonCfg<E>>();
	
	protected ArrayList<EExpoButtonCfg<E>> headerBtns = new ArrayList<>();
	protected ArrayList<EExpoRowButtonCfg<E>> rowBtns = new ArrayList<>();
	public EExpoButtonCfg<E> lineBtn;
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
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void addBtns(ArrayList listToAdd, EExpoButtonCfg<E> btn) throws DuplicateNameActionButtonException {
		registerBtn(btn);
		listToAdd.add(btn);
		
	}
	
	
	private void registerBtn(EExpoButtonCfg<E> btn) throws DuplicateNameActionButtonException{
		if(btn.name == null){
			btn.name = btn.invokable.getClass().getName();
		}
		if (actionName_Btn.containsKey(btn.name)) {			
			throw new DuplicateNameActionButtonException(btn.name);
		} else {
			btn.eexpoCrudCfgId = this.eexpoCrudCfgId;
			this.actionName_Btn.put(btn.name, btn);			
			
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

	public ArrayList<EExpoButtonCfg<E>> headerBtns(){
		return this.headerBtns;
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
	
	public EExpoButtonCfg<E> createBtn(EExpoButtonCfg<E> createBtnNew) throws DuplicateNameActionButtonException {
		createBtnNew.eexpoCrudCfgId = this.eexpoCrudCfgId;
//		createBtnNew.act = ACT.createPrepare;
		this.createBtn = createBtnNew; 
		this.addHeaderBtns(createBtnNew);
//		this.headerBtns.add(createBtnNew);
		return this.createBtn;
	}
	
	public EExpoRowButtonCfg<E> readBtn(EExpoRowButtonCfg<E> readBtnNew) throws DuplicateNameActionButtonException {
		readBtnNew.eexpoCrudCfgId = this.eexpoCrudCfgId;
//		readBtnNew.act = ACT.read;
		this.readBtn = readBtnNew;
		this.lineBtn = readBtnNew;
		this.registerBtn(readBtnNew);
		return this.readBtn;
	}
	
	public EExpoRowButtonCfg<E> updateBtn(EExpoRowButtonCfg<E> updateBtnNew) throws DuplicateNameActionButtonException {
		updateBtnNew.eexpoCrudCfgId = this.eexpoCrudCfgId;
//		updateBtnNew.act = ACT.updatePrepare;
		this.updateBtn = updateBtnNew;
		this.addRowBtns(updateBtnNew);
//		this.rowBtns.add(updateBtnNew); 
		return this.updateBtn;
	}
	
	public EExpoRowButtonCfg<E> deleteBtn(EExpoRowButtonCfg<E> deleteBtnNew) throws DuplicateNameActionButtonException {
		deleteBtnNew.eexpoCrudCfgId = this.eexpoCrudCfgId;
//		deleteBtnNew.act = ACT.deletePrepare;
		this.deleteBtn = deleteBtnNew; 
//		this.rowBtns.add(deleteBtnNew);
		this.addRowBtns(deleteBtn);
		return this.deleteBtn;
	}
	
}
