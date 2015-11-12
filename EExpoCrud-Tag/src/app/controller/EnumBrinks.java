package app.controller;

public class EnumBrinks {
 
	
	public  interface fieldI{
		public String value = null;
		public String label= null;
		public String err= null;
	}
	
	
	enum form implements fieldI{
		field1("v1"), field2("v2"), field3("v3");
		
		public String value;
		
		private form(String v){
			this.value = v;
		}
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(form.field2.value);
		System.out.println(form.field3.value);
	}
	
	
	
}
