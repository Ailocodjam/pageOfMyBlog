package controllers;

import java.util.*;

import javax.persistence.Cache;

import org.ietf.jgss.Oid;

import models.*;
import play.Play;
import play.data.validation.*;
import play.i18n.Messages;
import play.libs.*;
import play.mvc.*;
import play.cache.*;

/**
 * @author Wahson Leung
 * @version 2013-4-9 上午11:16:28
 * 
 */
public class UserAdmin extends Controller {

	@Before(only = { "addFollowing", "blogComment", "reprintBlog", "homeIndex",
			"createBlog" })
	static void requiredLogin() throws Throwable {
		Secure.checkAccess();
		if (Security.isConnected()) {
			User user = User.find("user_name", Security.connected()).first();
			renderArgs.put("user_name", user.user_name);
		}
	}

	// @Before(only = { "addFollowing", "blogComment", "reprintBlog",
	// "homeIndex" })
	// public static void setConnectedUser() {
	// if (Security.isConnected()) {
	// User user = User.find("user_name", Security.connected()).first();
	// renderArgs.put("user_name", user.user_name);
	// }
	// }

	/*
	 * 编辑个人信息
	 */
	public static void editUserInfo() {

	}

	/*
	 * 用户注册
	 */
	public static void register(String user_name, String user_password) {
		Map<Object, Object> info = new HashMap<Object, Object>();
		if (User.find("user_name", user_name).fetch().size() > 0) {
			info.put("reg_info", "user_name already exist");
			renderJSON(info);
		}
		User user = new User(user_name, user_password);
		user.save();
		info.put("reg_info", "success");
		renderJSON(info);
	}

	/*
	 * 游客看到的博主首页
	 * 
	 * @param user_id 博主id
	 */
	public static void index(Long user_id) {
		User user = User.findById(user_id);
		User guest = User.find("user_name", Security.connected()).first();
		if (user != null) {
			if (guest != null && user.user_name.equals(guest.user_name)) {
				homeIndex();
			}
			List<Blog> blogs = Blog.find(
					"user_id = ? order by release_date desc", user.id).fetch();
			render(user, blogs);
		}
	}

	/*
	 * 博主首页
	 */
	public static void homeIndex() {
		User user = User.find("user_name", Security.connected()).first();
		if (user != null) {
			List<Blog> blogs = Blog.find(
					"user_id = ? order by release_date desc", user.id).fetch();
			render(user, blogs);
		}
	}

	/*
	 * 添加关注
	 * 
	 * @param follow_id 被关注者id
	 */
	public static void addFollowing(Long following_id) {
		User following = User.findById(following_id);
		User user = User.find("user_name", Security.connected()).first();
		if (user != null && following != null
				&& !user.followings.contains(following)) {
			user.addFollowings(following);
		}
		// 传回被关注者的粉丝数
		Map<Object, Object> info = new HashMap<Object, Object>();
		info.put("followers", following.followers.size());
		renderJSON(info);
	}

	/*
	 * 取消关注
	 * 
	 * @param following_id 被取消关注者id
	 */
	public static void removeFollowing(Long following_id) {
		User user = User.find("user_name", Security.connected()).first();
		User following = User.findById(following_id);
		if (following != null) {
			user.removeFollowings(following);
		}
		Map<Object, Object> info = new HashMap<Object, Object>();
		info.put("followers", following.followers.size());
		renderJSON(info);
	}

	/*
	 * 查看博客
	 * 
	 * @param blog_id 博客id
	 */
	public static void show(Long blog_id) {
		Blog blog = Blog.findById(blog_id);
		if (blog == null) {
			index(blog.author.id);
		}
		String randomId = Codec.UUID();
		String mode = "guest_full";
		User user = blog.author;
		if (Security.isConnected()) {
			if (Security.connected().equals(user.user_name)) {
				mode = "home_full";
			}
		}
		render(blog, user, randomId, mode);
	}

