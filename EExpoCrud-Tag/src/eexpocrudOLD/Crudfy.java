package eexpocrudOLD;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import eexpocrud.CrudAnnotation.DisplayType;
import eexpocrud.CrudfyUtils;

/***
 * @author fulvius
 * 
 * EExpoCrud: componente visual p criar views de crud p pojos, com suporte a acesso a DB e custom view
 * 	com foco na tipagem forte; 
 * 
 * - uso de annotation p customizar format e mask
 * - display automatico dos dados tipados (Date, Double, Currency)
 * - delegacao de view customizada 
 * - DAO customizados 
 * - clique na linha p detalhar o registro.
 * - suporte a formBean customizados amarrados via annotation (se aparece ou n)  
 * - suporte a multiplos cruds numa pag (v2)
 * - apresentacao dos sub obj em forma de arvore. ex.: compra-item (v2)
 * - urls action seguras com timestamp-assign (p evitar manipulacao e mexer registros n listados) (v2)
 * - criacao de links avulsos p printar em qualquer parte do proj (v2) 
 * 		(ex.: Crudfy.bean(LivroEntity.class).prepareCreate().link();)
 * 		(ex.: Crudfy.cfg(LivroEntityCfg).prepareCreate().link();)
 * - filtros de pesquisa (v2) (id > 3; nome > joao;  data< 2015-05-01)
 * - extensivel
 * - metodos dos controllers q retornam HrefLink;
 * - nav com suporte a listas q n tem total (ex.: resultado de filtros)
 * 
 * - como resolver o problema da nav em listas grandes, como das NewsPub?
 * uso no GAE, melhor combinar cursor e offset p nav, tendo em vista q o uso exclusivo de offset traz junto
 * tds as chaves dos entities de qualquer forma. Ou seja, usa o cursor p marcar qual reg esta e os offsets p 
 * pular, mas atualizando o cursor.
 * 
 * ciclo de vida da listagem: 
 * 1- recebe a req
 * 		param nav? se sim, faz paginacao
 * 		param id? se sim, tenta pegar da sessao. 
 * 			senao, tenta achar instancia pelo url+class
 * 		
 * 	
 * 
 */


public class Crudfy <E, K>{

	
	protected Class<E> beanClazz;
//	private int qtdeDisplay=10, pageAtual=1;
//	private Object cursorAtual;
	
	private ArrayList<Field> fields;
	private List<?> listToShow;
	private HashMap<String, CrudObj> id_RowMap;
	private DaoEntityAbs<E, K> daoManager;
	public String id;
	
	 
	
	
	
	public static class CrudObj {
		public String id; 
		public HashMap<String, String> field_ValueMap = new HashMap<>();		
	}
	
	public static class CrudTableToShow{
		public ArrayList<String> thList = new ArrayList<>();
		public Map<String, CrudObj> id_objMap = new TreeMap<>();
		public List<CrudObj> objListSorted;
		public CrudfyCfg nav;		
		public long totalRecords; 

		
		
		private void sort(){
			objListSorted = new ArrayList<>();
			objListSorted.addAll(id_objMap.values()); 
			Collections.sort(objListSorted, new Comparator<CrudObj>() {
				@Override
				public int compare(CrudObj o1, CrudObj o2) {
					return o1.id.compareTo(o2.id);

				}
			});
//			
//			ArrayList<String> toSort = new ArrayList<>();
//			toSort.addAll(this.id_objMap.keySet());
//			Collections.sort(toSort);
		}
		
		public String getIdFirstItemPage(){ return this.objListSorted.get(0).id;}
		public String getIdLastItemPage(){ return this.objListSorted.get(this.objListSorted.size()-1).id;}
		
	}
	
	
	
	
	

	
	public static <B,K>  Crudfy<B,K> beanClass(Class<B> clazz){
		Crudfy<B,K> crudfy = new Crudfy<>();
		crudfy.beanClazz = clazz;
		crudfy.identifyFields();
		
		return crudfy;
	}
	


