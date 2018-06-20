package collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import db.DbManager;

public class LibraryImpl {
	static Connection conn;
	static PreparedStatement ps;
	static DbManager db = new DbManager();	
	/*
	public void displayCollection() {
		// displays entire collection and all details
	}
	
	public void Search() {
		//returns display of books that match search criteria
		// not implemented yet
	}
	*/
	public static int checkout(collection col) {
		//check out function
		//run SQL query
		int status_available = 0;
		int status = 0;
		
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select Status from Booklist where ISBN = ?");
			ps.setString(1, col.getISBN());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				status_available = rs.getInt(1);
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		
		if(status_available==1) {
			try{
				conn = db.getConnection();
				/*
				ps =conn.prepareStatement("insert into Collection values(?,?,?,?,?,?)");
				ps.setString(1, col.getISBN());
				ps.setString(2, "1");
				ps.setString(3, "1");
				ps.setString(4, col.getRentedBy());
				ps.setString(5, "2018-06-15 00:00:00");
				ps.setString(6, "2018-06-20 12:00:00");
				*/
				//function using collection class
				ps =conn.prepareStatement("update Booklist set status = 0 where ISBN = ?");
				ps.setString(1, col.getISBN());
				status = ps.executeUpdate();
				conn.close();
				//check out function by changing status 
			}catch(Exception e){
				System.out.println(e);
			}
			return status;
		}else {
			return 0;
		}
	}
	
	public static Book getBook() {
		// returns specific book; can check out this book
		Book b = new Book();
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select * from Booklist");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				b.setISBN(rs.getString(1));
				b.setTitle(rs.getString(2));
				b.setAuthor(rs.getString(3));
				b.setStatus(rs.getString(4));
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return b;		
	}
}