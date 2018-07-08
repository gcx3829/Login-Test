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
	
	@Override
public int addBook(Book t) {
		
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
	
	@Override
	public Book getBookDetails(String ISBN) {
		// returns specific book; can check out this book
		Book b = new Book();
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select ISBN, Title, Author, Status from TitleList where ISBN = ?");
			ps.setString(1, ISBN);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				b.setISBN(rs.getString(1));
				b.setTitle(rs.getString(2));
				b.setAuthor(rs.getString(3));
				b.setStatus(rs.getString(4));
				
				// call function here to get all book copies
				// count number of available copies from this function call
				// this will eventually replace status
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return b;		
	}
	
	@Override
	public List<Book> getTitleList() {
		//returns all titles
		//will eventually be changed to work better with proper search function
		ResultSet rs;
		List<Book> books = new ArrayList<Book>();
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
				Book book = new Book();
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
	
	@Override
	public int changeStatus(String ISBN, int newStatus) { // will be changed to change status of copy
		// returns specific book; can check out this book
		// check if particular user rented book
		// get particular instead of just title
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
	
	//@Override
	public List<Book> SearchTitleFields(String field, String value) // NEW FUNCTION
	{
		//
		ResultSet rs;
		List<Book> books = new ArrayList<Book>();
		try{
			conn = db.getConnection();
			//ps =conn.prepareStatement("select t.ISBN, t.Title, t.Author, t.Status from TitleList as t, Collection as c where " + field + " like ? and t.ISBN=c.ISBN");
			ps = conn.prepareStatement("select ISBN, Title, Author, Status from TitleList where " + field + " like ?");
			//value = "%" + value; 
			//value = value + "%";
			ps.setString(1,"%" + value + "%");
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
				Book book = new Book();
				book.setISBN(rs.getString(1));
				book.setTitle(rs.getString(2));
				book.setAuthor(rs.getString(3));
				book.setStatus(statusText);
				books.add(book);
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return books;
	}

}
