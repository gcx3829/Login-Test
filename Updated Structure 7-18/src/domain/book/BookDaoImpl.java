package domain.book;

//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.DbManager;
import domain.search.Search;

public class BookDaoImpl implements BookDao {
	static Connection conn;
	static PreparedStatement ps;
	static DbManager db = new DbManager();
	
	public String addNewBook(Book b) {
		String message="";
		try {
			// if book is not in titlelist table, add it into titlelist and collection
			if (getTitle(b.getISBN()).getTitle() == null) { //use find status to see if book is in the database
				conn = db.getConnection();
				
				Statement statement = conn.createStatement();
				String query = "insert into titlelist values("+b.getISBN()+","+b.getTitle()+","
						+b.getAuthor()+","+b.getGenre()+","+b.getEdition()+")";
				
				statement.addBatch(query);
				
				query = "insert into collection values("+b.getISBN()+",1,1,null,null,null)"; //add new title as copy 1
				statement.addBatch(query);
				
				statement.executeBatch();
				statement.close();
				message = "Added a new Copy of '" + b.getTitle() + "' successfully!";
				conn.close();
			}else { // if title already exists; just add a new copy
				int currentCopyID = largestInventoryID(); // find number of copies
				currentCopyID ++;
				conn = db.getConnection();
				ps =conn.prepareStatement("insert into collection values(?,?,1,null,null,null)");
				ps.setString(1, b.getISBN());
				ps.setInt(2, currentCopyID);
				ps.executeUpdate();
				message = "Added book '" + b.getTitle() + "' successfully!";
				conn.close();
			}
		}catch(Exception e){
			System.out.println(e);
			return null;

		}
	
		return message;
	}
	
	public int addTitle(Book b) {
		int status[] = {0};
		try {
			conn = db.getConnection();
			Statement statement = conn.createStatement();
			String query = "insert into titlelist values("+b.getISBN()+","+b.getTitle()+","
					+b.getAuthor()+","+b.getGenre()+","+b.getEdition()+")";
			statement.addBatch(query);
			status = statement.executeBatch();
			conn.close();
		}catch(Exception e){
			System.out.println(e);
			return 0;
		}
		return status[0];
	}
	
	public List<Book> search(Search query) {
		ResultSet rs;
		List<Book> books = new ArrayList<Book>();
		try{
			conn = db.getConnection();
			ps = conn.prepareStatement("select titlelist.ISBN, titlelist.Title, titlelist.Author, titlelist.Edition,"
					+ " titlelist.Genre from titlelist where titlelist.ISBN like ? and titlelist.Title like ?"
					+ " and titlelist.Author like ? and titlelist.Genre like ? and titlelist.Edition like ?");
			ps.setString(1,"%" + query.getValue("ISBN") + "%");
			ps.setString(2,"%" + query.getValue("Title") + "%");
			ps.setString(3,"%" + query.getValue("Author") + "%");
			ps.setString(4,"%" + query.getValue("Genre") + "%");
			ps.setString(5,"%" + query.getValue("Edition") + "%");
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Book book = new Book(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
				
				books.add(book);
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		
		return books;
	}
	
	public List<Book> searchDetails(Search query) {
		ResultSet rs;
		List<Book> books = new ArrayList<Book>();
		try{
			conn = db.getConnection();
			String q = "select titlelist.ISBN, titlelist.Title, titlelist.Author, titlelist.Edition,"
					+ " titlelist.Genre, collection.InventoryID, collection.Status, loan.Renter,"
					+ " loan.CheckOutDate, loan.ReturnByDate from titlelist INNER JOIN collection" 
					+ " ON collection.ISBN = titlelist.ISBN LEFT OUTER JOIN loan ON collection.inventoryID ="
					+ " loan.inventoryID where titlelist.ISBN like ? and titlelist.Title like ?"
					+ " and titlelist.Author like ? and titlelist.Genre like ? and titlelist.Edition like ?"
					+ " and collection.Status like ?";
			
			if (query.getValue("Renter").length()>0) {
				ps = conn.prepareStatement(q + " and loan.Renter = ?");
				ps.setString(7,query.getValue("Renter"));
			} else { ps = conn.prepareStatement(q); }
			ps.setString(1,"%" + query.getValue("ISBN") + "%");
			ps.setString(2,"%" + query.getValue("Title") + "%");
			ps.setString(3,"%" + query.getValue("Author") + "%");
			ps.setString(4,"%" + query.getValue("Genre") + "%");
			ps.setString(5,"%" + query.getValue("Edition") + "%");
			ps.setString(6, "%" + query.getValue("Status") + "%");
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
				String temp;
				temp=rs.getString(8); //code here used to deal with Null results
				if (temp != null) { book.setRentedBy(temp); }
				else {book.setRentedBy(""); }
				temp=rs.getString(9);
				if (temp != null) { book.setCheckOutDate(temp); }
				else {book.setCheckOutDate(""); }
				temp=rs.getString(10);
				if (temp != null) { book.setReturnByDate(temp); }
				else {book.setReturnByDate(""); }
				if(rs.wasNull()) {
					book.setRentedBy("");
					book.setCheckOutDate("");
					book.setReturnByDate("");
				}
				books.add(book);
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		
		return books;
	}
	
	@Override
	public Book getBook(String InventoryID) { //pass in inventory
		// returns specific book; can check out this book
		Book book = new Book();
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select titlelist.ISBN, titlelist.Title, titlelist.Author, titlelist.Edition,"
					+ " titlelist.Genre, collection.InventoryID, collection.Status, loan.Renter,"
					+ " loan.CheckOutDate, loan.ReturnByDate from titlelist INNER JOIN collection" 
					+ " ON collection.ISBN = titlelist.ISBN LEFT OUTER JOIN loan ON collection.inventoryID ="
					+ " loan.inventoryID where collection.InventoryID = ?");
			ps.setString(1, InventoryID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				book = new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4));
				book.setInventoryID(InventoryID);
				book.setStatus(rs.getString(7));
				String temp;
				temp=rs.getString(8); //code here used to deal with Null results
				if (temp != null) { book.setRentedBy(temp); }
				else {book.setRentedBy(""); }
				temp=rs.getString(9);
				if (temp != null) { book.setCheckOutDate(temp); }
				else {book.setCheckOutDate(""); }
				temp=rs.getString(10);
				if (temp != null) { book.setReturnByDate(temp); }
				else {book.setReturnByDate(""); }
				if(rs.wasNull()) {
					book.setRentedBy("");
					book.setCheckOutDate("");
					book.setReturnByDate("");
				}
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return book;		
	}
	
	@Override
	public Book getTitle(String ISBN) { //pass in inventory
		// returns specific book; can check out this book
		Book book = new Book();
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select Title, Author, Edition, Genre from titlelist where ISBN = ?");
			ps.setString(1, ISBN);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				book = new Book(ISBN, rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(3));
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return book;		
	}
	
	public int findStatus(String ISBN) {
		int status = 0;
		
		try {
			conn = db.getConnection();
			ps =conn.prepareStatement("select Status from collection where ISBN = ?");
			ps.setString(1, ISBN);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				if (rs.getString("Status").equals("1")) { //finds if book is available
					status = 1;
					break;
				}
				else if (rs.getString("Status").equals("1")) { //finds if book has waitlist
					status = 5;
				}
				
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);

		}
		return status;
	}
	
