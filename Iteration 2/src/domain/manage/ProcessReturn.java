package domain.manage;

import domain.book.*;
import domain.search.Search;

public class ProcessReturn {
	
	public String processReturn(String ISBN, int condition) { 
		BookDao bookDao = new BookDaoImpl();
		Search s = new Search();
		String message = "Book has not been returned";
		int status = 0;
		
		if ((bookDao.copyExists(ISBN, s.getCopyID(ISBN, "2")))==1) {
			status = bookDao.changeStatus(ISBN, s.getCopyID(ISBN, "2"), 1);
		} else if ((bookDao.copyExists(ISBN, s.getCopyID(ISBN, "3")))==1) {
			status = bookDao.changeStatus(ISBN, s.getCopyID(ISBN, "3"), 1);
		}
		
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

