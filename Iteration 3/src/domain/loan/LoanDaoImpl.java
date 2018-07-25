package domain.loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import db.DbManager;
import domain.book.Book;
import domain.user.User;
import java.util.HashMap;

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
					l.setReturnDate(rs.getString("ReturnByDate"));
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
			ps.setString(4, l.getReturnDate());
			
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
				Book book = new Book(rs.getString("ISBN"), "ISBN");
				book.setInventoryID(rs.getString("inventoryID"));
				books.add(book);
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		
		return books;
	}
	
	public Map<String, Book> rentedBooks(User u) {
		ResultSet rs;
		Map<String, Book> books = new HashMap<String, Book>();
		try{
			conn = db.getConnection();
			ps = conn.prepareStatement("select titlelist.ISBN, titlelist.Title, titlelist.Author, titlelist.Edition,"
					+ " titlelist.Genre, collection.InventoryID, collection.Status, loan.Renter,"
					+ " loan.CheckOutDate, loan.ReturnByDate from titlelist INNER JOIN collection" 
					+ " ON collection.ISBN = titlelist.ISBN LEFT OUTER JOIN loan ON collection.inventoryID ="
					+ " loan.inventoryID where loan.Renter = ?");

			ps.setString(1,u.getUsername());
			rs = ps.executeQuery();
			while (rs.next()) {
				Book book = new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4));
				book.setISBN(rs.getString(1));
				book.setTitle(rs.getString(2));
				book.setAuthor(rs.getString(3));
				book.setEdition(rs.getString(4));
				book.setGenre(rs.getString(5));
				book.setInventoryID(rs.getString(6));
				book.setStatus(rs.getString(7));
				book.setRentedBy(rs.getString(8));
				book.setCheckOutDate(rs.getString(9));
				book.setReturnByDate(rs.getString(10));
				//books.add(book);
				books.put(book.getISBN(), book);
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		
		return books;
	}
	
	public int deleteLoan(Book b) {
		int status;
		try {
			ps = conn.prepareStatement("delete from loan where inventoryID = ?");
			ps.setString(1, b.getInventoryID());
			status = ps.executeUpdate();
		}
		catch(Exception e){
			System.out.println(e);
			return 2;
		}
		return status;
	}
	
	public int updateLoan(Loan l) {
		int temp =0;
		try {
			ps = conn.prepareStatement("select * from loan where inventoryID = ?");
			ps.setString(1, l.getBook().getInventoryID());
			ResultSet rs_2 = ps.executeQuery();
			while (rs_2.next()) {
				temp = 1;
			}
			if (temp == 1) {
				//LOAN INFO EXISTS, UPDATE IT
				ps = conn.prepareStatement("update loan set Renter = ?, CheckOutDate = ?, ReturnByDate = ? where inventoryID = ?");
				ps.setString(1, l.getRenter().getUsername());
				ps.setString(2, l.getCheckOutDate());
				ps.setString(3, l.getReturnDate());
				ps.setString(4, l.getBook().getInventoryID());
			}else {
				//LOAN INFO NOT EXISTS, ADD NEW ONE
				ps = conn.prepareStatement("insert into loan values(?,?,?,?)");	
				ps.setString(1, l.getBook().getInventoryID());
				ps.setString(2, l.getRenter().getUsername());
				ps.setString(3, l.getCheckOutDate());
				ps.setString(4, l.getReturnDate());
			}
			int rs3 = ps.executeUpdate();
			return rs3;
		}catch(Exception e){
			System.out.println(e);
			return 2;
		}
	}

}
