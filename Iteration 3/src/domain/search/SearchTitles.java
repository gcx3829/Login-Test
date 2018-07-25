package domain.search;

import java.util.ArrayList;
import java.util.List;

import domain.book.Book;
import domain.book.BookDao;
import domain.book.BookDaoImpl;

public class SearchTitles implements Search{
	List<String> values;
	
	public SearchTitles(String ISBN, String Title, String Author, String genre, String edition) {
		//
		values =  new ArrayList<String>();
		values.add(ISBN);
		values.add(Title);
		values.add(Author);
		values.add(genre);
		values.add(edition);
	}
	
	public String find() {
		BookDao bookDao = new BookDaoImpl();
		List<Book> books = bookDao.search(this);
		return displayBooks(books);
	}
	
	
	private String displayBooks(List<Book> books) {
		String message="";
		StringBuilder displayTable = new StringBuilder();
		BookDao bookDao = new BookDaoImpl();
		
		if (books.size()==0) { //no books returned; return warning message about search instead
			displayTable.append("<tr><th>There were no books returned from this search. Please"
					+ " choose new Search Parameters</th></tr>");
		} else { //table can be set up because books have been returned
			displayTable.append("<tr><th>ISBN</th><th>Title</th><th>Author</th><th>Genre</th><th>Edition</th><th>Status</th>"); //set up first line of table
			String last = "";
			for (int i=0;i<books.size(); i++) {
				if (last.compareTo(books.get(i).getISBN())!=0) { //does not print same title twice
					displayTable.append("<tr>"); //for start of line in table
					displayTable.append("<td>").append(books.get(i).getISBN()).append("</td>"); // prints ISBN
					displayTable.append("<td>").append(books.get(i).getTitle()).append("</td>"); // prints Title
					displayTable.append("<td>").append(books.get(i).getAuthor()).append("</td>"); // prints Author
					displayTable.append("<td>").append(books.get(i).getGenre()).append("</td>"); // prints Category
					displayTable.append("<td>").append(books.get(i).getEdition()).append("</td>"); // prints Edition
					String statusText; //find if book with this ISBN is available
					if (Integer.toString(bookDao.findStatus(books.get(i).getISBN())).compareTo("1")==0) {
						statusText = "Available";
					} else if(Integer.toString(bookDao.findStatus(books.get(i).getISBN())).compareTo("5")==0) {
						statusText = "Has Waitlist";
					} else {
						statusText = "Unavailable";
					}
					displayTable.append("<td>").append(statusText).append("</td>"); //prints status text
					last = books.get(i).getISBN(); //set variable to check for duplicates
				}
			}
			
			displayTable.append("</tr>"); //for end of line in table
		}
		message=displayTable.toString(); //transforms stringbuilder into string
		return message;
	}
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
			default:
		}
		return result;
	}
}
