package domain.search;

import java.util.ArrayList;
import java.util.List;

import domain.book.Book;
import domain.book.BookDao;
import domain.book.BookDaoImpl;

public class SearchBookDetails implements Search{
	List<String> values;
	public SearchBookDetails(String ISBN, String Title, String Author, String genre, String edition, String renter, String status) {
		values =  new ArrayList<String>();
		values.add(ISBN);
		values.add(Title);
		values.add(Author);
		values.add(genre);
		values.add(edition);
		values.add(renter);
		if (status.compareTo("10")!=0) {
			values.add(status);
		} else {values.add("");}
		
	}
	
	public SearchBookDetails(String ID) {
		values =  new ArrayList<String>();
		values.add(ID);
	}
	
	public SearchBookDetails(String field, List<String> orValues) { //OR search
		values =  new ArrayList<String>();
		int i;
		int j;
		int k=0;
		switch (field) {
			case "ISBN":
				i=0; break;
			case "Title":
				i=1; break;
			case "Author":
				i=2; break;
			case "Genre":
				i=3; break;
			case "Edition":
				i=4; break;
			case "Renter":
				i=5; break;
			case "Status":
				i=6; break;
			case "ID":
				i=0; break;
			default:
				i=7;
		}
		for (j=0;j<i;j++) {values.add(""); }

		values.add(orValues.get(k));
		for (j=i+1; j<7; j++) {values.add("");}
		for (k=1;k<orValues.size();k++) {
			values.add(orValues.get(k));
		}
	}
	
	public String find() {
		BookDao bookDao = new BookDaoImpl();
		List<Book> books = new ArrayList<Book>();
		if (values.size()==7) { 
			books = bookDao.searchDetails(this);
		}
		else if (values.size()==1){
			Book book = new Book();
			book.setInventoryID(values.get(0));
			book = bookDao.getBook(values.get(0));
			books.add(book);
		} else {
			int i=0;
			books = bookDao.searchDetails(this);
			while (values.get(i).length()==0) {
				i++;
			}
			
			while (values.size()!=7) {
				//
				values.set(i, values.get(7));
				values.remove(7);
				books.addAll(bookDao.searchDetails(this));
			}
		}
		
		return displayBooks(books);
	}
	
	private String displayBooks(List<Book> books) {
		String message="";
		StringBuilder displayTable = new StringBuilder();
		
		if (books.size()==0) { //no books returned; return warning message about search instead
			displayTable.append("<tr><th>There were no books returned from this search. Please"
					+ " choose new Search Parameters</th></tr>");
		} else { //table can be set up because books have been returned
			displayTable.append("<tr><th>Inventory ID</th><th>ISBN</th><th>Title</th><th>Author</th><th>Genre</th><th>Edition</th>"
					+ "<th>Status</th><th>Rented By</th><th>Check Out Date</th><th>Return Deadline</th></tr>"); //set up first line of table
			
			for (int i=0;i<books.size(); i++) {
				displayTable.append("<tr>"); //for start of line in table
				displayTable.append("<td>").append(books.get(i).getInventoryID()).append("</td>");
				displayTable.append("<td>").append(books.get(i).getISBN()).append("</td>"); // prints ISBN
				displayTable.append("<td>").append(books.get(i).getTitle()).append("</td>"); // prints Title
				displayTable.append("<td>").append(books.get(i).getAuthor()).append("</td>"); // prints Author
				displayTable.append("<td>").append(books.get(i).getGenre()).append("</td>"); // prints Category
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
				}else if (books.get(i).getStatus().compareTo("5")==0){
					statusText = "Reserved (Waitlist)";
				}
				displayTable.append("<td>").append(statusText).append("</td>"); //prints status text
				String temp;
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
			}
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
