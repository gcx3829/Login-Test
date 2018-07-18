package domain.manage;

import domain.book.*;
import domain.search.OldSearch;

public class ProcessReturn {
	
	public String processReturn(String InventoryID, int condition) { 
		BookDao bookDao = new BookDaoImpl();
		String message = "Book has not been returned";
		int success = 0;
		int status = bookDao.returnStatus(InventoryID);
		
		if ((status==2)||(status==3)||(status==4)) {
			success = bookDao.changeStatus(InventoryID, condition);
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

