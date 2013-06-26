package controllers;

import models.User;

/**
 * @author Wahson Leung
 * @version 2013-4-8 下午11:35:06
 * 
 */
public class Security extends controllers.Secure.Security {

	 static boolean check(String profile) {
		if ("admin".equals(profile)) {
			return User.find("user_name", connected()).<User> first().isAdmin;
		}
		return false;
	}

	public static boolean authentify(String user_name, String user_password) {
		return User.connect(user_name, user_password) != null;
	}
	
//	static void onDisconnected() {
//		Application.index();
//	}

//	static void onAuthenticated() {
//		UserAdmin.index();
//	}
}
