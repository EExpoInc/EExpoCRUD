package eexpocrud.dao.impl.jpa.test;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Cacheable(false)
public class PageEntity implements Serializable{
	
	@Id
	String url;
	
	String title;
	
	String bodyContent;
	
	@Temporal(TemporalType.DATE)
	public Date dataCadastro;
}
