package test;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import eexpocrudOLD.DaoEntityAbs;

public class FilterImgUrlEntityDAOImpl extends DaoEntityAbs<FilterImgUrlEntity, Long> {
	private List<FilterImgUrlEntity> memList = AdmFiltersAction.list();
	// private int total = memList.size();
	
	private TreeMap<Long, FilterImgUrlEntity> memListMap = new TreeMap<>();
	private List<Long> memKeys = new ArrayList<>();
	
	public FilterImgUrlEntityDAOImpl() {
		super(false);		
		this.qtdeDisplay = 10;
		this.estimated = true;
		this.crudfyCfg.qtdeBtnDisplay = 10;
		
		for (FilterImgUrlEntity e : memList) {
			memListMap.put(e.id, e);
			memKeys.add(e.id);
		}
	}
	
	@Override
	public List<FilterImgUrlEntity> initialList() {
		return memList.subList(0, this.qtdeDisplay);
	}
	

	@Override
	public List<FilterImgUrlEntity> list(Long startId, int jump) {
		//memListMap.
		int idx = memKeys.indexOf(startId);
		return memList.subList(idx+1, idx+1+this.qtdeDisplay);
		
//		return null;
	}

	
	@Override
	public List<FilterImgUrlEntity> nav(long initialPos, long finalPos) {
		return memList.subList((int) initialPos, (int) finalPos);
		
	}
	
	
	
//	@Override
//	public List<FilterImgUrlEntity> list(Long startId, int size) {
//	}
	
	@Override
	public void create(FilterImgUrlEntity b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FilterImgUrlEntity read(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(FilterImgUrlEntity b) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public long total() {
//		return this.memListMap.size();
		return 0;
	}

	@Override
	public List<FilterImgUrlEntity> listAfter(Long idAfter) {
		if(this.memKeys.indexOf(idAfter+1) > -1){
			int idx = this.memKeys.indexOf(idAfter) +1;
			if(idx+qtdeDisplay > this.memKeys.size()){
				return this.memList.subList(idx, this.memKeys.size());
			}else{
				return this.memList.subList(idx, idx+qtdeDisplay);	
			}			
		}else{
			return this.memList.subList(this.memKeys.size() - qtdeDisplay, this.memKeys.size());
		}
		
	}

	@Override
	public List<FilterImgUrlEntity> listBefore(Long idBefore) {
		int idx = this.memKeys.indexOf(idBefore) -1 ;
		int begin = idx - qtdeDisplay <= 0 ? 0 :idx - qtdeDisplay;
		if(begin == 0 ){
			return this.listAfter(-1L);
		}else{
			return this.memList.subList(begin, idx);	
		}
	}

	
}
