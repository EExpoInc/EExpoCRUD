package test;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import eexpocrud.CrudAnnotation.CrudId;
import eexpocrud.CrudAnnotation.DisplayType;
import eexpocrud.CrudAnnotation.NotShow;
import eexpocrud.CrudAnnotation.Show;



public class FilterImgUrlEntity {
	
	@CrudId
	public Long id;

	@Show({DisplayType.none, DisplayType.update})
	public List<String> lista; 

	public String nome;
	public String filter;
	
	@NotShow({DisplayType.list})
	public boolean remove;
	
	public String replace;
	public int qtde;
	
	@NotShow(DisplayType.list)
	public Date data;
	
	//@NotShow({DisplayType.list})
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public void setRemove(boolean remove) {
		this.remove = remove;
	}
	public void setReplace(String replace) {
		this.replace = replace;
	}
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
		
//		return super.toString();
	}
	
	
}
