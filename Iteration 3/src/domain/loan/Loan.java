package domain.loan;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import domain.book.Book;
import domain.book.BookDao;
import domain.book.BookDaoImpl;
import domain.user.User;

public class Loan {
	private String returnByDate;
	private String checkOutDate;
	private Book book;
	private User renter;
	private BookDao bookDao;
	private LoanDao loanDao;
	
	public Loan() {
		bookDao = new BookDaoImpl();
		loanDao = new LoanDaoImpl();
	}
	public Loan(String ISBN, User user) {
		bookDao = new BookDaoImpl();
		loanDao = new LoanDaoImpl();
		book = bookDao.getTitle(ISBN);
		this.renter = user;
	}
	public Loan(Book book, String username, String CheckOutDate, String ReturnByDate) {
		bookDao = new BookDaoImpl();
		loanDao = new LoanDaoImpl();
		this.book = book;
		this.renter = new User(username);
		this.checkOutDate = CheckOutDate;
		this.returnByDate = ReturnByDate;
	}
	public Loan(Book book, User user) {
		bookDao = new BookDaoImpl();
		loanDao = new LoanDaoImpl();
		this.book = book;
		this.renter = user;
	}
	
	public Loan(Book book, User user, String CheckOutDate, String ReturnByDate) {
		bookDao = new BookDaoImpl();
		loanDao = new LoanDaoImpl();
		this.book = book;
		this.renter = user;
		this.checkOutDate = CheckOutDate;
		this.returnByDate = ReturnByDate;
	}
	public int verify() {
		if (book.getTitle()==null) {
			return 2; //book with that ISBN does not exist
		}
		book = bookDao.getFirstBook(book);//now b have inventory id and isbn and it is available
		if(book.getInventoryID() == null) {// book not available
			return 1; //book with that ISBN is not available
		}
		// change book status in database
		int changeStatus = bookDao.changeStatus(book, 0);
		if(changeStatus == 0) { //change status failed
			return 3; //an unspecified problem occurs
		}
		this.checkOutDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.returnByDate = LocalDateTime.now().plusDays(14).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		if (loanDao.add(this)==0) { //loan not successfully added to database
			return 3;
		}
		
		this.renter.addToRented(book); //adds this book to list of checked out books
		return 0;
	}
	
	public int endLoan() {
		int changeStatus = bookDao.changeStatus(book, 2); //set status of book to returned
		if(changeStatus == 0) { //change status failed
			return 1;
		}
		if (loanDao.end(this)==0) { //loan not successfully added to database
			return 1;
		}
		return 0;
	}
	
	public Book getBook() {
		return this.book;
	}
	public void setBook(Book book) {
		this.book=book;
	}
	
	public User getRenter() {
		return this.renter;
	}
	public void setRenter(User Renter) {
		this.renter=Renter;
	}
	
	public String getReturnDate() {
		return this.returnByDate;
	}
	public void setReturnDate(String ReturnByDate) {
		this.returnByDate=ReturnByDate;
	}
	
	public String getCheckOutDate() {
		return this.checkOutDate;
	}
	public void setCheckOutDate(String CheckOutDate) {
		this.checkOutDate=CheckOutDate;
	}
	
}
