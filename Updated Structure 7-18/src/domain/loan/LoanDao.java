package domain.loan;

import java.util.List;
import java.util.Map;

import domain.book.*;
import domain.user.*;

public interface LoanDao {
	//public Loan find(User u, List<Book> books);
	
	public int add(Loan l);
	
	public int end(Loan l);
	
	public String Check(User u, Book b);
	
	public List<Book> getUserRentedBooks(User u);
	
	public Map<String, Book> rentedBooks(User u);
}
