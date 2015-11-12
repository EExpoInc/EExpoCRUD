package test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;



public class AdmFiltersAction {
	public static Map<Long, FilterImgUrlEntity> dao = new TreeMap<>();

	public static List<FilterImgUrlEntity> list() {

			
			Random r = new Random(System.currentTimeMillis());
			for(int i=0; i<168; i++){
				FilterImgUrlEntity f = new FilterImgUrlEntity();
//				f.id = System.currentTimeMillis()+i;
				f.id = (long) r.nextInt(100000);				
				f.filter = "filter:::"+f.id; 
//				f.filter += "as s afa sdf a sf asd fasfd asf asfdq  e rqer filter:::"+f.id;
//				f.filter += "as s afa sdf a sf asd fasfd asf asfdq  e rqer filter:::"+f.id;
//				f.filter += "as s afa sdf a sf asd fasfd asf asfdq  e rqer filter:::"+f.id;
//				f.filter = "filter ::: "+r.nextInt(1000); 
				f.nome = "nome:::"+ i ;
				f.remove = r.nextBoolean();
				f.replace = "repl:::"+f.id;
				f.id = 0L+i;
				f.lista = new ArrayList<String>();
				f.lista.add("item 1");
				f.lista.add("item 2");
				
				dao.put(f.id, f);				
			}
		
		
//		print(dao.values());
		
		ArrayList<FilterImgUrlEntity> toSort =  (new ArrayList<>(dao.values()));
		Collections.sort(toSort, new Comparator<FilterImgUrlEntity>() {
			@Override
			public int compare(FilterImgUrlEntity o1, FilterImgUrlEntity o2) {
				if(o1.id > o2.id){
					return 1;
				}else if(o1.id < o2.id){
					return -1;
				}else{
					return 0;			
				}		
			}
		});
		
		
		return toSort ;

	}

	private static void print(Collection<FilterImgUrlEntity> values) {

		for (FilterImgUrlEntity e : values) {
			System.out.println(e.nome);
		}
	}

	public static void create(HttpServletRequest req) {
		FilterImgUrlEntity f = UtilsBean.populate2(FilterImgUrlEntity.class, req.getParameterMap());
//		FilterImgUrlEntity f = UtilsBean.populate(FilterImgUrlEntity.class, req.getParameterMap());
//		FilterImgUrlEntity f = UtilsBean.populate2(new FilterImgUrlEntity(), req.getParameterMap());
		f.id = System.currentTimeMillis();
		dao.put(f.id, f);
		
	}

	public static FilterImgUrlEntity read(Long id) {
		return dao.get(id);
	}

	public static void update(HttpServletRequest req) {
		FilterImgUrlEntity f = UtilsBean.populate2(FilterImgUrlEntity.class, req.getParameterMap());
		dao.put(f.id, f);
	}

	public static void delete(Long id) {
		dao.remove(id);
	}
	
	

}
