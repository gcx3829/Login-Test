package domain.loan;

import java.util.Date;

import domain.book.Book;
import domain.user.User;

public class Loan {
	private String ReturnByDate;
	private String CheckOutDate;
	private Book book;
	private User Renter;
	
	public Loan() {
		
	}
	public Loan(Book book, User user) {
		this.book = book;
		this.Renter = user;
	}
	
	public Loan(Book book, User user, String CheckOutDate, String ReturnByDate) {
		this.book = book;
		this.Renter = user;
		this.CheckOutDate = CheckOutDate;
		this.ReturnByDate = ReturnByDate;
	}
	
	public Book getBook() {
		return this.book;
	}
	public void setBook(Book book) {
		this.book=book;
	}
	
	public User getRenter() {
		return this.Renter;
	}
	public void setRenter(User Renter) {
		this.Renter=Renter;
	}
	
	public String getReternDate() {
		return this.ReturnByDate;
	}
	public void setReternDate(String ReturnByDate) {
		this.ReturnByDate=ReturnByDate;
	}
	
	public String getCheckOutDate() {
		return this.CheckOutDate;
	}
	public void setCheckOutDate(String CheckOutDate) {
		this.CheckOutDate=CheckOutDate;
	}
	
}
