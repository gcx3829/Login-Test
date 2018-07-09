package domain.manage;

import domain.book.*;
import domain.search.Search;

public class DeleteBooks {
	
	public int deleteTitle(String ISBN) { // new code
		// function to delete books already in database
		int status =0;
		BookDao bookDao = new BookDaoImpl();
		
		if (bookDao.titleExists(ISBN)==1) {
			status=1;
			bookDao.deleteTitles(ISBN);
		}
		return status;
	}
	
	public int deleteCopy(String ISBN, String CopyID) { // new code
		// function to delete books already in database
		int status =0;
		BookDao bookDao = new BookDaoImpl();
		
		if (bookDao.copyExists(ISBN, CopyID)==1) {
			status=1;
			bookDao.deleteCopy(ISBN, CopyID);
		}
		return status;
	}

}

