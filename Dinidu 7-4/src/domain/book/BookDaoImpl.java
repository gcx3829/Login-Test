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
	
	@Override
	public int addBook(Title t) {
		int status = 0;
		
		try{
			// code to check if book is already in database
			conn = db.getConnection();
			ps =conn.prepareStatement("insert into TitleList values(?,?,?,?,?,?)");
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
	
	@Override
	public Title getBookDetails(String ISBN) {
		// returns specific book; can check out this book
		Title t = new Title();
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select ISBN, Title, Author, Status from TitleList where ISBN = ?");
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
	
	@Override
	public List<Title> getTitleList() {
		//returns all titles
		//will eventually be changed to work better with proper search function
		ResultSet rs;
		List<Title> titles = new ArrayList<Title>();
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select ISBN, Title, Author, Status from TitleList");
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
	
	@Override
	public int changeStatus(String ISBN, int newStatus) { // will be changed to change status of copy
		// returns specific book; can check out this book
		int status=0;
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("update TitleList set status = ? where ISBN = ?");
			ps.setString(2, ISBN);
			ps.setInt(1, newStatus);
			status = ps.executeUpdate();
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}	
		return status;
	}
	
	@Override
	public int bookExists(String ISBN) {
		// checks if book exists
		int status = 0;
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select Title from TitleList where ISBN = ?");
			ps.setString(1, ISBN);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				status = 1; //book exists
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return status;		
	}
	
	@Override
	public int changeBookDetails(Title newDetails, String ISBN) // NEW FUNCTION
	{
		//
		try{
			conn = db.getConnection();
			if (newDetails.getISBN().length()>0) {
				ps =conn.prepareStatement("update TitleList set ISBN = ? where ISBN = ?");
				ps.setString(2, ISBN);
				ps.setString(1, newDetails.getISBN());
				ps.executeUpdate();
				
				// will need extra code here for copy/collection/book table
				/*
				ps =conn.prepareStatement("update Book set ISBN = ? where ISBN = ?");
				ps.setString(2, ISBN);
				ps.setString(1, newDetails.getISBN());
				ps.executeUpdate();
				 * 
				 */
			}
			if (newDetails.getTitle().length()>0) {
				ps =conn.prepareStatement("update TitleList set Title = ? where ISBN = ?");
				ps.setString(2, ISBN);
				ps.setString(1, newDetails.getTitle());
				ps.executeUpdate();
			}
			
			if (newDetails.getAuthor().length()>0) {
				ps =conn.prepareStatement("update TitleList set Author = ? where ISBN = ?");
				ps.setString(2, ISBN);
				ps.setString(1, newDetails.getAuthor());
				ps.executeUpdate();
			}
			
			if (newDetails.getCategory().length()>0) {
				ps =conn.prepareStatement("update TitleList set Category = ? where ISBN = ?");
				ps.setString(2, ISBN);
				ps.setString(1, newDetails.getCategory());
				ps.executeUpdate();
			}
			
			if (newDetails.getEdition().length()>0) {
				ps =conn.prepareStatement("update TitleList set Edition = ? where ISBN = ?");
				ps.setString(2, ISBN);
				ps.setString(1, newDetails.getEdition());
				ps.executeUpdate();
			}
			
			if (newDetails.getStatus().length()>0) { // code will be outdated with new table structure
				ps =conn.prepareStatement("update TitleList set Status = ? where ISBN = ?");
				ps.setString(2, ISBN);
				ps.setString(1, newDetails.getStatus());
				ps.executeUpdate();
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}	
		return 0;
	}
	
	public void editTitleFields(String field, String ISBN, String value) // NEW FUNCTION
	{
		//
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("update TitleList set " + field + " = ? where ISBN = ?");
			ps.setString(1, value);
			ps.setString(2, ISBN);
			ps.executeUpdate();
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}	
	}
	
	public void deleteBooks(String ISBN) {
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("delete from TitleList where ISBN = ?");
			ps.setString(1, ISBN);
			ps.executeUpdate();
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		
	}

}
