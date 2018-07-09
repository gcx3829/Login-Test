package domain.loan;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import domain.book.*;

public class ReturnBook {
	
	public int returnBook(String ISBN, String Userid) { //will eventually pass specific copy here
		int status=1;
		int copyid= 0;
		
		BookDao bookDao = new BookDaoImpl();
		
		//if book does not exist in database
		if (bookDao.titleExists(ISBN) == 0) {
			status = 2;
			System.out.println("Book does not exist!");
			return status;
		}
		
		copyid = bookDao.findUserRentCopyID(ISBN, Userid);
		
		//no copy id found
		
		if(copyid == 0) {
			status = 0;
			System.out.println("You did not rent that book!");
			return status;
		}
		
		int temp = bookDao.changeStatus(ISBN, Integer.toString(copyid), 2);
		if (temp==1) {
			bookDao.editCopyFields("RentedBy", ISBN, Integer.toString(copyid), null);
			bookDao.editCopyFields("CheckOutDate", ISBN, Integer.toString(copyid), null);
			bookDao.editCopyFields("ReturnByDate", ISBN, Integer.toString(copyid), null);
		} else {
			status = 3;
			System.out.println("Return Book fail!!");
			return status;
		}
		//if(bookDao.changeStatus_Withcopyid(ISBN, copyid, Userid, 2) == 0) {
		//	status = 3;
		//	System.out.println("Check out fail!!");
		//	return status;
		//}
		
		status = 1;
		
		return status;
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
