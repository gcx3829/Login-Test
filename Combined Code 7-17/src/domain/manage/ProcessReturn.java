package domain.manage;

import domain.book.*;
import domain.search.OldSearch;

public class ProcessReturn {
	
	public String processReturn(String InventoryID, int condition) { 
		BookDao bookDao = new BookDaoImpl();
		OldSearch s = new OldSearch();
		String message = "Book has not been returned";
		int status = 0;
		
		Book b = bookDao.getBook(new Book(InventoryID, "InventoryID"));
		if (b.getStatus()==null) {}
		else if(b.getStatus().compareTo("2")==0) {
			status = bookDao.changeStatus(b, condition);
		}else if(b.getStatus().compareTo("3")==0) {
			status = bookDao.changeStatus(b, condition);
		}
		/*
		if ((bookDao.copyExists(ISBN, s.getCopyID(ISBN, "2")))==1) {
			status = bookDao.changeStatus(ISBN, s.getCopyID(ISBN, "2"), 1);
		} else if ((bookDao.copyExists(ISBN, s.getCopyID(ISBN, "3")))==1) {
			status = bookDao.changeStatus(ISBN, s.getCopyID(ISBN, "3"), 1);
		}
		*/
		if ((status!=0) && (condition==1)) {
			message ="Book has been made available for Library patrons";
		} else if ((status!=0) && (condition==2)) {
			message = "Book has been sent for repair";
		}else if ((status!=0) && (condition==3)) {
			message="Book has been ruined and cannot be made available to the public";
		}
		return message;
	}

}

