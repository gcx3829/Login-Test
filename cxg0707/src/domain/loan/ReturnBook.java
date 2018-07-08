package domain.loan;

import domain.book.*;

public class ReturnBook {
	
	public int returnBook(String ISBN) { //will eventually pass specific copy here
		int status=1;
		String available="1";
		
		Book b = new Book();
		BookDao bookDao = new BookDaoImpl();
		
		b = bookDao.getBookDetails(ISBN);
		available=b.getStatus();
		
		//call function to check if book is overdue and charge fees
		//checkBook();
		
		if(available.equals("0")) {//if book is checked out
			status=bookDao.changeStatus(ISBN, 2);
		}
		return status;
	}

	public int checkOverdue() {
		//checks to see if book is overdue
		return 0;
	}

}
