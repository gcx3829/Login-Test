package domain.loan;

import domain.book.*;

public class CheckOut {
	
	public int checkout(String ISBN, String Userid) {
		int status=0;
		int copyid= 0;
		
		BookDao bookDao = new BookDaoImpl();
		
		//if book does not exist in database
		if (bookDao.titleExists(ISBN) == 0) {
			status = 2;
			System.out.println("Book does not exist!");
			return status;
		}
		
		copyid = bookDao.findCurrentAvailCopyNum(ISBN);
		
		//call function checkUser() to check for overdue books and possibly stop transaction for given user
		//int userStatus = checkUser();
		
		if(copyid == 0) {
			status = 0;
			System.out.println("Book not available!");
			return status;
		}
		
		//Check user have already rent that book or not
		if(bookDao.checkUserRent(ISBN, Userid) == 1) {
			status = 4;
			System.out.println("You already have that book!!!");
			return status;
		}
		
		if(bookDao.changeStatus_Withcopyid(ISBN, copyid,Userid, 0) == 0) {
			status = 3;
			System.out.println("Check out fail!!");
			return status;
		}
		
		status = 1;
		
		return status;
	}
	
	public int checkUser() {
		// checks how many overdue books user has
		return 0;
	}
}
