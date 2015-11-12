package eexpocrud;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class CrudAnnotation{
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface CrudId {
		
	}
	
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Show {
		DisplayType[] value() default DisplayType.all;
	}
	
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface NotShow {
		DisplayType[] value() default DisplayType.none;
	}
	public static enum DisplayType{all, none, list, create, read, update, delete}
	

	
}

