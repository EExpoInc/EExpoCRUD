package test.deleteme.mvc;

import test.deleteme.db.BlogPostEntity;

public class Main {
	public static void main(String[] args) {
		BlogPostController bpc = new BlogPostController();
		System.out.println(bpc.publishAction(null).link());
		System.out.println(BlogPostController.preEditAction().link());
		System.out.println(bpc.createAction().link());
		System.out.println(bpc.createAction_(new BlogPostEntity() {
			{
				this.id = 1;
				this.draft = false;
				this.title = "meu post";
				this.uf = "MS";
			}
		}).link());
	}
}
