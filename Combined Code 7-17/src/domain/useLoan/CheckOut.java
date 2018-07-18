package domain.useLoan;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.time.format.DateTimeFormatter;

import domain.book.*;
import domain.user.*;
import domain.loan.*;

public class CheckOut {
	
	public String checkout(Book b, User u) {//input parameter only have ISBN
		
		BookDao bookDao = new BookDaoImpl();
		LoanDao loanDao = new LoanDaoImpl();
		String message = "";
		
		//First Check Book Existance in database
		if(bookDao.getTitle(b).getTitle() == null) {
			message = "book with ISBN "+ b.getISBN() + " not found in database";
			return message; //return 2;
		}
		Book title = bookDao.getTitle(b);
	    //then check if user already rented that book	
		for (Book temp : loanDao.getUserRentedBooks(u)) {
			if(temp.getISBN().equals(b.getISBN())) {
				message = "You already rented book " +title.getTitle()+" !!! Check out failed";
				return message; //return 4;
			}
		}
		
		b = bookDao.getFirstBook(b);//now b have inventory id and isbn and it is available
		
		if(b.getInventoryID() == null) {// book not available
			message = "Book " + title.getTitle() + " is not available now! Please select another one!";
			return message; //return 0;
		}
		
		// change book status in database
		int changeStatus = bookDao.changeStatus(b, 0);
		
		if(changeStatus == 0) { //change status failed
			message = "Book "+ b.getTitle() + " Check out failed!!";
			return message; //return 3;
		}
		
		//get current time and return time
		 
		String CurrentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String ReturnDateTime = LocalDateTime.now().plusDays(14).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		// initialize loan class
		Loan l = new Loan(b, u, CurrentDateTime, ReturnDateTime);
		// add loan into database
		int loanStatus = loanDao.add(l);
		if(loanStatus == 0) { //change status failed
			message = "Book "+ b.getTitle() + " Check out failed!!";
			return message; //return 3;
		}
		
		message = "Book "+ b.getTitle() + " checked out successfully!";
		return message; //return 1;
	}
	
	public int checkUser() {
		// checks how many overdue books user has
		return 0;
	}
}
