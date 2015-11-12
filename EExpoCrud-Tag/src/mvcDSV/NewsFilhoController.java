package mvcDSV;

public class NewsFilhoController extends NewsController{
	public void list(){
		NewsFilhoController nfc = new NewsFilhoController();
		
	}
	
	public static enum act{CREATE, READ, UPDATE, DELETE}
	
	public static enum param{
//		teste(""){
//			
//		},
		act(){
			
		}
	}
	
	public static void main(String[] args) {
		System.out.println(act.CREATE);
		
	}
}
