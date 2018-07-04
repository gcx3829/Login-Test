package domain.manage;

import domain.book.*;

public class ManageBooks {
	
	public int editBook(Title t, String ISBN) { // new code
		BookDao bookDao = new BookDaoImpl();
		int status=0;
		
		
		if (bookDao.bookExists(ISBN)==1) {
			status=1;
			
			//
			
			if (t.getISBN().length()>0) {
				bookDao.editTitleFields("ISBN", ISBN, t.getISBN());
			}
			
			if (t.getTitle().length()>0) {
				bookDao.editTitleFields("Title", ISBN, t.getTitle());
			}
			
			if (t.getAuthor().length()>0) {
				bookDao.editTitleFields("Author", ISBN, t.getAuthor());
			}
			
			if (t.getCategory().length()>0) {
				bookDao.editTitleFields("Category", ISBN, t.getCategory());
			}
			
			if (t.getEdition().length()>0) {
				bookDao.editTitleFields("Edition", ISBN, t.getEdition());
			}
			
			if (t.getStatus().length()>0) {
				bookDao.changeStatus(ISBN, Integer.parseInt(t.getStatus()));
				//bookDao.editTitleFields("Status", ISBN, t.getStatus());
			}
			
			
		}
		return status;
	}
	
	public int deleteBook(String ISBN) { // new code
		// function to delete books already in database
		int status =0;
		BookDao bookDao = new BookDaoImpl();
		
		if (bookDao.bookExists(ISBN)==1) {
			status=1;
			bookDao.deleteBooks(ISBN);
		}
		return status;
	}
	
	public int processReturn(String ISBN, int newStatus) { 
		// will also pass info to identify copy
		// function to process returned books
		
		// check if book status is 'checked out' or 'returned'
		
		//change status according to condition of book
		//status=titleDao.changeStatus(ISBN, newStatus);
		return 0;
	}

}

