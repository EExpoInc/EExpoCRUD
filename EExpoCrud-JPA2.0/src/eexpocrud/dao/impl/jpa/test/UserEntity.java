package eexpocrud.dao.impl.jpa.test;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import eexpocrud.CrudAnnotation.DisplayType;
import eexpocrud.CrudAnnotation.NotShow;

@SuppressWarnings("serial")
@Entity 
public class UserEntity implements Serializable{
	
	public static enum Sexo{
		NA, M, F
	}
	
	
	@Id @GeneratedValue
	public int id;
	  
	public String nome;
	public int anoNasc;
	
	public String end;
	
	@NotShow(DisplayType.list)
	public String bio;
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date dataCadastro;
	
	/**Quando for parte de um ENUM, sera listado o enum e colocado numa combobox*/
	public Sexo sexo; 
	
	public int idDept;
	
}
