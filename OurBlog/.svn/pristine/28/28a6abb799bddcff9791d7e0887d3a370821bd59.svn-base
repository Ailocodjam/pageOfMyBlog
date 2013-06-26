import org.junit.*;

import controllers.Security;

import java.util.*;

import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

	@Before
	public void setUp() {
		Fixtures.deleteDatabase();
		Fixtures.loadModels("data.yml");		
	}

//	@Test
	public void aVeryImportantThingToTest() {

		User u1 = User.find("user_name", "kaka").first();
		User u2 = User.find("user_name", "messy").first();
		User u3 = User.find("user_name", "CR").first();
		User u4 = User.find("user_name", "me").first();

		assertNotNull(u1);
		assertNotNull(u2);
		assertNotNull(u3);
		assertNotNull(u4);

		u1.followings.add(u2);
		u1.followings.add(u3);
		u3.followings.add(u2);
		u1.followings.add(u4);
		
		u1.save();
		u3.save();
		u1 = User.find("user_name", "kaka").first();
		assertNotNull(u1);
		System.out.println(u1.followings.size());
		u2 = User.find("user_name", "messy").first();
		assertNotNull(u2);
		System.out.println(u2.followers);
		
		
	}

	@Test
	public void tryConnect(){
//		User user=User.connect("kaka", "secret");
//		assertNotNull(user);
//		assertEquals("kaka", user.user_name);
//		assertEquals("secret", user.user_password);
	}
	
//	@Test
	public void tryAddComment(){
		
		Blog b1=Blog.find("title", "1st blog").first();
		User user=User.find("user_name", "you").first();
		
		assertNotNull(b1);
		assertNotNull(user);
		
		String content="good!";
		
		b1.addComment(user, content);
		b1.addComment(user, "nice");
//		b1.save();
		
//		assertEquals(3, Comment.count());
		
	}
	
	//@Test
	public void tryUserAdmin(){
		User author=User.find("user_name", "kaka").first();
		List<User> users=User.findAll();
		assertNotNull(author);
		
		author.followings.add(users.get(1));
		author.followings.add(users.get(2));
		author.followings.add(users.get(3));
		author.save();
//			List<Blog> blogs=Blog.find("author", author).fetch();
//			List<Comment> comments=Comment.find("author", author).fetch();
//			
//			System.out.println(blogs);
//			System.out.println(comments);
//			assertEquals(3, blogs.size());
//			assertEquals(1, comments.size());
		System.out.println("______________________________");
		System.out.println(author.followings.size());
		System.out.println(users.get(2).followers.size());
	}
	
	//@Test
	public void tryCommentAdmin(){
		User author = User.find("user_name", "Wahson").first();
		assertNotNull(author);
		List<Blog> blogs = Blog.find("user_id", author.id).fetch();
		assertTrue(blogs.size()>0);
		List<Comment> comments = new ArrayList<Comment>();
		for (Blog blog : blogs) {
			comments.addAll(Comment.find("blog", blog).<Comment> fetch());
		}
		
		System.out.println(comments);
		
		
	}
	
	//@Test
	public void tryDeleteComment(){
		System.out.println(Comment.count());
		int i=Comment.delete("id", 8L);
		assertEquals(0, i);
		System.out.println(Comment.findAll());
	}
}
