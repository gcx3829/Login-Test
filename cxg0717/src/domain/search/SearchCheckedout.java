package domain.search;

import java.util.ArrayList;
import java.util.List;

import domain.book.Book;
import domain.book.BookDao;
import domain.book.BookDaoImpl;

public class SearchCheckedout implements Search{
	List<String> values;
	public SearchCheckedout(String renter) {
		values =  new ArrayList<String>();
		for (int i=0; i<5; i++) {
			values.add("");
		}
		values.add(renter);
		values.add("");
	}

	@Override
	public String find() {
		BookDao bookDao = new BookDaoImpl();
		List<Book> books = new ArrayList<Book>();
		books = bookDao.searchDetails(this);
		return displayBooks(books);
	}

	private String displayBooks(List<Book> books) {
		String message="";
		StringBuilder displayTable = new StringBuilder();
		
		if (books.size()==0) { //no books returned; return warning message about search instead
			displayTable.append("<tr><th>There are no books checked out by you at this time</th></tr>");
		} else { //table can be set up because books have been returned
			displayTable.append("<tr><th>ISBN</th><th>Title</th><th>Author</th><th>Category</th><th>Edition</th>"
					+ "<th>Check Out Date</th><th>Return Deadline</th></tr>"); //set up first line of table
			
			for (int i=0;i<books.size(); i++) {
				displayTable.append("<tr>"); //for start of line in table
				displayTable.append("<td>").append(books.get(i).getISBN()).append("</td>"); // prints ISBN
				displayTable.append("<td>").append(books.get(i).getTitle()).append("</td>"); // prints Title
				displayTable.append("<td>").append(books.get(i).getAuthor()).append("</td>"); // prints Author
				displayTable.append("<td>").append(books.get(i).getGenre()).append("</td>"); // prints Category
				displayTable.append("<td>").append(books.get(i).getEdition()).append("</td>"); // prints Edition
				String temp="";
				if (books.get(i).getCheckOutDate().isEmpty()) {temp = "N/A"; }
				else {temp = books.get(i).getCheckOutDate(); }
				displayTable.append("<td>").append(temp).append("</td>"); // prints Check Out Date
				if (books.get(i).getReturnByDate().isEmpty()) {temp = "N/A"; }
				else {temp = books.get(i).getReturnByDate(); }
				displayTable.append("<td>").append(temp).append("</td>"); // prints Return By Date
				
				displayTable.append("</tr>"); //for end of line in table
			}
		}
		message=displayTable.toString(); //transforms stringbuilder into string
		return message;
	}
	
	@Override
	public String getValue(String field) {
		String result="";
		switch (field) {
			case "ISBN":
				result=values.get(0);
				break;
			case "Title":
				result=values.get(1);
				break;
			case "Author":
				result=values.get(2);
				break;
			case "Genre":
				result=values.get(3);
				break;
			case "Edition":
				result=values.get(4);
				break;
			case "Renter":
				result=values.get(5);
				break;
			case "Status":
				result=values.get(6);
				break;
			case "ID":
				result=values.get(0);
				break;
			default:
		}
		return result;
	}
}
