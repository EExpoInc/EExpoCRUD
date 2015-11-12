package test.deleteme.mvc;

import org.javatuples.Triplet;

import test.deleteme.bo.BlogPostBO;
import test.deleteme.db.BlogPostEntity;
import test.deleteme.fw.ActionBase;
import test.deleteme.fw.NoInputAction;
import test.deleteme.fw.SimpleAction;

public class BlogPostController {
	/*******
	 * O action tem:
	 * 
	 * o metodo final link(): 
	 * 		se passar um entity, sera criado um link com o id; 
	 * 		se passar um data/pojo, sera transformado em parametros na hora de criar a Action pela primeira vez, devera ser mapeada a url de chamada ao canonical name da Action no
	 * 			application
	 * 		sera invocado p criar o link na pag de origem
	 * 
	 * o metodo abstrato execute(): 
	 * 		se criar uma action passando uma entidade, ela sera recuperada no DB por id 
	 * 		se criar usando um pojo, sera remontado p passar no execute da action retorna um obj q sera recuperado na pag convencionada atraves do
	 * 			result(request);
	 * 		sera invocado p chamar o obj de negocio, e despacha o resultado p pag de destino
	 * 
	 * e o metodo static result(request):
	 * 		pega da request o retorno tipado da Action na pag de destino
	 * 
	 * req e resp p trabalhar com o HTTP
	 * 
	 * 
	 */
	
	public ActionBase createPreAction() {
		return new ActionBase() {
			@Override
			protected void execute() {
				BlogPostBO bo = new BlogPostBO();
				
//				bo.createDraft(new BlogPostEntity());
			}
		};
		
	}

	public NoInputAction<BlogPostEntity> createPreAction(final BlogPostEntity pe) {
		return new NoInputAction<BlogPostEntity>() {
			@Override
			protected BlogPostEntity executeNoInput() {
				BlogPostBO bo = new BlogPostBO();
				return bo.createDraft(); 
			}
		};
	}

	
	public NoInputAction<BlogPostEntity> createAction_(final BlogPostEntity pe) {
		return new NoInputAction<BlogPostEntity>() {
			@Override
			protected BlogPostEntity executeNoInput() {

				return pe; 
			}
		};
	}
	
	public SimpleAction<BlogPostEntity, Triplet<BlogPostEntity, Integer, String>> publishAction(final BlogPostEntity pe) {
		return new SimpleAction<BlogPostEntity, Triplet<BlogPostEntity, Integer, String>>(pe) {
			@Override
			protected Triplet<BlogPostEntity, Integer, String> execute(BlogPostEntity pe) {
				BlogPostBO bo = new BlogPostBO();
				bo.publish(pe);
				return new Triplet<BlogPostEntity, Integer, String>(null, bo.total() , "valor tres");
			}
		};
	}
	
	
	public static class MyCustomAction extends SimpleAction<BlogPostEntity, Boolean>{

		public MyCustomAction(BlogPostEntity obj) {
			super(obj);
		}

		@Override
		protected Boolean execute(BlogPostEntity obj) {
			return false;
		}
		
	} 
	
	
	public SimpleAction<BlogPostEntity, Boolean> createAction() {
		return new MyCustomAction(null);
	}

	
	
	
}
