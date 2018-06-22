package domain.loan;

import domain.book.*;

public class CheckOut {
	
	public int checkout(String ISBN) {
		int status=0;
		String available="0";
		
		Title t = new Title();
		BookDao bookDao = new BookDaoImpl();
		
		t = bookDao.getBookDetails(ISBN);
		available=t.getStatus();
		
		//call function checkUser() to check for overdue books and possibly stop transaction for given user
		//int userStatus = checkUser();
		
		if(available.equals("1")) {//if book is available
			status=bookDao.changeStatus(ISBN, 0);
		}
		return status;
	}
	
	public int checkUser() {
		// checks how many overdue books user has
		return 0;
	}
}
