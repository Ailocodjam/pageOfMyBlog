package controllers;

import play.*;
import play.db.jpa.Blob;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

	@Before
	public static void addDefault() {
		renderArgs.put("blogTitle",
				Play.configuration.getProperty("blog.title"));
		renderArgs.put("blogBaseline",
				Play.configuration.getProperty("blog.baseline"));
	}

	public static void index() {
		List<Blog> blogs = Blog.find("order by release_date desc").fetch(10);
		render(blogs);
	}

//	public static void show(Long id) {
//		Blog blog = Blog.findById(id);
//		if (blog == null) {
//			index();
//		}
//		User author = blog.author;
//		render(blog, author);
//	}

//	public static void blogComment(Long blog_id, String content) {
//		if (!Security.isConnected()) {
//			Admin.login();
//		} else {
//			User author = User.find("user_name", Security.connected()).first();
//			Blog blog = Blog.findById(blog_id);
//			blog.addComment(author, content);
//			flash.success("Thank you %s", author.user_name);
//			UserAdmin.show(blog_id);
//		}
//	}

	public static void upLoadFile(Blob photo) {
		// File to =photo.getFile();
		// Images.resize(photo.getFile(),to, 100, 100);
		// User user=new User();
		// user.photo=photo;
		// user.save();
		// getPhoto(user.id);
	}

	public static void getPhoto(Long id) {
		// User user=User.findById(id);
		// if(user!=null){
		// System.out.println(user.photo.getFile().length()/1024);
		// response.setContentTypeIfNotSet(picture.image.type());
		// renderBinary(user.photo.get());
		// }
		// render();
	}

}