	public int updateBookDetails(Book newBook) {
		int status = 0;
		try {
		conn = db.getConnection();
		ps =conn.prepareStatement("update titlelist set ISBN = ?, Title = ?, Author = ?, "
				+ "Edition = ?, Genre = ? where ISBN = ?");
		ps.setString(1, newBook.getISBN());
		ps.setString(2, newBook.getTitle());
		ps.setString(3, newBook.getAuthor());
		ps.setString(4, newBook.getEdition());
		ps.setString(5, newBook.getGenre());
		ps.setString(6, newBook.getInventoryID());
		status = ps.executeUpdate();
		conn.close();
		}catch(Exception e){
			System.out.println(e);
		}	
		
		return status;
	}
	
	public int updateBook(Book newBook) {
		int status = 0;
		try {
		conn = db.getConnection();
		ps =conn.prepareStatement("update collection set ISBN = ?, Status = ? where ISBN = ?");
		ps.setString(1, newBook.getISBN());
		ps.setString(2, newBook.getStatus());
		ps.setString(3, newBook.getInventoryID());
		status = ps.executeUpdate();
		conn.close();
		}catch(Exception e){
			System.out.println(e);
		}	
		
		return status;
	}

	public int updateTitle(String oldISBN, Book newBook) {
		int status = 0;
		try {
		conn = db.getConnection();
		ps =conn.prepareStatement("update titlelist set ISBN = ?, Title = ?, Author = ?, "
				+ "Edition = ?, Genre = ? where ISBN = ?");
		ps.setString(1, newBook.getISBN());
		ps.setString(2, newBook.getTitle());
		ps.setString(3, newBook.getAuthor());
		ps.setString(4, newBook.getEdition());
		ps.setString(5, newBook.getGenre());
		ps.setString(6, oldISBN);
		status = ps.executeUpdate();
		conn.close();
		}catch(Exception e){
			System.out.println(e);
		}	
		
		return status;
	}
	
	public int largestInventoryID() {
		int largestID = -1;
		try {
			conn = db.getConnection();
			ps = conn.prepareStatement("select InventoryID from collection");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				largestID = Math.max(largestID,rs.getInt(0));
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return largestID+1;
	}
	
	
	
public Book getFirstBook(Book book) {
		
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select collection.inventoryID, titlelist.ISBN, titlelist.Title, titlelist.Author,"
					+ " titlelist.edition, titlelist.Genre from collection INNER JOIN titlelist" + 
					" ON collection.ISBN = titlelist.ISBN where titlelist.ISBN = ? and Status = 1");
			ps.setString(1, book.getISBN());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				book.setInventoryID(rs.getString(1));
				book.setISBN(rs.getString(2));
				book.setTitle(rs.getString(3));
				book.setAuthor(rs.getString(4));
				book.setEdition(rs.getString(5));
				book.setGenre(rs.getString(6));
				break;
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return book;	
		
	}
	
