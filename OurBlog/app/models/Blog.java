package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * @author Wahson Leung
 * @version 2013-4-6 下午7:56:36
 * 
 */
@Entity
@Table(name = "tb_blog", catalog = "blog")
public class Blog extends Model {

	@Required
	@ManyToOne
	@JoinColumn(name = "user_id")
	public User author;

	public String type;

	@Required
	@MaxSize(100)
	public String title;

	@Required(message = "A message is required!")
	@Lob
	@MaxSize(10000)
	public String content;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "blog")
	public List<Comment> comments;

	@Required
	public Date release_date;

	public Long blog_from;

	public Blog(User author, String type, String title, String content) {
		super();
		this.author = author;
		this.type = type;
		this.title = title;
		this.content = content;
		this.release_date = new Date();
	}

	public Blog previous() {
		return Blog.find(
				"user_id = ? and release_date < ? order by release_date desc",
				this.author.id, release_date).first();
	}

	public Blog next() {
		return Blog.find(
				"user_id = ? and release_date > ? order by release_date desc",
				this.author.id, release_date).first();
	}

	/*
	 * 添加评论
	 * 
	 * @param author 发出评论的人
	 * 
	 * @param content 评论内容
	 */
	public Blog addComment(User author, String content) {
		Comment newComment = new Comment(this, author, content);
		this.comments.add(newComment);
		this.save();
		return this;
	}

	/*
	 * 用户转载本条博客
	 * 
	 * @param author 转载者
	 */
	public Blog reprint(User author) {
		Blog blog = new Blog(author, "reprint", this.title, this.content);
		blog.blog_from = this.id;
		return blog;
	}

	@Override
	public String toString() {
		return title;
	}
}
