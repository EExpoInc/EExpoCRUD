package eexpocrud.dao.impl.jpa.test;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Cacheable(false)
public class ChannelEntity implements Serializable{
	
	@EmbeddedId
	ChannelId id;
	
	String title;
	
	String bodyContent;
	
	@Temporal(TemporalType.DATE)
	public Date dataCadastro;
	
	
	@Embeddable
	public static class ChannelId implements Serializable, Comparable<ChannelId>{
		public int id;
		public String sufix;
		@Override
		public int compareTo(ChannelId o) {
			return 0;
		}
	}
}
