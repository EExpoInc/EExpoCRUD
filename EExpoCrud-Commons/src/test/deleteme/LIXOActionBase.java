package test.deleteme;

import test.deleteme.fw.ActionBase;

public abstract class LIXOActionBase<R>  extends ActionBase{
	
	public static class DataLinkBase {
		public String id;
		
		public DataLinkBase idInt(int id) {
			this.id = id + "";
			return this;
		}
		
		public DataLinkBase idLong(long id) {
			this.id = id + "";
			return this;
		}
		
		public int idInt() {
			return Integer.parseInt(id);
		}
		
		public Long idLong() {
			return Long.parseLong(id);
		}
			
	}
	
	
//	abstract R execute();
	
	
	
	
	
}
