package eexpocrud.dao.impl.jpa.test;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import eexpocrud.CrudAnnotation.NotShow;

@SuppressWarnings("serial")
@Entity
@Cacheable(false)
public class PostEntity implements Serializable {
	
	public static enum STATUS{
		livre, castigo, emObs, restrito
	}
	
	@Id
	public int id;
	
	public String title;
	
	@NotShow
	String body;
	
	public STATUS status;

	@Temporal(TemporalType.TIME)
	Date dateCreate;
	
	public String authorEmail;
	
}
