package domain.manage;
import domain.book.*;

public class addBooks {
	
	

	public int add(Book b)
	{
		BookDao bookDao = new BookDaoImpl();
		int status=0;
		
		if (bookDao.titleExists(b.getISBN())==0)
			status=1;
			bookDao.addBook(b);
			
		return status;
	}
}
