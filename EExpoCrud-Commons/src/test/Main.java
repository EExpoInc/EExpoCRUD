package test;

import static j2html.TagCreator.*;

import java.util.List;

import org.javatuples.Triplet;

import test.deleteme.db.BlogPostEntity;
import test.deleteme.fw.SimpleAction;
import test.deleteme.mvc.BlogPostController;

public class Main {
	public static void main2(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		BlogPostController mc = new BlogPostController();
		final BlogPostEntity pe = new BlogPostEntity();
		
		// DataLinkBase dl = new DataLinkBase() {
		// {
		// this.idInt(me.id);
		// }
		// };
		
		/** SE PASSAR UM ENTITY, O FRAMEWORK COLOCARA SO SEU ID NOS PARAMS, e configurado, ira popular o entity atraves de jpa ***/
		System.out.println("Ao ser colocado no jsp: <%=mc.createPreAction().link()%>  ");
		System.out.println("ira gerar >> " + mc.createPreAction().link());
		
		// SimpleAction<BlogPostEntity, Boolean> al = mc.createAction();
		// al.result(null)
		
		SimpleAction<BlogPostEntity, Boolean> al = mc.createAction();
		System.out
				.println("\nDevera ser gravado na aplication o seguinte obj q sera vinculado a url montada");
		System.err.println(al.getClass().getName());
		System.out.println("Qdo chamado, devera chamar o execute com o data preenchido.");
		
		SimpleAction<BlogPostEntity, Triplet<BlogPostEntity, Integer, String>> al2 = mc.publishAction(pe);
		System.out.println("\nDevera ser gravado na aplication o seguinte obj");
		System.err.println(al2.getClass().getName());
		System.out.println("Qdo chamado, devera chamar o execute com o data preenchido.");
		
		// Class<?> actC = Class.forName("test.deleteme.mvc.PostController$2");
		// NoInputAction<PostEntity> act = (NoInputAction<PostEntity>) actC.newInstance();
		// System.out.println(act.link());
		
	}
	
	public static class MyBean {
		String nome;
		int qtde;
		// String end;
		String[] coresPref;
		String uf;
	}
	
	public static class MyBeanForm extends MyBean {
		// ou define um metodo com o msm nome da prop ou usa uma anotation p especificar qual prop ele se refere
		// @DefaultValues("multiples)
		
		int formId;
		
		public Object coresPref() {
			return null;
		}
		
		// @DefaultValues("uf")
		public List<Object> todosEstados() {
			return null;
		}
	}
	
	public static void main3(String[] args) {
		
		MyBean mb = new MyBeanForm() {
			{
				this.formId = 10;
			}
		};
		
		Class<? extends MyBean> c = mb.getClass();
	}
	
	public static void main(String[] args) {
		String s = body().with(h1("Heading!").withClass("example"), img().withSrc("img/hello.png"),
				div().withAlt("myDiv").with(h2("wow"), input().withType("text").withValue("meu valor"))).render();
		System.out.println(s);
	}
	
}
