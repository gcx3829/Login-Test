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
	
	public int checkout(Book b, User u) {//input parameter only have ISBN
		
		BookDao bookDao = new BookDaoImpl();
		LoanDao loanDao = new LoanDaoImpl();
		
		//First Check Book Existance in database
		if(bookDao.titleExists(b) == 0) {
			return 2;
		}
		
	    //then check if user already rented that book	
		for (Book temp : loanDao.getUserRentedBooks(u)) {
			if(temp.getISBN().equals(b.getISBN())) {
				return 4;
			}
		}
		
		b = bookDao.getFirstBook(b);//now b have inventory id and isbn and it is available
		
		if(b.getInventoryID() == null) {// book not available
			return 0;
		}
		
		// change book status in database
		int changeStatus = bookDao.changeStatus(b, 0);
		
		if(changeStatus == 0) { //change status failed
			return 3;
		}
		
		//get current time and return time
		 
		String CurrentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String ReturnDateTime = LocalDateTime.now().plusDays(14).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		// initialize loan class
		Loan l = new Loan(b, u, CurrentDateTime, ReturnDateTime);
		// add loan into database
		int loanStatus = loanDao.add(l);
		if(loanStatus == 0) { //change status failed
			return 3;
		}
		
		
		return 1;
	}
	
	public int checkUser() {
		// checks how many overdue books user has
		return 0;
	}
}
