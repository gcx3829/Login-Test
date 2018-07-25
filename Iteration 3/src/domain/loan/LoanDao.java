package domain.loan;

import java.util.List;
import java.util.Map;

import domain.book.*;
import domain.user.*;

public interface LoanDao {
	
	public int add(Loan l);
	
	public int end(Loan l);
	
	public List<Book> getUserRentedBooks(User u);
	
	public Map<String, Book> rentedBooks(User u);
	
	public int deleteLoan(Book book); // used to edit loans on specific book
	
	public int updateLoan(Loan loan);
}
