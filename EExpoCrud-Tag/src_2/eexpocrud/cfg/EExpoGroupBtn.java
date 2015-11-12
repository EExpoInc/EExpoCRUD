package eexpocrud.cfg;

import java.io.Serializable;
import java.util.ArrayList;

import eexpocrud.cfg.EExpoButtonCfg;
import eexpocrud.cfg.EExpoRowButtonCfg;

@SuppressWarnings("serial")
public class EExpoGroupBtn<E> implements Serializable{
	EExpoButtonCfg<E> createBtn;
	EExpoRowButtonCfg<E> readBtn, updateBtn, deleteBtn;
	public ArrayList<EExpoButtonCfg<E>> addtionalBtns = new ArrayList<>();
	public ArrayList<EExpoButtonCfg<E>> headerBtns = new ArrayList<>();
	public ArrayList<EExpoRowButtonCfg<E>> rowBtns = new ArrayList<>();
	EExpoButtonCfg<E> lineBtn; 
	E rowEntity;
	
	public EExpoGroupBtn() {
//		this.rowEntity = rowEntity;
	}
	
//	public EExpoGroupBtn<E> add(EExpoButtonCfg<E> btn) {
//		addtionalBtns.add(btn);
//		return this;
//	}
	
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
		this.createBtn = createBtnNew;
		this.headerBtns.add(createBtnNew);
		return this.createBtn;
	}
	
	public EExpoRowButtonCfg<E> readBtn(EExpoRowButtonCfg<E> readBtnNew) {
		this.readBtn = readBtnNew;
		this.lineBtn = readBtnNew;
		return this.readBtn;
	}
	
	public EExpoRowButtonCfg<E> updateBtn(EExpoRowButtonCfg<E> updateBtnNew) {
		this.updateBtn = updateBtnNew;
		this.rowBtns.add(updateBtnNew);
		return this.updateBtn;
	}
	
	
	public EExpoRowButtonCfg<E> deleteBtn(EExpoRowButtonCfg<E> deleteBtnNew) {
		this.deleteBtn = deleteBtnNew;
		this.rowBtns.add(deleteBtnNew);
		return this.deleteBtn;
	}
	
}
