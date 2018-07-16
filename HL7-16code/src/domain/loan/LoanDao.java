package domain.loan;

import java.util.List;

import domain.book.*;
import domain.user.*;
import java.util.Date;  
import java.text.SimpleDateFormat; 

public interface LoanDao {
	public Loan find(User u, List<Book> books);
	
	public int add(Loan l);
	
	public int end(Loan l);
	
	public String Check(User u, Book b);
	
	public List<Book> getUserRentedBooks(User u);
	
}
