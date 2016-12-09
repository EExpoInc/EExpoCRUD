package eexpocrud.action;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import eexpocrud.CrudAnnotation.DisplayType;
import eexpocrud.CrudfyUtils;
import eexpocrud.cfg.ConditionalRowColorI;
import eexpocrud.cfg.EExpoRowButtonCfg;
import eexpocrud.cfg.EExpoButtonCfg.BootstrapDefaultColor;

public class ListActionData<E extends Object, ID extends Comparable<ID>> {
	public ArrayList<Field> fields = new ArrayList<Field>();
	public Map<ID, CrudRowObj<E, ID>> id_objMap = new TreeMap<ID, CrudRowObj<E,ID>>();
	public Map<ID, E> id_entityMap = new TreeMap<>();
	public Class<E> entityClass;
	
	public boolean hitBegin = false;
	public boolean hitEnd = false;
	
	
	
	public static class CrudRowObj <E, ID>{
		public  ID id;
		public E entity;
		public HashMap<String, String> field_ValueMap = new LinkedHashMap<String, String>();
		

		
		ConditionalRowColorI conditionalRowColor;
		BootstrapDefaultColor rowColor;

		
		public String idEscaped(){
			
			return CrudfyUtils.encodeToURL(this.id);
		}
		
		
		public CrudRowObj <E, ID> color(BootstrapDefaultColor color) {
			this.rowColor = color;
			return this;
		}

		public CrudRowObj <E, ID> color(ConditionalRowColorI cond) {
			this.conditionalRowColor = cond;
			return this;
		}
		
		public BootstrapDefaultColor color(){
			if(this.conditionalRowColor != null){
				return this.conditionalRowColor.execute();
			}else{
				return this.rowColor;
			}
		}

		
//		public ID id(){
//			return this.id;
////			return HtmlEncoder.text(this.id+"");
//		}
	}
	
	public ListActionData(List<E> list, Class<E> entityClass) {
		this.entityClass = entityClass;
		filterFields();
		populateRow(list);
		
	}
	
	private void populateRow(List<E> list) {
//		CrudfyTagHelper<?, String> tagHelper2 = new CrudfyTagHelper<String,String>(null, null, null);
		for (E e : list) {
			CrudRowObj<E,ID> obj = createCrudObj(e);
			this.id_objMap.put((ID) obj.id, obj);
			this.id_entityMap.put(obj.id, e);
		}
		
	}
	
	private void filterFields() {
		
		for (Field field : entityClass.getDeclaredFields()) {
			field.setAccessible(true);
			if (CrudfyUtils.showable(field, DisplayType.list)) {
				fields.add(field);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private CrudRowObj<E,ID> createCrudObj(E entity) {
		CrudRowObj<E,ID> result = new CrudRowObj<E,ID>();
		result.entity = entity;
		for (Field field : fields) {
			try {				
				if (field.get(entity) != null) {
					if(field.getType().equals(Date.class)){
						Date d = (Date) field.get(entity);						
						result.field_ValueMap.put(field.getName(), CrudfyUtils.universalFullDateFormat.format(d));
					}else{
						result.field_ValueMap.put(field.getName(), field.get(entity).toString());	
					}
					
				} else {
					result.field_ValueMap.put(field.getName(), null);
				}
				
				if (CrudfyUtils.isIdField(field)) {
					result.id = (ID) field.get(entity);
				}
				
			} catch (IllegalArgumentException | IllegalAccessException | NullPointerException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	public String getTopIdEscaped(){
		return CrudfyUtils.encodeToURL(this.id_objMap.keySet().toArray()[0]+"");
	}
	@SuppressWarnings("unchecked")
	public ID getTopId(){		
		return (ID) this.id_objMap.keySet().toArray()[0];
	}

	
	public String getBottomIdEscaped(){
		return CrudfyUtils.encodeToURL(this.id_objMap.keySet().toArray()[this.id_objMap.size()-1]+"");
	}
	@SuppressWarnings("unchecked")
	public ID getBottomId(){
		return (ID)this.id_objMap.keySet().toArray()[this.id_objMap.size()-1];
	}

	
//	public static void main(String[] args) {
//		
//		ListActionData<FilterImgUrlEntity, Long> lad = new ListActionData<>(AdmFiltersAction.list(),
//				FilterImgUrlEntity.class);
//		Set<Long> ks = lad.id_objMap.keySet();
//		
//		for(Field f: lad.fields){
//			System.out.print(f.getName() + " - ");
//		}
////		System.out.println("");
//		
//		for (Long k : ks) {
//			System.out.println(k + " - "+ lad.id_objMap.get(k).field_ValueMap.values());
//		}
//		
//	}
	
}
