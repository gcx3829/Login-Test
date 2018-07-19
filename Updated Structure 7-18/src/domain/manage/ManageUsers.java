package domain.manage;

import java.util.List;

import domain.user.User;
import domain.user.UserDao;
import domain.user.UserDaoImpl;

public class ManageUsers {
	
	public String displayUsers() {
		String message="";
		StringBuilder displayTable = new StringBuilder();
		displayTable.append("<tr><th>Username</th><th>Name</th><th>Password</th><th>Type</th></tr>"); //set up first line of table
		UserDao userDao = new UserDaoImpl();
		List<User> users = userDao.getUserList();
		
		for (int i=0;i<users.size(); i++) {
			displayTable.append("<tr>"); //for start of line in table
			displayTable.append("<td>").append(users.get(i).getUsername()).append("</td>");
			displayTable.append("<td>").append(users.get(i).getName()).append("</td>"); // prints name
			displayTable.append("<td>").append(users.get(i).getPassword()).append("</td>"); // prints password
			displayTable.append("<td>").append(users.get(i).getUsertype()).append("</td>"); // prints usertype
			displayTable.append("</tr>"); //for end of line in table
		}
		
		
		
		message=displayTable.toString(); //transforms stringbuilder into string
		return message;
	}

}
