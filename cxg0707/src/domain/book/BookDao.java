package domain.book;

import java.util.List;

public interface BookDao {
	public int addBook(Book t);
	
	public Book getBookDetails(String ISBN);
	
	public List<Book> getTitleList();
	
	public int changeStatus(String ISBN, int newStatus);
	
	public int bookExists(String ISBN); // NEW FUNCTION
	
	public void editTitleFields(String Field, String ISBN, String value);
	
	public void deleteBooks(String ISBN);
	
	public int findStatus(String ISBN);
	
	public int findCurrentCopyNum(String ISBN);
	
	public List<Book> SearchTitleFields(String field, String value);
}