	private void identifyFields() {
		this.fields = new ArrayList<>();
		Field[] _fields = this.beanClazz.getDeclaredFields();
		for (Field field : _fields) {
			this.fields.add(field);
			field.setAccessible(true);
		}
		
	}
	
	
	public Crudfy<E,K>  listToShow(List<E> listToShow){
		 this.listToShow = listToShow;
		return this; 
	}
	
	
	
	public Crudfy<E,K>  id(String id){
		this.id = id;
		return this; 
	}
	
	public Crudfy<E,K> daoManager(DaoEntityAbs<E, K> daoManager2){
		this.daoManager = daoManager2;		
		return this;
	}
	
	
	public CrudTableToShow initialList(){
		listToShow(daoManager.initialList());
		return getGenericObjMap();
	}
	
	
	public CrudTableToShow nav(int page) {
		long initialPos = daoManager.qtdeDisplay*page - daoManager.qtdeDisplay;
		initialPos = initialPos < 0 ? 0 : initialPos;
		long finalPos = daoManager.qtdeDisplay*page;
		finalPos = finalPos > daoManager.total() ? daoManager.total() -1 : finalPos;
		
//		listToShow(daoManager.nav(page, this.qtdeDisplay));
		listToShow(daoManager.nav(initialPos, finalPos));
		daoManager.pageAtual = page;
		return getGenericObjMap();
	}
	
	
	

	public CrudTableToShow getGenericObjMap(){
		
		
		CrudTableToShow table = new CrudTableToShow();
		prepareId_RowMap();
		table.id_objMap = this.id_RowMap;
		table.thList = genThList();
		table.totalRecords = daoManager.total();
		if(this.daoManager.estimated){
			table.nav = genCrudNavEstimated();
		}else{
			table.nav = genCrudNav();	
		}
		
		table.sort();
		
		
//		table.navActualPage = 0;
//		table.navInitPage=0;
//		table.navEndPage=8;
		
		return table;
	}
	
//	private CrudfyCfg genCrudNav() {
//	private CrudfyCfg genCrudNav2() {
//		CrudfyCfg result = daoManager.CrudfyCfg;
//	}
	
	
	private CrudfyCfg genCrudNavEstimated() {
		CrudfyCfg result = daoManager.crudfyCfg;
		result.isEstimated = daoManager.estimated;
		result.firstComposition = false;

		return result;
	}
	private CrudfyCfg genCrudNav() {
		CrudfyCfg result = daoManager.crudfyCfg;
		long total = daoManager.total();
		result.qtdePagesTotal = (int) (total/daoManager.qtdeDisplay);
		result.btnActive = daoManager.pageAtual;
//		result.btnActive = daoManager.CrudfyCfg.actualPage;
		if(total < daoManager.qtdeDisplay){
			result.isNeeded = false;
		}else{
			result.isNeeded = true;
			
			if(result.qtdeBtnDisplay > result.qtdePagesTotal){
				result.qtdeBtnDisplay  = result.qtdePagesTotal;
				result.firstComposition = true;	
				result.lastComposition = true;
				result.posBtnInit=1;
				result.posBtnEnd=result.qtdeBtnDisplay+1;
				
			}else{
				if(result.btnActive <= (result.qtdeBtnDisplay/2)){ // esta na composicao inicio?				
					result.firstComposition = true;	
					result.posBtnInit=1;
					result.posBtnEnd=result.qtdeBtnDisplay;
				}else if((result.qtdePagesTotal - (result.qtdeBtnDisplay/2)) <= (result.btnActive)){ // esta na composicao final?
					result.lastComposition = true;
					result.posBtnEnd=result.qtdePagesTotal+1;
					result.posBtnInit = result.qtdePagesTotal - result.qtdeBtnDisplay+2;
				}else{
					boolean isEven = (result.qtdeBtnDisplay%2) == 0 ? true : false; 
					result.posBtnInit = result.btnActive - (result.qtdeBtnDisplay/2);
					result.posBtnEnd = result.btnActive + (result.qtdeBtnDisplay/2) + (isEven?-1:0);
					result.posBtnInit = result.posBtnInit == 0? 1: result.posBtnInit;
				}
			}
		}
		if(result.posBtnInit == 1){
			result.firstComposition = true;
		}
		if(result.posBtnEnd == result.qtdePagesTotal){
			result.lastComposition = true;
		}
		return result;
	}



