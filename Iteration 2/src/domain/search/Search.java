package domain.search;

import java.util.ArrayList;
import java.util.List;

import domain.book.Book;
import domain.book.BookDao;
import domain.book.BookDaoImpl;

public class Search {
	public List<Book> search(Book b) {
		BookDao bookDao = new BookDaoImpl();
		List<Book> temp = new ArrayList<Book>();
		List<Book> books = bookDao.getAllBooks();//new ArrayList<Book>();
		
		
		temp = bookDao.SearchTitleFields("ISBN", b.getISBN());
		books.retainAll(temp);
		
		temp = bookDao.SearchTitleFields("Title", b.getTitle());
		books.retainAll(temp);
			//books = temp;
		
		temp = bookDao.SearchTitleFields("Author", b.getAuthor());
		books.retainAll(temp);
		
		temp = bookDao.SearchTitleFields("Category", b.getCategory());
		books.retainAll(temp);
		
			temp = bookDao.SearchTitleFields("Edition", b.getEdition());
			books.retainAll(temp);
		
		if (b.getCopyID().length()>0) { //need to check for all fields which an be Null
			temp = bookDao.SearchCopyFields("CopyID", b.getCopyID());
			books.retainAll(temp);
		}
		
		if (b.getRentedBy().length()>0) {
			temp = bookDao.SearchCopyFields("RentedBy", b.getRentedBy());
			books.retainAll(temp);
		}
		
		if (b.getCheckOutDate().length()>0) {
			temp = bookDao.SearchCopyFields("CheckOutDate", b.getCheckOutDate());
			books.retainAll(temp);
		}
		
		if (b.getReturnByDate().length()>0) {
			temp = bookDao.SearchCopyFields("ReturnByDate", b.getReturnByDate());
			books.retainAll(temp);
		}
		
		if (b.getStatus().length()>0) {
			temp = bookDao.SearchCopyFields("Status", b.getStatus());
			books.retainAll(temp);
			//bookDao.changeStatus(ISBN, Integer.parseInt(b.getStatus()));
			//bookDao.editTitleFields("Status", ISBN, t.getStatus());
		}
		
		return books;
	}
	
	public String displayTitles(List<Book> books) {
		String message="";
		StringBuilder displayTable = new StringBuilder();
		BookDao bookDao = new BookDaoImpl();
		displayTable.append("<tr><th>ISBN</th><th>Title</th><th>Author</th><th>Category</th><th>Edition</th><th>Status</th></tr>"); //set up first line of table
		
		String last = "";
		for (int i=0;i<books.size(); i++) {
			if (last.compareTo(books.get(i).getISBN())!=0) { //does not print same title twice
				displayTable.append("<tr>"); //for start of line in table
				displayTable.append("<td>").append(books.get(i).getISBN()).append("</td>"); // prints ISBN
				displayTable.append("<td>").append(books.get(i).getTitle()).append("</td>"); // prints Title
				displayTable.append("<td>").append(books.get(i).getAuthor()).append("</td>"); // prints Author
				displayTable.append("<td>").append(books.get(i).getCategory()).append("</td>"); // prints Category
				displayTable.append("<td>").append(books.get(i).getEdition()).append("</td>"); // prints Edition
				String statusText; //find if book with this ISBN is available
				if (Integer.toString(bookDao.findStatus(books.get(i).getISBN())).compareTo("1")==0) {
					statusText = "Available";
				} else {
					statusText = "Unavailable";
				}
				displayTable.append("<td>").append(statusText).append("</td>"); //prints status text
				last = books.get(i).getISBN(); //set variable to check for duplicates
			}
			
			displayTable.append("</tr>"); //for end of line in table
			//message = Integer.toString(i);
		}
		message=displayTable.toString(); //transforms stringbuilder into string
		return message;
	}

	public String getCopyID(String ISBN, String TargetStatus) {
		Book b = new Book();
		//List<Book> books = new ArrayList<Book>();
		String message = "0";
		
		b.setNull();
		b.setISBN(ISBN);
		b.setStatus(TargetStatus);
		Search s = new Search();
		List<Book> books = s.search(b);
		if (books.size()>0)  { //get first copy that meets requirements
			message = books.get(0).getCopyID();
		}
		return message;
	}
	
