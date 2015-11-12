package mvcDSV;

public abstract class NewsController {
	
	public static final String CREATE_ACTION = 	"CREATE_ACTION";
	public static final String READ_ACTION = 	"READ_ACTION";
	public static final String UPDATE_ACTION = 	"UPDATE_ACTION";
	public static final String DELETE_ACTION = 	"DELETE_ACTION";
	
	
	public static void meuMetodo(){
		
	}
	
	public static class Create{
		public NewsEntity execute(NewsEntity ne){			
			return ne;
		}
		
	}
	
	public static  class Update{
		public NewsEntity execute(NewsEntity ne){
			return ne;
		}		
	}

	public static class Read{
		public NewsEntity execute(long id){
			return null;
		}

	}

	public static class Delete{
		public NewsEntity execute(long id){
			return null;
		}		
	}

}