	public void show(){
		prepareId_RowMap();
		printSysout();
	}
	
	private ArrayList<String> genThList(){
		ArrayList<String> result = new ArrayList<>();		
		for(Field field : fields){
			if(showIntoList(field)){
				result.add(field.getName());
			}			
		}
		return result;
	}
	
	public static boolean showIntoList(Field field){
		return CrudfyUtils.showable(field, DisplayType.list);
	}
	


	private void printSysout() { 
		String out = "";
		
		out += "id" + "\t";
		ArrayList<String> _th = genThList();
		for(String th : _th){
			out+=  "<"+ th + "> ";			
		}

		
		for(CrudObj co: id_RowMap.values()){
			out += "\n";
			out += co.id + "\t";
			
			for(Field field : fields){
				out+=  "<"+ co.field_ValueMap.get(field.getName()) + "> ";			
			}

//			for(String cov : co.field_Value.values()){
//				out+=  "<"+ cov + "> ";
//			}
			
		}
		System.out.println(out);
	}


	
	
	private void prepareId_RowMap() {
		this.id_RowMap = new HashMap<>();
		
		DecimalFormat myFormatter = new DecimalFormat("00000000000");
		if(this.listToShow != null){
			int _id = 0;
			for(Object obj : this.listToShow){
				CrudObj co = createCrudObj(obj);
				if(co.id == null){
					co.id =  myFormatter.format(_id++);
				}
				this.id_RowMap.put(co.id, co);				
			}
		}		
	}
	
	
	
	private  CrudObj createCrudObj(Object obj){
		CrudObj result  = new CrudObj(); 
		for(Field field : fields){
			try {
				
				if(field.get(obj) != null){
//					result.field_Value.put(field.getName(), field.get(obj).toString());	
					result.field_ValueMap.put(field.getName(), diplayField(obj, field));	
				}else{
					result.field_ValueMap.put(field.getName(), null);
				}
				
				if(CrudfyUtils.isIdField(field)){ 
					result.id = displayField(obj, field, true);
				} 
				
			} catch (IllegalArgumentException | IllegalAccessException | NullPointerException e) {
				e.printStackTrace();
			}			
		}
		return result;
	}

	



	private static String diplayField(Object obj, Field field) throws IllegalArgumentException, IllegalAccessException{
		return displayField(obj, field, false);
	}
	
	
	private static String displayField(Object obj, Field field, boolean isID) throws IllegalArgumentException, IllegalAccessException{
		
		if(field.getType().equals(Long.class) || field.getClass().equals(int.class)){
			DecimalFormat idFormatter = new DecimalFormat("000"+"000"+"000"+"000"+"000"+"000" );
			return  isID ? idFormatter.format(field.get(obj)): field.get(obj) + "";
		}else{
			return field.get(obj).toString();
		}
	}



	@SuppressWarnings("unchecked")
	public CrudTableToShow listAfter(Long idAfter) {
		listToShow(this.daoManager.listAfter((K) idAfter));
		return this.getGenericObjMap();
		
	}



	@SuppressWarnings("unchecked")
	public CrudTableToShow listBefore(Long idBefore) {
		listToShow(this.daoManager.listBefore((K) idBefore));
		return this.getGenericObjMap();
	}
	
	
	
	
}
