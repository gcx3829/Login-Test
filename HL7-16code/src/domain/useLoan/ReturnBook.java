package domain.useLoan;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import domain.book.*;
import domain.loan.Loan;
import domain.loan.LoanDao;
import domain.loan.LoanDaoImpl;
import domain.user.User;

public class ReturnBook {
	
	public int returnBook(Book b, User u) { //will eventually pass specific copy here
		BookDao bookDao = new BookDaoImpl();
		LoanDao loanDao = new LoanDaoImpl();
		
		//First Check Book Existance in database
		if(bookDao.titleExists(b) == 0) {
			return 2;
		}
		
		
		//then check if user already rented that book
		boolean FindBook = false;
		for (Book temp : loanDao.getUserRentedBooks(u)) {
			if(temp.getISBN().equals(b.getISBN())) {
				b = temp;
				FindBook = true;
				break;
			}
		}
		
		if (!FindBook) return 0;
		
		//change status and end loan;
		// change book status in database
		int changeStatus = bookDao.changeStatus(b, 1);
		
		if(changeStatus == 0) { //change status failed
			return 3;
		}
		// add loan into database
		Loan l = new Loan(b, u);
		int loanStatus = loanDao.end(l);
		if(loanStatus == 0) { //change status failed
			return 3;
		}
		
		
		return 1;
	}

	public int checkOverdue(String ISBN, String username) {
		//checks to see if book is overdue
		BookDao bookDao = new BookDaoImpl();
		int status=0;
		
		int copyid = bookDao.findUserRentCopyID(ISBN, username);
		Book b = bookDao.getCopyDetails(ISBN, Integer.toString(copyid));
		b.getReturnByDate(); //get return by date into correct format and compare to time now using date.compare (date 2)
		// if date1 is after date 2,  then value >0
		// then change status to 1 and return it
		// in loan controller, have if statement will give statement outlining a fee to be paid if book is returned late
		
		return status;
	}

}
