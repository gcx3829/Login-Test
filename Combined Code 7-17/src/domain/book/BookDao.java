package domain.book;

import java.util.List;

import domain.search.Search;

public interface BookDao {
	public int addBook(Book t);
	
	public List<Book> search(Search query);
	
	public List<Book> searchDetails(Search query);
	
	public Book getBook(Book book); // pass Book
	
	public Book getTitle(Book book);
	
	public int findStatus(Book book);
	
	public Book getFirstBook(Book book);
	
	public int changeStatus(Book book, int newStatus);
	
	public int updateBook(Book oldBook, Book newBook);
	
	public int titleExists(Book book);
	
	// ** CODE BELOW THIS NEEDS TO BE REPLACED AND DELETED
	
	public int findStatus(String ISBN);
	
	public Book getTitleDetails(String ISBN);
	
	public Book getCopyDetails(String ISBN, String CopyID);
	
	public List<Book> getTitleList();
	
	public int changeStatus(String ISBN, String CopyID, int newStatus);
	
	public int titleExists(String ISBN);
	
	public int copyExists(String ISBN, String CopyID); 
	
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
}