	public String displayBooks(List<Book> books) {
		String message="";
		StringBuilder displayTable = new StringBuilder();
		BookDao bookDao = new BookDaoImpl();
		displayTable.append("<tr><th>ISBN</th><th>Title</th><th>Author</th><th>Category</th><th>Edition</th><th>Status</th>"
				+ "<th>Copy ID</th><th>Rented By</th><th>Check Out Date</th><th>Return Deadline</th></tr>"); //set up first line of table
		
		String temp;
		for (int i=0;i<books.size(); i++) {
			displayTable.append("<tr>"); //for start of line in table
			displayTable.append("<td>").append(books.get(i).getISBN()).append("</td>"); // prints ISBN
			displayTable.append("<td>").append(books.get(i).getTitle()).append("</td>"); // prints Title
			displayTable.append("<td>").append(books.get(i).getAuthor()).append("</td>"); // prints Author
			displayTable.append("<td>").append(books.get(i).getCategory()).append("</td>"); // prints Category
			displayTable.append("<td>").append(books.get(i).getEdition()).append("</td>"); // prints Edition
			String statusText=""; //find if book with this ISBN is available
			if (books.get(i).getStatus().compareTo("1")==0) {
				statusText = "Available";
			}else if (books.get(i).getStatus().compareTo("2")==0){
				statusText = "Returned";
			}else if (books.get(i).getStatus().compareTo("3")==0){
				statusText = "Under Repair";
			}else if (books.get(i).getStatus().compareTo("4")==0){
				statusText = "Ruined";
			}else if (books.get(i).getStatus().compareTo("0")==0){
				statusText = "Checked Out";
			}
			//statusText = books.get(i).getStatus();
			displayTable.append("<td>").append(statusText).append("</td>"); //prints status text
			displayTable.append("<td>").append(books.get(i).getCopyID()).append("</td>");
			if (books.get(i).getRentedBy().isEmpty()) {temp = "N/A"; }
			else {temp = books.get(i).getRentedBy(); }
			displayTable.append("<td>").append(temp).append("</td>"); // prints Rented By
			if (books.get(i).getCheckOutDate().isEmpty()) {temp = "N/A"; }
			else {temp = books.get(i).getCheckOutDate(); }
			displayTable.append("<td>").append(temp).append("</td>"); // prints Check Out Date
			if (books.get(i).getReturnByDate().isEmpty()) {temp = "N/A"; }
			else {temp = books.get(i).getReturnByDate(); }
			displayTable.append("<td>").append(temp).append("</td>"); // prints Return By Date
			
			displayTable.append("</tr>"); //for end of line in table
			//message = Integer.toString(i);
		}
		message=displayTable.toString(); //transforms stringbuilder into string
		return message;
	}
	
	public String displayUnloaned(List<Book> books) {
		String message="";
		StringBuilder displayTable = new StringBuilder();
		BookDao bookDao = new BookDaoImpl();
		displayTable.append("<tr><th>ISBN</th><th>Title</th><th>Author</th><th>Category</th><th>Edition</th><th>Status</th></tr>"); //set up first line of table
		
		String temp;
		for (int i=0;i<books.size(); i++) {
			displayTable.append("<tr>"); //for start of line in table
			displayTable.append("<td>").append(books.get(i).getISBN()).append("</td>"); // prints ISBN
			displayTable.append("<td>").append(books.get(i).getTitle()).append("</td>"); // prints Title
			displayTable.append("<td>").append(books.get(i).getAuthor()).append("</td>"); // prints Author
			displayTable.append("<td>").append(books.get(i).getCategory()).append("</td>"); // prints Category
			displayTable.append("<td>").append(books.get(i).getEdition()).append("</td>"); // prints Edition
			String statusText=""; //find if book with this ISBN is available
			if (Integer.toString(bookDao.findStatus(books.get(i).getISBN())).compareTo("1")==0) {
				statusText = "Available";
			}else if (Integer.toString(bookDao.findStatus(books.get(i).getISBN())).compareTo("2")==0){
				statusText = "Returned";
			}else if (Integer.toString(bookDao.findStatus(books.get(i).getISBN())).compareTo("3")==0){
				statusText = "Under Repair";
			}else if (Integer.toString(bookDao.findStatus(books.get(i).getISBN())).compareTo("4")==0){
				statusText = "Ruined";
			}else if (Integer.toString(bookDao.findStatus(books.get(i).getISBN())).compareTo("0")==0){
				statusText = "Checked Out";
			}
			displayTable.append("<td>").append(statusText).append("</td>"); //prints status text
			displayTable.append("</tr>"); //for end of line in table
			//message = Integer.toString(i);
		}
		message=displayTable.toString(); //transforms stringbuilder into string
		return message;
	}

}


/*
 * String last = "";
		for (int i=0;i<books.size(); i++) {
			if (last.compareTo(books.get(i).getISBN())!=0) {
				displayTable.append("<tr>"); //for start of line in table
				displayTable.append("<td>").append(books.get(i).getISBN()).append("</td>"); // prints ISBN
				displayTable.append("<td>").append(books.get(i).getTitle()).append("</td>"); // prints Title
				displayTable.append("<td>").append(books.get(i).getAuthor()).append("</td>"); // prints Author
				displayTable.append("<td>").append(books.get(i).getCategory()).append("</td>"); // prints Category
				displayTable.append("<td>").append(books.get(i).getEdition()).append("</td>"); // prints Edition
				displayTable.append("<td>").append(books.get(i).getStatus()).append("</td>"); //prints status text
				displayTable.append("<td>").append(books.get(i).getCopyID()).append("</td>");
				if (usertype == 1) {
					displayTable.append("<td>").append(books.get(i).getRentedBy()).append("</td>");
					displayTable.append("<td>").append(books.get(i).getCheckOutDate()).append("</td>");
					displayTable.append("<td>").append(books.get(i).getReturnByDate()).append("</td>");
				}
				last = books.get(i).getISBN();
			}
			
			displayTable.append("</tr>"); //for end of line in table
			//message = Integer.toString(i);
		}
		message=displayTable.toString(); //transforms stringbuilder into string
		*/
