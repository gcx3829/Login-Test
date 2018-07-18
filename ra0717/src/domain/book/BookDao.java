package domain.book;

import java.util.List;

import domain.search.Search;

public interface BookDao {
	public int addBook(Book t);
	
	public List<Book> search(Search query);
	
	public List<Book> searchDetails(Search query);
	
	public Book getBook(Book book); // pass Book
	
	public int findStatus(Book book);
	
	// ** CODE BELOW THIS NEEDS TO BE REPLACED AND DELETED
	
	public int findStatus(String ISBN);
	
	public Book getTitleDetails(String ISBN);
	
	public Book getCopyDetails(String ISBN, String CopyID);
	
	public List<Book> getTitleList();
	
	public int changeStatus(String InventoryID, int newStatus);
	
	public int titleExists(String ISBN);
	
	public int copyExists(String InventoryID); 
	
	public void editTitleFields(String Field, String ISBN, String value);
	
	public void editCopyFields(String field, String ISBN, String CopyID, String value);
	
	public void deleteTitles(String ISBN);
	
	public void deleteCopy(String ISBN, String CopyID);
	
	public int findCurrentAvailCopyNum(String ISBN);
	
	public int findUserRentCopyID(String ISBN, String Userid);
	
	public int checkUserRent(String ISBN, String Userid);
	
	public int findCurrentCopyNum(String ISBN);
	
	public List<Book> SearchTitleFields(String field, String value);
	
	public List<Book> SearchCopyFields(String field, String value);
	
	public List<Book> getAllBooks();
	
	public int returnStatus(String InventoryID);
	
	public int update(Book book, String oldISBN);
}
