package domain.book;

import java.util.List;

public interface BookDao {
	public int addBook(Book t);
	
	public Book getTitleDetails(String ISBN);
	
	public Book getCopyDetails(String ISBN, String CopyID);
	
	public List<Book> getTitleList();
	
	public int changeStatus(String ISBN, String CopyID, int newStatus);
	
	public int titleExists(String ISBN); // NEW FUNCTION
	
	public int copyExists(String ISBN, String CopyID); // NEW FUNCTION
	
	public void editTitleFields(String Field, String ISBN, String value);
	
	public void editCopyFields(String field, String ISBN, String CopyID, String value);
	
	public void deleteTitles(String ISBN);
	
	public void deleteCopy(String ISBN, String CopyID);
	
	public int findStatus(String ISBN);
	
	public int findCurrentAvailCopyNum(String ISBN);
	
	public int findUserRentCopyID(String ISBN, String Userid);
	
	public int checkUserRent(String ISBN, String Userid);
	
	public int findCurrentCopyNum(String ISBN);
	
	public List<Book> SearchTitleFields(String field, String value);
	
	public List<Book> SearchCopyFields(String field, String value);
	
	public List<Book> getAllBooks();
}
