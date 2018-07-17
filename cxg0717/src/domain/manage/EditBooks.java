package domain.manage;

import domain.book.*;
import domain.search.OldSearch;

public class EditBooks {
	
	public int editTitle(Book b, String ISBN) { // new code
		BookDao bookDao = new BookDaoImpl();
		int status=0;
		
		
		if (bookDao.titleExists(ISBN)==1) {
			status=1;
			
			//
			if (b.getISBN().length()>0) {
				bookDao.editTitleFields("ISBN", ISBN, b.getISBN());
				int max = bookDao.findCurrentCopyNum(ISBN);
				for (int i=1; i<=max; i++) {
					bookDao.editCopyFields("ISBN", ISBN, Integer.toString(i), b.getISBN());
				}
				ISBN = b.getISBN();
			}
			
			if (b.getTitle().length()>0) {
				bookDao.editTitleFields("Title", ISBN, b.getTitle());
			}
			
			if (b.getAuthor().length()>0) {
				bookDao.editTitleFields("Author", ISBN, b.getAuthor());
			}
			
			if (b.getGenre().length()>0) {
				bookDao.editTitleFields("Category", ISBN, b.getGenre());
			}
			
			if (b.getEdition().length()>0) {
				bookDao.editTitleFields("Edition", ISBN, b.getEdition());
			}
		}
		return status;
	}
	
	public int editCopy(Book b, String ISBN, String CopyID) { // new code
		BookDao bookDao = new BookDaoImpl();
		int status=0;
		
		if (bookDao.copyExists(CopyID)==1) {
			status=1;
		
			if (b.getISBN().length()>0) {
				bookDao.editCopyFields("ISBN", ISBN, CopyID, b.getISBN());
				ISBN = b.getISBN();
			}
			
			if (b.getInventoryID().length()>0) {
				bookDao.editCopyFields("CopyID", ISBN, CopyID, b.getInventoryID());
				CopyID = b.getInventoryID();
			}
			
			if (b.getRentedBy().length()>0) {
				bookDao.editTitleFields("RentedBy", ISBN, b.getRentedBy());
			}
			
			if (b.getCheckOutDate().length()>0) {
				bookDao.editTitleFields("CheckOutDate", ISBN, b.getCheckOutDate());
			}
			
			if (b.getReturnByDate().length()>0) {
				bookDao.editTitleFields("ReturnByDate", ISBN, b.getReturnByDate());
			}
			
			if (b.getStatus().length()>0) {
				bookDao.changeStatus(CopyID, Integer.parseInt(b.getStatus()));
				//bookDao.editTitleFields("Status", ISBN, t.getStatus());
			}
			
			
		}
		return status;
	}
	
}

