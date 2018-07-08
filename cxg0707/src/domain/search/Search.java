package domain.search;

import java.util.ArrayList;
import java.util.List;

import domain.book.Book;
import domain.book.BookDao;
import domain.book.BookDaoImpl;

public class Search {
	public String search(Book b, int usertype) {
		String message="";
		StringBuilder displayTable = new StringBuilder();
		if (usertype == 0) {
			displayTable.append("<tr><th>ISBN</th><th>Title</th><th>Author</th><th>Category</th><th>Edition</th><th>Status</th></tr>"); //set up first line of table
		}else {
			displayTable.append("<tr><th>ISBN</th><th>Title</th><th>Author</th><th>Category</th><th>Edition</th><th>Status</th><th>CheckOutDate</th><th>ReturnByDate</th></tr>");
		}
		BookDao bookDao = new BookDaoImpl();
		List<Book> temp = new ArrayList<Book>();
		List<Book> books = bookDao.getTitleList();//new ArrayList<Book>();
		
		if (!b.getISBN().isEmpty()) {
			temp = bookDao.SearchTitleFields("ISBN", b.getISBN());
			books.retainAll(temp);
			}
		
		if (!b.getTitle().isEmpty()) {
			temp = bookDao.SearchTitleFields("Title", b.getTitle());
			books.retainAll(temp);
			//books = temp;
		}
		
		if (!b.getAuthor().isEmpty()) {
			temp = bookDao.SearchTitleFields("Author", b.getAuthor());
			books.retainAll(temp);
		}
		
		if (!b.getCategory().isEmpty()) {
			temp = bookDao.SearchTitleFields("Category", b.getCategory());
			books.retainAll(temp);
		}
		
		if (!b.getEdition().isEmpty()) {
			temp = bookDao.SearchTitleFields("Edition", b.getEdition());
			books.retainAll(temp);
		}
		/*
		if (b.getCopyID().length()>0) {
		}
		
		if (b.getRentedBy().length()>0) {
		}
		
		if (b.getCheckOutDate().length()>0) {
		}
		
		if (b.getStatus().length()>0) {
			//bookDao.changeStatus(ISBN, Integer.parseInt(b.getStatus()));
			//bookDao.editTitleFields("Status", ISBN, t.getStatus());
		}
		*/
		for (int i=0;i<books.size(); i++) {
			displayTable.append("<tr>"); //for start of line in table
			displayTable.append("<td>").append(books.get(i).getISBN()).append("</td>"); // prints ISBN
			displayTable.append("<td>").append(books.get(i).getTitle()).append("</td>"); // prints Title
			displayTable.append("<td>").append(books.get(i).getAuthor()).append("</td>"); // prints Author
			displayTable.append("<td>").append(books.get(i).getCategory()).append("</td>"); // prints Category
			displayTable.append("<td>").append(books.get(i).getEdition()).append("</td>"); // prints Edition
			displayTable.append("<td>").append(books.get(i).getStatus()).append("</td>"); //prints status text
			if (usertype == 1) {
				displayTable.append("<td>").append(books.get(i).getCheckOutDate()).append("</td>");
				displayTable.append("<td>").append(books.get(i).getReturnByDate()).append("</td>");
			}
			displayTable.append("</tr>"); //for end of line in table
			//message = Integer.toString(i);
		}
		message=displayTable.toString(); //transforms stringbuilder into string
		return message;
	}

}
