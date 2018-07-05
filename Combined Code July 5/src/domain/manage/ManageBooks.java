package domain.manage;

import domain.book.*;

public class ManageBooks {
	
	public int editBook(Book b, String ISBN) { // new code
		BookDao bookDao = new BookDaoImpl();
		int status=0;
		
		
		if (bookDao.bookExists(ISBN)==1) {
			status=1;
			
			//
			
			if (b.getISBN().length()>0) {
				bookDao.editTitleFields("ISBN", ISBN, b.getISBN());
			}
			
			if (b.getTitle().length()>0) {
				bookDao.editTitleFields("Title", ISBN, b.getTitle());
			}
			
			if (b.getAuthor().length()>0) {
				bookDao.editTitleFields("Author", ISBN, b.getAuthor());
			}
			
			if (b.getCategory().length()>0) {
				bookDao.editTitleFields("Category", ISBN, b.getCategory());
			}
			
			if (b.getEdition().length()>0) {
				bookDao.editTitleFields("Edition", ISBN, b.getEdition());
			}
			
			if (b.getCopyID().length()>0) {
				bookDao.editTitleFields("Edition", ISBN, b.getEdition());
			}
			
			if (b.getRentedBy().length()>0) {
				bookDao.editTitleFields("Edition", ISBN, b.getEdition());
			}
			
			if (b.getCheckOutDate().length()>0) {
				bookDao.editTitleFields("Edition", ISBN, b.getEdition());
			}
			
			if (b.getReturnByDate().length()>0) {
				bookDao.editTitleFields("Edition", ISBN, b.getEdition());
			}
			
			if (b.getStatus().length()>0) {
				bookDao.changeStatus(ISBN, Integer.parseInt(b.getStatus()));
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

