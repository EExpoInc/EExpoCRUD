package test.deleteme.mvc;

import org.javatuples.Triplet;

import test.deleteme.MyBeautyBean;
import test.deleteme.bo.BlogPostBO;
import test.deleteme.db.BlogPostEntity;
import test.deleteme.fw.ActionBase;
import test.deleteme.fw.IoAction;
import test.deleteme.fw.ResultAction;

public class BlogPostController {
	/*******
	 * O action tem:
	 * 
	 * o metodo final link(): se passar um entity, sera criado um link com o id; se passar um data/pojo, sera transformado em
	 * parametros na hora de criar a Action pela primeira vez, devera ser mapeada a url de chamada ao canonical name da Action no
	 * application sera invocado p criar o link na pag de origem
	 * 
	 * o metodo abstrato execute(): se criar uma action passando uma entidade, ela sera recuperada no DB por id se criar usando um
	 * pojo, sera remontado p passar no execute da action retorna um obj q sera recuperado na pag convencionada atraves do
	 * result(request); sera invocado p chamar o obj de negocio, e despacha o resultado p pag de destino
	 * 
	 * e o metodo static result(request): pega da request o retorno tipado da Action na pag de destino
	 * 
	 * req e resp p trabalhar com o HTTP
	 * 
	 * 
	 * ComplexActionBase{ // para usar com formularios prepare(CommandBase cb) confirm(CommandBase cb) cancel(CommandBase cb) }
	 * 
	 * CommandBase{ execute(){ // chama alguma logica de negocio // ja vai ter um Entity carregado aqui } }
	 * 
	 * 
	 */
	
	public ActionBase createPreAction() {
		return new ActionBase() {
			@Override
			protected void executeBase() {
				BlogPostBO bo = new BlogPostBO();
				 bo.createDraft();
			}
			
		};
		
	}
	
	public ResultAction<BlogPostEntity> preEditAction() {
		return new ResultAction<BlogPostEntity>() {
			@Override
			protected BlogPostEntity execute() {
				BlogPostBO bo = new BlogPostBO();
				return bo.createDraft();
			}
		};
	}
	
	public ResultAction<BlogPostEntity> createAction_(final BlogPostEntity pe) {
		return new ResultAction<BlogPostEntity>() {
			@Override
			protected BlogPostEntity execute() {
				
				return pe;
			}
		};
	}
	
	public IoAction<BlogPostEntity, Triplet<BlogPostEntity, Integer, String>> publishAction(
			final BlogPostEntity pe) {
		return new IoAction<BlogPostEntity, Triplet<BlogPostEntity, Integer, String>>(pe) {
			@Override
			protected Triplet<BlogPostEntity, Integer, String> execute(BlogPostEntity pe) {
				BlogPostBO bo = new BlogPostBO();
				bo.publish(pe);				
				return new Triplet<BlogPostEntity, Integer, String>(null, bo.total(), "valor tres");
			}
		};
	}
	
	
	
	/**
	 * 
	 * BlogPostController.prepareEditAction(blogPostEntity).link(this); >> gera um link: ./BlogPost.prepareEdit/123
	 * BlogPostController.confirmEditAction(blogPostEntity).link(this); >> gera um link: ./BlogPost.confirmEdit/123
	 *     e vai receber um Bean populado com os dados do POST atraves do act.populateBean(MyBeautyBean.class);
	 * 
	 */
	
	public static class MyCustomAction extends IoAction<BlogPostEntity, Boolean> {
		
		public MyCustomAction(BlogPostEntity obj) {
			super(obj);
		}
		
		@Override
		protected Boolean execute(BlogPostEntity obj) {
			return false;
		}
	}
	
	public IoAction<BlogPostEntity, Boolean> createAction() {
		
		return new MyCustomAction(new BlogPostEntity());
	}
	
	private void deleteme(){
		MyBeautyBean mbb =  createAction().populateBean(MyBeautyBean.class);
		System.out.println(mbb);
		
		
	}
	
	
}
