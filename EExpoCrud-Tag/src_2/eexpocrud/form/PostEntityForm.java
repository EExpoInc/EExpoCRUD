package eexpocrud.form;

import eexpocrud.CrudfyUtils;
import eexpocrud.dao.impl.jpa.test.PostEntity;

public class PostEntityForm extends PostEntity {
	
	public String hidden;
	
	
	//para definir o universo dos elementos a serem mostrados
	public int[] status() {
		return new int[] { 1, 2, 3 };
	}
	
	public static void main(String[] args) {
		PostEntity pe1 = new PostEntityForm(); 
		PostEntity pe2 = new PostEntity();
		pe2.title = "oba! teletransporte!";
		
		PostEntityForm pef = new PostEntityForm(); 
		CrudfyUtils.beanTransfusion(pef, pe2);
		System.out.println(pef.title);
		
	}
	

}
