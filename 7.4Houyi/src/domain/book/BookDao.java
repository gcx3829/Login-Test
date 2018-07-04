package domain.book;

import java.util.List;

public interface BookDao {
	public int addBook(book t);
	
	public int findStatus(String ISBN);
	
	public int findCurrentCopyNum(String ISBN);
	
	public book getBookDetails(String ISBN);
	
	public List<book> getTitleList();
	
	public int changeStatus(String ISBN, int newStatus);
}
