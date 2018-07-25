package domain.book;

import java.util.List;

import domain.search.Search;

public interface BookDao {
	public String addNewBook(Book b);
	
	public int addTitle(Book b);
	
	public List<Book> search(Search query);
	
	public List<Book> searchDetails(Search query);

	public Book getBook(String inventoryID);
	
	public Book getTitle(String ISBN);
	
	public int findStatus(String ISBN);	
	
	public int updateBook(Book newBook);
	
	public int updateTitle(String oldISBN, Book newBook);
	
	public int updateBookDetail(Book book);
	
	public int largestInventoryID();
	
	public Book getFirstBook(Book book);
	
	public int changeStatus(Book book, int newStatus);
}
