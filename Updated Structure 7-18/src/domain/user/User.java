package domain.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import domain.book.Book;
import domain.book.BookDao;
import domain.book.BookDaoImpl;
import domain.loan.Loan;
import domain.loan.LoanDao;
import domain.loan.LoanDaoImpl;

/**
 * 
 * @author mehra
 *
 */
public class User {

	private String username;
	private String password;
	private String name;
	private int usertype;
	
	private Map<String, Book> rentedBooks; //books that have been checked out by user
	
	public String checkOutBook(String ISBN ) {
		BookDao bookDao = new BookDaoImpl();
		String message="";
		Book b = getLoanedBook(ISBN);
		if (b!=null) {
			message = "You already rented book " +b.getTitle()+" !!! Check out failed";
			return message;
		}
		
		Loan l = new Loan(ISBN, this);
		int status = l.verify();
		if (status==2) {
			message = "book with ISBN "+ ISBN + " not found in database";
			return message;
		} else if (status==3) {
			message = "Book " +bookDao.getTitle(ISBN).getTitle()+" check out failed!!";
			return message;
		} else if (status==1) {
			message = "Book " +bookDao.getTitle(ISBN).getTitle()+" is not available!!";
			return message;
		}
		message = "Book " +bookDao.getTitle(ISBN).getTitle()+" was checked out successfully";
		
		return message;
	}
	public String returnBook(String ISBN) {
		BookDao bookDao = new BookDaoImpl();
		String message="";
		
		Book b = getLoanedBook(ISBN);
		Loan l = new Loan(b, this);

		if (b==null) {
			message = "You haven't rented a book with ISBN: " +ISBN+" !!! Return failed";
			return message;
		}
		
		if (b.getTitle()==null) {
			message = "book with ISBN "+ ISBN + " not found in database";
			return message;
		} //else if (status==1) {
			//message = "You did not rent book " +bookDao.getTitle(b).getTitle()+" !!! Return failed";
			//return message;
		//}
		
		int status = l.endLoan();
		if (status==1) {
			message = "Book " +bookDao.getTitle(ISBN).getTitle()+" return failed!!";
			return message;
		} 
		message = "Book " + b.getTitle()+" was returned successfully";
		rentedBooks.remove(ISBN);
		
		return message;
	}
	
	public void addToRented(Book book) { //called by checkout function
		rentedBooks.put(book.getISBN(), book);
	}
	
	public Book getLoanedBook(String ISBN) {
		Book b = rentedBooks.get(ISBN); //get book if it exists
		return b;
	}
	
	public void setRentedBooks() {
		LoanDao loanDao = new LoanDaoImpl();
		rentedBooks = loanDao.rentedBooks(this);
	}
	
	private List<Book> getRentedBooks() {
		List<Book> rented = new ArrayList<Book>(rentedBooks.values());
		return rented;
	}
	
	public String displayBooks() {
		String message="";
		StringBuilder displayTable = new StringBuilder();
		List<Book> books = getRentedBooks();
		
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
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUsertype() {
		return usertype;
	}
	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

	
}

