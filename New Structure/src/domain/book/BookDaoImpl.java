package domain.book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.DbManager;

public class BookDaoImpl implements BookDao {
	static Connection conn;
	static PreparedStatement ps;
	static DbManager db = new DbManager();
	
	public int addBook(Title t) {
		int status = 0;
		
		try{
			// code to check if book is already in database
			conn = db.getConnection();
			ps =conn.prepareStatement("insert into booklist values(?,?,?,?,?,?)");
			ps.setString(1, t.getISBN());
			ps.setString(2, t.getTitle());
			ps.setString(3, t.getAuthor());
			ps.setString(4, t.getCategory());
			ps.setString(5, t.getEdition());
			ps.setInt(6, 1);
			status = ps.executeUpdate();
			
			// code to call collection database and insert new copy into database
			
			conn.close();

		}catch(Exception e){
			System.out.println(e);

		}
		return status;
	}
	
	public Title getBookDetails(String ISBN) {
		// returns specific book; can check out this book
		Title t = new Title();
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select ISBN, Title, Author, Status from Booklist where ISBN = ?");
			ps.setString(1, ISBN);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				t.setISBN(rs.getString(1));
				t.setTitle(rs.getString(2));
				t.setAuthor(rs.getString(3));
				t.setStatus(rs.getString(4));
				
				// call function here to get all book copies
				// count number of available copies from this function call
				// this will eventually replace status
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return t;		
	}
	
	public List<Title> getTitleList() {
		//returns all titles
		//will eventually be changed to work better with proper search function
		ResultSet rs;
		List<Title> titles = new ArrayList<Title>();
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select ISBN, Title, Author, Status from Booklist");
			rs = ps.executeQuery();
			while (rs.next()) {
				String statusText;
				//code to find the status of the books
				if (rs.getString(4).equals("1")) { //code to turn int for status into string
					statusText = "Available";
				}
				else {
					statusText = "Unavailable";
				}
				Title title = new Title();
				title.setISBN(rs.getString(1));
				title.setTitle(rs.getString(2));
				title.setAuthor(rs.getString(3));
				title.setStatus(statusText);
				titles.add(title);
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
			rs = null;
		}
		return titles;		
	}
	
	public int changeStatus(String ISBN, int newStatus) { // will be changed to change status of copy
		// returns specific book; can check out this book
		int status=0;
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("update Booklist set status = ? where ISBN = ?");
			ps.setString(2, ISBN);
			ps.setInt(1, newStatus);
			status = ps.executeUpdate();
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}	
		return status;
	}

}
