package domain.loan;

import domain.book.*;

public class ReturnBook {
	
	public int returnBook(String ISBN) { //will eventually pass specific copy here
		// DG will change this function
		// need to return a specific copy
		// need to change to returned status instead of available
		int status=1;
		String available="1";
		
		book b = new book();
		BookDao bookDao = new BookDaoImpl();
		
		b = bookDao.getBookDetails(ISBN);
		available=b.getStatus();
		
		//call function to check if book is overdue and charge fees
		//checkBook();
		
		if(available.equals("0")) {//if book is available
			status=bookDao.changeStatus(ISBN, 1);
		}
		return status;
	}

	public int checkOverdue() {
		//checks to see if book is overdue
		return 0;
	}

}