	/*
	 * 表示查看用户的粉丝
	 * 
	 * @param user_id 你要查看关注或粉丝的用户的id
	 * 
	 * @param isFollowing true表示查看关注的人 false表示查看粉丝
	 */
	public static void listFollows(Long user_id, boolean isFollowing) {
		User user = User.findById(user_id);
		List<User> follows = new ArrayList<User>();
		String mode = "guest";
		if (user != null) {
			if (isFollowing) {
				follows = user.followings;
			} else {
				follows = user.followers;
			}
			if (Security.isConnected()) {
				if (Security.connected().equals(user.user_name)) {
					mode = "home";
				}
			}
		}
		render(user, follows, mode);
	}

	/*
	 * 评论博客
	 * 
	 * @param blog_id 被评论博客id
	 * 
	 * @param content 评论内容
	 */
	public static void blogComment(Long blog_id,
			@Required(message = "评论内容不能没有喔！") String content,
			@Required(message = "没看到验证码吗？") String code, String randomId) {

		User user = User.find("user_name", Security.connected()).first();
		Blog blog = Blog.findById(blog_id);
		validation.equals(code, play.cache.Cache.get(randomId)).message(
				Messages.get("wrong_code"));

		if (validation.hasErrors()) {
			user = blog.author;
			render("UserAdmin/show.html", blog, user, randomId);
		}
		flash.success("Thank you %s", user.user_name);
		blog.addComment(user, content);
		show(blog_id);

	}

	/*
	 * 生成验证码
	 */
	public static void captcha(String id) {
		Images.Captcha captcha = Images.captcha();
		String code = captcha.getText("#d0e79d");// 设置验证码显示颜色
		play.cache.Cache.set(id, code, "10mn");
		renderBinary(captcha);
	}

	public static void blank() {
		User user = User.find("user_name", Security.connected()).first();
		render(user);
	}

	/*
	 * 发表博客,博主只有登录了，才能够发表博客
	 */
	public static void createNewBlog(
			@Required(message = "亲！别忘了写标题呢！") String title,
			@Required(message = "亲！不能没有主要内容呀！") String content) {

		User user = User.find("user_name", Security.connected()).first();
		if (validation.hasErrors()) {
			// 待续
			render("UserAdmin/blank.html", user);
		}
		Blog newBlog = new Blog(user, "nature", title, content);
		newBlog.save();
		homeIndex();
	}

	/*
	 * 转载博客
	 * 
	 * @param blog_id 被转载博客id
	 */
	public static void reprintBlog(Long blog_id) {
		Blog blog = Blog.findById(blog_id);
		if (blog != null) {
			blog.reprint(
					User.find("user_name", Security.connected()).<User> first())
					.save();
			// 转载成功，回到博客主页
			homeIndex();
		}
		// 转载失败的处理
		//
		//
		renderHtml("<p>转载失败</p>");
	}

	/*
	 * 删除博客
	 * 
	 * @param blog_id 要删除的博客id
	 */
	public static void deleteBlog(Long blog_id) {
		String nameString = Blog.findById(blog_id).toString();
		Blog.delete("id", blog_id);
		flash.success(Messages.get("delete_blog_succ"), nameString);
		homeIndex();
	}

	/*
	 * 修改博客
	 * 
	 * @param
	 */
	public static void modifyBlog(Long blog_id,
			@Required(message = "亲！别忘了写标题呢！") String title,
			@Required(message = "亲！不能没有主要内容呀！") String content) {
		User user = User.find("user_name", Security.connected()).first();
		if (validation.hasErrors()) {
			render("UserAdmin/edit.html", user);
		}
		Blog blog = Blog.findById(blog_id);
		blog.title = title;
		blog.content = content;
		blog.save();
		homeIndex();
	}

	/*
	 * 评论管理
	 */
	public static void commentAdmin() {
		User author = User.find("user_name", Security.connected()).first();
		List<Blog> blogs = Blog.find("byUser", author).fetch();
		List<Comment> comments = new ArrayList<Comment>();
		for (Blog blog : blogs) {
			comments.addAll(Comment.find("blog", blog).<Comment> fetch());
		}
		render(comments);
	}

	/*
	 * 评论删除
	 * 
	 * @param comment_id
	 */
	public static void deleteComment(Long comment_id) {
		Comment.delete("id", comment_id);
		// redirect or else
	}

	// // 判断用户是否已关注
	// public static boolean containFollow(Long follow_id) {
	// return User.find("user_name", Security.connected()).<User> first()
	// .containFollowing(follow_id);
	// }
}
