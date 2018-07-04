package domain.book;

import java.util.List;

public interface BookDao {
	public int addBook(Title t);
	
	public Title getBookDetails(String ISBN);
	
	public List<Title> getTitleList();
	
	public int changeStatus(String ISBN, int newStatus);
	
	public int bookExists(String ISBN); // NEW FUNCTION
	
	public int changeBookDetails(Title newDetails, String ISBN); // NEW FUNCTION
	
	public void editTitleFields(String Field, String ISBN, String value);
	
	public void deleteBooks(String ISBN);
}
