package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * @author Wahson Leung
 * @version 2013-4-6 下午7:55:55
 * 
 */
@Entity
@Table(name="tb_comment",catalog="blog")
public class Comment extends Model {

	@Required
	@ManyToOne
	@JoinColumn(name = "blog_id")
	public Blog blog;

	@Required
	@ManyToOne
	@JoinColumn(name = "user_id")
	public User author;

	@Lob
	@Required
	@MaxSize(1000)
	public String content;

	public Date comment_date;

	public Comment(Blog blog, User author, String content) {
		super();
		this.blog = blog;
		this.author = author;
		this.content = content;
		this.comment_date = new Date();
	}

	@Override
	public String toString() {
		return content;
	}

	
}
