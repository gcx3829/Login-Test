package domain.book;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.DbManager;

public class BookDaoImpl implements BookDao { 
	static Connection conn;
	static PreparedStatement ps;
	static DbManager db = new DbManager();
	
	//This function find the book availability based on ISBN, searching from collection table
	public int findStatus(String ISBN){       
		int status = 0;
		
		try {
			conn = db.getConnection();
			ps =conn.prepareStatement("select Status from collection where ISBN = ?");
			ps.setString(1, ISBN);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				if (rs.getString("Status").equals("1")) {
					status = 1;
					break;
				}
				
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);

		}
		return status;
	}
	
	public int findCurrentCopyNum(String ISBN){   // new function    
		int maxCopyID = 0;
		
		try {
			conn = db.getConnection();
			ps =conn.prepareStatement("select copyid from collection where ISBN = ?");
			ps.setString(1, ISBN);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				if (Integer.parseInt(rs.getString("CopyID")) > maxCopyID) {
					maxCopyID = Integer.parseInt(rs.getString("CopyID"));
				}
				
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);

		}
		return maxCopyID;
	}
	
	public int addBook(book t) {
		
		try{
			// if book is not in titlelist table, add it into titlelist and collection
			if (findStatus(t.getISBN()) == 0) { //use find status to see if book is in the database
				conn = db.getConnection();
				
				Statement statement = conn.createStatement();
				String query = "insert into titlelist values("+t.getISBN()+","+t.getTitle()+","
						+ ""+t.getAuthor()+","+t.getCategory()+","+t.getEdition()+",1)";
				
				statement.addBatch(query);
				
				query = "insert into collection values("+t.getISBN()+",1,1,null,null,null)"; //add new title as copy 1
				statement.addBatch(query);
				
				statement.executeBatch();
				statement.close();
				conn.close();
			}else { // if title already exists; just add a new copy
				int currentCopyID = findCurrentCopyNum(t.getISBN()); // find number of copies
				currentCopyID ++;
				conn = db.getConnection();
				ps =conn.prepareStatement("insert into collection values(?,?,1,null,null,null)");
				ps.setString(1, t.getISBN());
				ps.setInt(2, currentCopyID);
				ps.executeUpdate();
				conn.close();
			}
			
			return 1;
			
			

		}catch(Exception e){
			System.out.println(e);
			return 0;

		}
	}
	
	public book getBookDetails(String ISBN) { // OLD CODE
		// returns specific book; can check out this book
		book t = new book();
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select ISBN, Title, Author, Status from titlelist where ISBN = ?");
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
	
	public List<book> getTitleList() {
		//returns all titles
		//will eventually be changed to work better with proper search function
		ResultSet rs;
		List<book> books = new ArrayList<book>();
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select ISBN, Title, Author, Status from titlelist");
			rs = ps.executeQuery();
			while (rs.next()) {
				String statusText;
				//code to find the status of the books
				if (findStatus(rs.getString(1)) == 1) { //code to turn int for status into string
					statusText = "Available";
				}
				else {
					statusText = "Unavailable";
				}
				book book = new book();
				book.setISBN(rs.getString(1));
				book.setTitle(rs.getString(2));
				book.setAuthor(rs.getString(3));
				book.setStatus(statusText);
				books.add(book);
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
			rs = null;
		}
		return books;		
	}
	
	public int changeStatus(String ISBN, int newStatus) { // will be changed to change status of copy
		// returns specific book; can check out this book
		int status=0;
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("update titlelist set status = ? where ISBN = ?");
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
