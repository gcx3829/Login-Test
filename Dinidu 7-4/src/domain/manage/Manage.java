package domain.manage;

import java.util.List;

import domain.book.*;
import domain.user.*;

public class Manage {
	
	public int editBook(Title t, String ISBN) { // new code
		BookDao bookDao = new BookDaoImpl();
		int status=0;
		
		
		if (bookDao.bookExists(ISBN)==1) {
			//
			/*bookDao.changeBookDetails(t, ISBN);
			 */
			status=1;
			
			//
			/*
			if (t.getISBN().length()>0) {
				bookDao.editTitleFields("ISBN", ISBN, t.getISBN());
			}
			
			if (t.getTitle().length()>0) {
				bookDao.editTitleFields("Title", ISBN, t.getTitle());
			}
			
			if (t.getAuthor().length()>0) {
				bookDao.editTitleFields("Author", ISBN, t.getAuthor());
			}
			
			if (t.getCategory().length()>0) {
				bookDao.editTitleFields("Category", ISBN, t.getCategory());
			}
			
			if (t.getEdition().length()>0) {
				bookDao.editTitleFields("Edition", ISBN, t.getEdition());
			}
			
			if (t.getStatus().length()>0) {
				bookDao.editTitleFields("Status", ISBN, t.getStatus());
			}
			*/
		}
		return status;
	}
	
	public int deleteBook() {
		// function to delete books already in database
		return 0;
	}
	
	public int processReturn(String ISBN, int newStatus) {
		// will also pass info to identify copy
		// function to process returned books
		
		// check if book status is 'checked out' or 'returned'
		
		//change status according to condition of book
		//status=titleDao.changeStatus(ISBN, newStatus);
		return 0;
	}
	
	public static String displayUsers() {
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
