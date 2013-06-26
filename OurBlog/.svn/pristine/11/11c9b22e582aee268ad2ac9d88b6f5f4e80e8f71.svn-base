package models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import play.data.binding.NoBinding;
import play.data.validation.Password;
import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;

/**
 * @author Wahson Leung
 * @version 2013-4-6 下午7:48:02
 * 
 */

@Entity
@Table(name = "tb_user", catalog = "blog")
public class User extends Model {
	@Required
	public String user_name;

	@Required
	@Password
	public String user_password;

	@Lob
	public Blob head_protrait;

	@NoBinding("profile")
	public boolean isAdmin;

	@OneToMany(mappedBy = "author")
	public List<Blog> blogs;
	// 关注
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tb_follow", catalog = "blog", joinColumns = { @JoinColumn(name = "follow_from") }, inverseJoinColumns = { @JoinColumn(name = "follow_to") })
	public List<User> followings;

	// 粉丝
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "followings")
	public List<User> followers;

	public User(String user_name, String user_password) {
		super();
		this.user_name = user_name;
		this.user_password = user_password;
		followers = new ArrayList<User>();
		followings = new ArrayList<User>();
		blogs = new ArrayList<Blog>();
	}

	public static User connect(String user_name, String user_password) {
		return User.find("byUser_nameAndUser_password", user_name,
				user_password).first();
	}

	public User addFollowings(User following) {
		this.followings.add(following);
		this.save();
		return this;
	}

	public User removeFollowings(User following) {
		this.followings.remove(following);
		this.save();
		return this;
	}

	@Override
	public String toString() {
		return user_name;
	}

	public boolean containFollowing(Long follow_id) {
		User follow = User.findById(follow_id);
		return followings.contains(follow);
	}
}
