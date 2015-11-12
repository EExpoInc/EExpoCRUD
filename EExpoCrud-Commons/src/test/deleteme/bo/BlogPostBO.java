package test.deleteme.bo;

import test.deleteme.db.BlogPostEntity;

public class BlogPostBO {
	
	//Dao<PostEntity> dao = new Dao<>();
	
	public BlogPostEntity createDraft(){
		BlogPostEntity pe = new BlogPostEntity();
		pe.draft = true;
		//dao.create(pe);
		return pe;
	}
	
	public void create(BlogPostEntity pe){
		System.err.println("public void create(PostEntity pe){ "+ pe.id + " " +pe.uf);
	}
	
	public int total(){
		return 987;
	}
	
	
	public void doSomething() {
		// TODO Auto-generated method stub
		
	}
	public void publish(BlogPostEntity pe) {
		System.err.println("	public void publish(PostEntity pe) {"+ pe.id + " " +pe.uf);
		
	}
	
}