	public int changeStatus(Book book, int newStatus) {
		int status=0;
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("update collection set Status = ? where inventoryID = ?");
			ps.setInt(1, newStatus);
			//ps.setString(2, book.getISBN());
			ps.setString(2, book.getInventoryID());
			status = ps.executeUpdate();
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}	
		return status;
	}
	
	// STUFF BELOW IS OUT OF DATE CODE; MADE LAST WEEK *************
	
	/*
	@Override
	public int addBook(Book t) {
		
		try{
			// if book is not in titlelist table, add it into titlelist and collection
			if (getTitle(t.getISBN()).getTitle() == null) { //use find status to see if book is in the database
				conn = db.getConnection();
				
				Statement statement = conn.createStatement();
				String query = "insert into titlelist values("+t.getISBN()+","+t.getTitle()+","
						+t.getAuthor()+","+t.getGenre()+","+t.getEdition()+",1)";
				
				statement.addBatch(query);
				
				query = "insert into collection values("+t.getISBN()+",1,1,null,null,null)"; //add new title as copy 1
				statement.addBatch(query);
				
				statement.executeBatch();
				statement.close();
				conn.close();
			}else { // if title already exists; just add a new copy
				int currentCopyID = largestInventoryID(); // find number of copies
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
	public Book getBook(Book input) { //pass in inventory
		// returns specific book; can check out this book
		Book book = new Book();
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select titlelist.ISBN, titlelist.Title, titlelist.Author, titlelist.Edition,"
					+ " titlelist.Genre, collection.InventoryID, collection.Status, loan.Renter,"
					+ " loan.CheckOutDate, loan.ReturnByDate from titlelist INNER JOIN collection" 
					+ " ON collection.ISBN = titlelist.ISBN LEFT OUTER JOIN loan ON collection.inventoryID ="
					+ " loan.inventoryID where collection.InventoryID = ?");
			ps.setString(1, input.getInventoryID());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				book.setISBN(rs.getString(1));
				book.setTitle(rs.getString(2));
				book.setAuthor(rs.getString(3));
				book.setEdition(rs.getString(4));
				book.setGenre(rs.getString(5));
				book.setInventoryID(rs.getString(6));
				book.setStatus(rs.getString(7));
				String temp;
				temp=rs.getString(8); //code here used to deal with Null results
				if (temp != null) { book.setRentedBy(temp); }
				else {book.setRentedBy(""); }
				temp=rs.getString(9);
				if (temp != null) { book.setCheckOutDate(temp); }
				else {book.setCheckOutDate(""); }
				temp=rs.getString(10);
				if (temp != null) { book.setReturnByDate(temp); }
				else {book.setReturnByDate(""); }
				if(rs.wasNull()) {
					book.setRentedBy("");
					book.setCheckOutDate("");
					book.setReturnByDate("");
				}
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return book;		
	}
	
	@Override
	public Book getTitle(Book input) { //pass in inventory
		// returns specific book; can check out this book
		Book book = new Book();
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select ISBN, Title, Author, Edition, Genre from titlelist where ISBN = ?");
			ps.setString(1, input.getISBN());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				book.setISBN(rs.getString(1));
				book.setTitle(rs.getString(2));
				book.setAuthor(rs.getString(3));
				book.setEdition(rs.getString(4));
				book.setGenre(rs.getString(5));
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return book;		
	}
	
	public int findStatus(Book book) {
		int status = 0;
		
		try {
			conn = db.getConnection();
			ps =conn.prepareStatement("select Status from collection where ISBN = ?");
			ps.setString(1, book.getISBN());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				if (rs.getString("Status").equals("1")) { //finds if book is available
					status = 1;
					break;
				}
				else if (rs.getString("Status").equals("1")) { //finds if book has waitlist
					status = 5;
					break;
				}
				
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);

		}
		return status;
	}
	
	public int updateBook(Book oldBook, Book newBook) {
		int status = 0;
		try {
		conn = db.getConnection();
		ps =conn.prepareStatement("update titlelist set ISBN = ?, Title = ?, Author = ?, "
				+ "Edition = ?, Genre = ? where ISBN = ?");
		ps.setString(1, newBook.getISBN());
		ps.setString(2, newBook.getTitle());
		ps.setString(3, newBook.getAuthor());
		ps.setString(4, newBook.getEdition());
		ps.setString(5, newBook.getGenre());
		ps.setString(6, oldBook.getISBN());
		status = ps.executeUpdate();
		conn.close();
		}catch(Exception e){
			System.out.println(e);
		}	
		
		return status;
	}
	
	public int titleExists(Book book) {
		// checks if book exists
		int status = 0;
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select Title from titlelist where ISBN = ?");
			ps.setString(1, book.getISBN());
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				status = 1; //book exists
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return status;		
	}*/
}
