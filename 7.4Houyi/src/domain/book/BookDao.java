package domain.book;

import java.util.List;

public interface BookDao {
	public int addBook(Title t);
	
	public int findStatus(String ISBN);
	
	public int findCurrentCopyNum(String ISBN);
	
	public Title getBookDetails(String ISBN);
	
	public List<Title> getTitleList();
	
	public int changeStatus(String ISBN, int newStatus);
}
