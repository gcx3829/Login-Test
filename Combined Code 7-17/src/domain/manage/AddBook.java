package domain.manage;
import domain.book.*;

public class AddBook {
	
	public String add(Book b)
	{
		BookDao bookDao = new BookDaoImpl();
		int status=0;
		String message="";
		
		if (bookDao.titleExists(b.getISBN())==0) {
			status=1;
		}
		bookDao.addBook(b);
		
		if(status == 1) {
			message = "Added book '" + b.getTitle() + "' successfully!";
		}else {
			message = "Added a new Copy of '" + b.getTitle() + "' successfully!";
		}
			
		return message;
	}
}