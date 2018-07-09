package domain.loan;

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
		
		if(bookDao.changeStatus_Withcopyid(ISBN, copyid, Userid, 2) == 0) {
			status = 3;
			System.out.println("Check out fail!!");
			return status;
		}
		
		status = 1;
		
		return status;
	}

	public int checkOverdue() {
		//checks to see if book is overdue
		return 0;
	}

}
