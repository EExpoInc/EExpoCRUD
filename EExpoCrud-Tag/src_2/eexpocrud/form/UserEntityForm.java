package eexpocrud.form;

import java.util.Map;
import java.util.TreeMap;

import eexpocrud.CrudfyUtils;
import eexpocrud.dao.impl.jpa.test.UserEntity;


/**
 * EntityForm eh usado p adicionar informacoes no momento que mostrar o bean, 
 *	principalmente no create e update, tendo em vista ser necessario as vzs mostrar 
 *	o conjunto completo dos dados disponiveis p dps escolher um subconjunto. <p>
 *	
 *	Deve estender a entidade e deve criar metodos com nomes das variaveis p pegar os valores totais. <p>
 * 
 *  Caso exista metodo com o msm nome da variavel e com retorno unico (n sendo uma lista ou mapa), 
 *  o metodo sera chamado prioritariamente p mostrar o valor. Isso pode acontecer caso se queira 
 *  fazer alguma formatacao muito especifica, ou buscar algum dado complementar p ser mostrado.  
 *
 * @author fulvius
 * @see   <a href="http://eexponews.com">http://eexponews.com</a>
 *   
 */
public class UserEntityForm extends UserEntity{
	
	public String hidden = "some value to hide";
	
	
	//para definir o universo dos elementos a serem mostrados
	public int[] anoNasc() {
		return new int[] { 80, 90, 100 };
	}
	
	
	
	//sera usado p pegar tds os valores caso o campo seja um subconj de um conj determinado
	public Map<Integer, String> idDept(){
		Map<Integer, String> result = new TreeMap<>();
		//get same data from DB to populate a comboBox
		result.put(1, "Account");
		result.put(2, "IT S2");
		result.put(3, "Sales");
		result.put(4, "Support");
		result.put(5, "Other Labelz");
		return result;
	}
	
	
	
	public static void main(String[] args) {
		UserEntity u1 = new UserEntityForm(); 
		UserEntity u2 = new UserEntity();
		u2.nome = "oba! beanTransfusion!";
		
		UserEntityForm uf = new UserEntityForm(); 
		CrudfyUtils.beanTransfusion(uf, u2);
		System.out.println(uf.nome);
		
	}
	

}
