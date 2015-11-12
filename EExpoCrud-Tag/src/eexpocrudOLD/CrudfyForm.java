package eexpocrudOLD;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import test.FilterImgUrlEntity;
import eexpocrud.CrudAnnotation.DisplayType;
import eexpocrud.CrudfyUtils;

public class CrudfyForm {
	
	public static class FieldForm {
		Class<?> supportedClass;
		String label;
		Object value;
	}
	
	@SuppressWarnings("serial")
	public static class ShowFieldException extends Exception {
		Object entity;
		Field f;
		
		public ShowFieldException(Exception e, Field f, Object entity) {
			super(e);
			this.f = f;
			this.entity = entity;
		}
	}
	
	public List<FieldForm> fieldList = new ArrayList<>();
	
	public CrudfyForm(Object entity) throws ShowFieldException {
		Field[] fields = entity.getClass().getFields();
		for (Field f : fields) {
			try {
				if (CrudfyUtils.showable(f, DisplayType.update) && CrudfyUtils.isSupported(f.getType())) {
					FieldForm ff = new FieldForm();
					ff.supportedClass = f.getClass();
					ff.label = f.getName();
					
					ff.value = f.get(entity); 
					this.fieldList.add(ff);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new ShowFieldException(e, f, entity);
			}
			
		}
	}
	
	public static void main(String[] args) throws ShowFieldException {
		Random r = new Random();
		int i = r.nextInt();
		FilterImgUrlEntity f = new FilterImgUrlEntity();
		// f.id = System.currentTimeMillis()+i;
		f.id = r.nextLong();
		f.qtde = r.nextInt();
		f.filter = "filter:::" + f.id;
		f.filter += "as s afa sdf a sf asd fasfd asf asfdq  e rqer filter:::" + f.id;
		// f.filter = "filter ::: "+r.nextInt(1000);
		f.nome = "nome:::" + i;
		f.remove = r.nextBoolean();
		f.replace = "repl:::" + f.id;
		// f.id = 0L+i;
		f.lista = new ArrayList<String>();
		f.lista.add("item 1");
		f.lista.add("item 2");
		
		CrudfyForm cf = new CrudfyForm(f);
		
		print(cf);
		
	}
	
	private static void print(CrudfyForm cf) {
		for (FieldForm ff : cf.fieldList) {
			System.out.println(ff.label + ":" + ff.value);
		}
		
	}
	
}
