package domain.loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.DbManager;
import domain.book.Book;
import domain.book.BookDao;
import domain.user.User;
import java.util.Date;  
import java.text.SimpleDateFormat;  
import java.text.DateFormat;
import java.text.ParseException;

public class LoanDaoImpl implements LoanDao{
	static Connection conn;
	static PreparedStatement ps;
	static DbManager db = new DbManager();
	ResultSet rs;
	
	public Loan find(User u, List<Book> books) {
		Loan l = new Loan();
		try{
			for (Book b : books) {
				conn = db.getConnection();
				ps = conn.prepareStatement("select CheckOutDate, ReturnByDate"
						                 + "From loan"
						                 + "Where loan.inventoryID = ?");
				ps.setString(1,b.getInventoryID());
				
				rs = ps.executeQuery();
				
				if((rs.next())&&(rs.getString("Renter") == u.getUsername())){
					l.setBook(b);
					l.setRenter(u);
					l.setCheckOutDate(rs.getString("CheckOutDate"));
					l.setReternDate(rs.getString("ReturnByDate"));
					conn.close();
					break;
				}
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return l;
	};
	
	public int add(Loan l) {
		try{
		
			conn = db.getConnection();
			ps = conn.prepareStatement("INSERT INTO loan (inventoryID,Renter,CheckOutDate,ReturnByDate)"
					                 + "VALUES (?,?,?,?)");
			ps.setString(1, l.getBook().getInventoryID());
			ps.setString(2, l.getRenter().getUsername());
			ps.setString(3, l.getCheckOutDate());
			ps.setString(4, l.getReternDate());
			
			int status = ps.executeUpdate();
			
			conn.close();
			
			return status;
		}catch(Exception e){
			System.out.println(e);
		}
		return 0;
	};
	
	public int end(Loan l) {
		try{
			
			conn = db.getConnection();
			ps = conn.prepareStatement("DELETE FROM loan "
					                 + "WHERE inventoryID = ?");
			ps.setInt(1, Integer.parseInt(l.getBook().getInventoryID()));
			
			int status = ps.executeUpdate();
			
			conn.close();
			return status;
		}catch(Exception e){
			System.out.println(e);
		}
		return 0;
		
	};
	
	public List<Book> getUserRentedBooks(User u){
		ResultSet rs;
		List<Book> books = new ArrayList<Book>();
		try{
			conn = db.getConnection();
			ps = conn.prepareStatement("select ISBN, Loan.inventoryID "
					+ "from Loan, collection "
					+ "where Loan.inventoryID = collection.inventoryID and Renter = ?");
			ps.setString(1, u.getUsername());
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Book book = new Book(rs.getString("ISBN"));
				book.setInventoryID(rs.getString("inventoryID"));
				books.add(book);
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		
		return books;
	}
	
	
	
	public String Check(User u, Book b) {
		return null;
	};
	
	
}
