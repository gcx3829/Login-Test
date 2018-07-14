package domain.search;

import java.util.List;

import domain.book.*;

public class DisplayAll {
	public static String displayCollection() {
		String message="";
		StringBuilder displayTable = new StringBuilder();
		displayTable.append("<tr><th>ISBN</th><th>Title</th><th>Author</th><th>Status</th></tr>"); //set up first line of table
		BookDao bookDao = new BookDaoImpl();
		List<Book> titles = bookDao.getTitleList();
		
		for (int i=0;i<titles.size(); i++) {
			displayTable.append("<tr>"); //for start of line in table
			displayTable.append("<td>").append(titles.get(i).getISBN()).append("</td>"); // prints ISBN
			displayTable.append("<td>").append(titles.get(i).getTitle()).append("</td>"); // prints Title
			displayTable.append("<td>").append(titles.get(i).getAuthor()).append("</td>"); // prints Author
			displayTable.append("<td>").append(titles.get(i).getStatus()).append("</td>"); // prints Status
			displayTable.append("</tr>"); //for end of line in table
		}
		
		
		
		message=displayTable.toString(); //transforms stringbuilder into string
		return message;
	}

}
