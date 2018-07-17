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
	
	@Override
public int addBook(Book t) {
		
		try{
			// if book is not in titlelist table, add it into titlelist and collection
			if (titleExists(t.getISBN()) == 0) { //use find status to see if book is in the database
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
				Book book = new Book();
				book.setISBN(rs.getString(1));
				book.setTitle(rs.getString(2));
				book.setAuthor(rs.getString(3));
				book.setEdition(rs.getString(4));
				book.setGenre(rs.getString(5));
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
				ps = conn.prepareStatement(q + " and loan.Renter like ?");
				ps.setString(7,"%" + query.getValue("Renter") + "%");
			} else { ps = conn.prepareStatement(q); }
			ps.setString(1,"%" + query.getValue("ISBN") + "%");
			ps.setString(2,"%" + query.getValue("Title") + "%");
			ps.setString(3,"%" + query.getValue("Author") + "%");
			ps.setString(4,"%" + query.getValue("Genre") + "%");
			ps.setString(5,"%" + query.getValue("Edition") + "%");
			ps.setString(6, "%" + query.getValue("Status") + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				Book book = new Book();
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
			System.out.println("returnerror");
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
	
	// **************  CODE BELOW THIS NEEDS TO BE REPLACED OR DELETED ***********
	
	public int findStatus(String ISBN){ 
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
				
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);

		}
		return status;
	}
	
	@Override
	public Book getTitleDetails(String ISBN) {
		// returns specific book; can check out this book
		Book b = new Book();
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select ISBN, Title, Author from TitleList where ISBN = ?");
			ps.setString(1, ISBN);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				b.setISBN(rs.getString(1));
				b.setTitle(rs.getString(2));
				b.setAuthor(rs.getString(3));
				
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
	public Book getCopyDetails(String ISBN, String CopyID) {
		// returns specific book; can check out this book
		Book b = new Book();
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select ISBN, CopyID, Status, RentedBy, CheckOutDate, ReturnByDate "
					+ "from collection where ISBN = ? and CopyID = ?");
			ps.setString(1, ISBN);
			ps.setString(1, CopyID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				b.setISBN(rs.getString(1));
				b.setInventoryID(rs.getString(2));
				b.setStatus(rs.getString(3));
				b.setRentedBy(rs.getString(4));
				b.setCheckOutDate(rs.getString(5));
				b.setReturnByDate(rs.getString(6));
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
				if (findStatus(rs.getString(1)) == 1) { //code to turn int for status into string
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
	public int changeStatus(String InventoryID, int newStatus) { // will be changed to change status of copy
		// returns specific book; can check out this book
		// check if particular user rented book
		// get particular instead of just title
		// returns specific book; can check out this book
		int status=0;
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("update collection set Status = ? where inventoryID = ?");
			ps.setString(2, InventoryID);
			ps.setInt(1, newStatus);
			status = ps.executeUpdate();
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}	
		return status;
	}
	
	/*public int changeStatus_Withcopyid(String ISBN, int copyid, String Userid ,int newStatus) {
		int status=0;
		// rework this so that editTitleFields and editCopyFields is used instead
		try{
			if(newStatus == 0) {
				conn = db.getConnection();
				ps =conn.prepareStatement(
						"update collection "
						+ "set Status = ?, RentedBy = ?, CheckOutDate= ? , ReturnByDate = ? "
						+ "where ISBN = ? and CopyID = ?");
				ps.setInt(1, newStatus);
				ps.setString(2, Userid);

		        String CurrentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				ps.setString(3, CurrentDateTime);
				
				String ReturnDateTime = LocalDateTime.now().plusDays(14).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				ps.setString(4, ReturnDateTime);
				
				ps.setString(5, ISBN);
				ps.setInt(6, copyid);
				status = ps.executeUpdate();
				conn.close();
			}
			else {
				conn = db.getConnection();
				ps =conn.prepareStatement(
					"update collection "
					+ "set status = ?, RentedBy = ?, CheckOutDate= ? , ReturnByDate = ? "
					+ "where ISBN = ? and copyid = ?");
				ps.setInt(1, newStatus);
				ps.setString(2, null);
	
		        ps.setString(3, null);
				
				ps.setString(4, null);
				
				ps.setString(5, ISBN);
				ps.setInt(6, copyid);
				status = ps.executeUpdate();
				conn.close();
			}
			
		}catch(Exception e){
			System.out.println(e);
		}	
		return status;
	}*/
	
	@Override
	public int titleExists(String ISBN) {
		// checks if book exists
		int status = 0;
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select Title from titlelist where ISBN = ?");
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
	public int copyExists(String InventoryID) {
		// checks if book exists
		int status = 0;
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select CopyID from collection where inventoryID = ?");
			ps.setString(1, InventoryID);
			ResultSet rs = ps.executeQuery();
			//rs.next();
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
			ps =conn.prepareStatement("update titlelist set " + field + " = ? where ISBN = ?");
			ps.setString(1, value);
			ps.setString(2, ISBN);
			ps.executeUpdate();
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}	
	}
	
	@Override
	public void editCopyFields(String field, String ISBN, String CopyID, String value) // NEW FUNCTION
	{
		//
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("update collection set " + field + " = ? where ISBN = ? and CopyID = ?");
			ps.setString(1, value);
			ps.setString(2, ISBN);
			ps.setString(3, CopyID);
			ps.executeUpdate();
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}	
	}
	
	public void deleteTitles(String ISBN) {
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("delete from titlelist where ISBN = ?");
			ps.setString(1, ISBN);
			ps.executeUpdate();
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
	
	public void deleteCopy(String ISBN, String CopyID) {
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("delete from titletist where ISBN = ? AND CopyID = ?"); 
			ps.setString(1, ISBN);
			ps.setString(2, CopyID);
			ps.executeUpdate();
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
	
	public int findCurrentAvailCopyNum(String ISBN) {
		int AvailCopyID = 0;
		
		try {
			conn = db.getConnection();
			ps =conn.prepareStatement("select copyid from collection where ISBN = ? AND status = 1");
			ps.setString(1, ISBN);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				AvailCopyID = Integer.parseInt(rs.getString("CopyID"));
				break;
				
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);

		}
		return AvailCopyID;
	}
	
	public int findUserRentCopyID(String ISBN, String Userid) {
		int UserRentCopyID = 0;
		
		try {
			conn = db.getConnection();
			ps =conn.prepareStatement("select CopyID from collection where ISBN = ? AND RentedBy = ?");
			ps.setString(1, ISBN);
			ps.setString(2, Userid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				UserRentCopyID = Integer.parseInt(rs.getString("CopyID"));
				break;
				
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);

		}
		return UserRentCopyID;
	}
	

	public int checkUserRent(String ISBN, String Userid) {
		int status = 0;
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select copyid from collection where ISBN = ? and rentedby = ?");
			ps.setString(1, ISBN);
			ps.setString(2, Userid);
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
			ps =conn.prepareStatement("select titlelist.ISBN, titlelist.Title, titlelist.Author, titlelist.Edition,"
					+ " titlelist.Category, collection.CopyID, collection.Status, collection.RentedBy,"
					+ " collection.CheckOutDate, collection.ReturnByDate from titlelist INNER JOIN collection"
					+ " ON collection.ISBN = titlelist.ISBN where titlelist." + field + " like ?");
			ps.setString(1,"%" + value + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				Book book = new Book();
				book.setISBN(rs.getString(1));
				book.setTitle(rs.getString(2));
				book.setAuthor(rs.getString(3));
				book.setEdition(rs.getString(4));
				book.setGenre(rs.getString(5));
				book.setInventoryID(rs.getString(6));
				book.setStatus(rs.getString(7));
				String temp; //code here used to deal with Null results
				temp=rs.getString(8);
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

	//@Override
		public List<Book> SearchCopyFields(String field, String value) // NEW FUNCTION
		{
			//
			ResultSet rs;
			List<Book> books = new ArrayList<Book>();
			try{
				conn = db.getConnection();
				ps =conn.prepareStatement("select titlelist.ISBN, titlelist.Title, titlelist.Author, titlelist.Edition,"
						+ " titlelist.Category, collection.CopyID, collection.Status, collection.RentedBy,"
						+ " collection.CheckOutDate, collection.ReturnByDate from collection INNER JOIN titlelist"
						+ " ON collection.ISBN = titlelist.ISBN where collection." + field + " like ?");
				ps.setString(1,"%" + value + "%");
				rs = ps.executeQuery();
				while (rs.next()) {
					Book book = new Book();
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
		
		public List<Book> getAllBooks() // NEW FUNCTION
		{
			//
			ResultSet rs;
			List<Book> books = new ArrayList<Book>();
			try{
				conn = db.getConnection();
				ps =conn.prepareStatement("select titlelist.ISBN, titlelist.Title, titlelist.Author, titlelist.Edition,"
						+ " titlelist.Category, collection.CopyID, collection.Status, collection.RentedBy,"
						+ " collection.CheckOutDate, collection.ReturnByDate from collection INNER JOIN titlelist"
						+ " ON collection.ISBN = titlelist.ISBN");
				rs = ps.executeQuery();
				while (rs.next()) {
					Book book = new Book();
					book.setISBN(rs.getString(1));
					book.setTitle(rs.getString(2));
					book.setAuthor(rs.getString(3));
					book.setEdition(rs.getString(4));
					book.setGenre(rs.getString(5));
					book.setInventoryID(rs.getString(6));
					book.setStatus(rs.getString(7));
					String temp; //code here used to deal with Null results
					temp=rs.getString(8);
					if (temp != null) { book.setRentedBy(temp); }
					else {book.setRentedBy(""); }
					temp=rs.getString(9);
					if (temp != null) { book.setCheckOutDate(temp); }
					else {book.setCheckOutDate(""); }
					temp=rs.getString(10);
					if (temp != null) { book.setReturnByDate(temp); }
					else {book.setReturnByDate(""); }
					books.add(book);
				}
				conn.close();
			}catch(Exception e){
				System.out.println(e);
			}
			return books;
		}
		
		public int returnStatus(String InventoryID) {
			int status = -1;
			try {
				conn = db.getConnection();
				ps =conn.prepareStatement("select Status from Collection where inventoryID = ?");
				ps.setString(1, InventoryID);
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					status = Integer.parseInt(rs.getString("Status"));
				}		
				conn.close();
			}catch(Exception e){
				System.out.println(e);
			}	
			return status;
		}
		
		public int update(Book book, String oldISBN) {
			int success = 0;
			int temp = 0;
			System.out.println(book.getISBN());
			System.out.println(book.getInventoryID());
			System.out.println(book.getAuthor());
			try{
				conn = db.getConnection();
				ps = conn.prepareStatement("select * from titlelist where ISBN = ?");
				ps.setString(1, book.getISBN());
				ResultSet rs_1 = ps.executeQuery();
				while (rs_1.next()) {
					temp = 1;
				}
				ps.clearBatch();
				System.out.println("*******");
				System.out.println(temp);
				if (temp == 1) {
					ps = conn.prepareStatement("update titlelist set ISBN = ?, Title = ?, Author = ?, Genre = ?, Edition = ? where ISBN = ?");
					ps.setString(1, book.getISBN());
					ps.setString(2, book.getTitle());
					ps.setString(3, book.getAuthor());
					ps.setString(4, book.getGenre());
					ps.setString(5, book.getEdition());
					ps.setString(6, oldISBN);
				}else {
					ps = conn.prepareStatement("insert into titlelist values(?,?,?,?,?)");
					ps.setString(1, book.getISBN());
					ps.setString(2, book.getTitle());
					ps.setString(3, book.getAuthor());
					ps.setString(4, book.getGenre());
					ps.setString(5, book.getEdition());
				}
				temp = 0;
				int rs1 = ps.executeUpdate();
				ps.clearBatch();
				
				ps = conn.prepareStatement("update collection set ISBN = ?, Status = ? where inventoryID = ?");	
				ps.setString(1, book.getISBN());
				ps.setString(2, book.getStatus());
				ps.setString(3, book.getInventoryID());
				int rs2 = ps.executeUpdate();
				ps.clearBatch();
				
				ps = conn.prepareStatement("select * from loan where inventoryID = ?");
				ps.setString(1, book.getInventoryID());
				ResultSet rs_2 = ps.executeQuery();
				while (rs_2.next()) {
					temp = 1;
				}
				System.out.println("*******");
				System.out.println(temp);
				if (temp == 1) {
					ps = conn.prepareStatement("update loan set inventoryID = ?, Renter = ?, CheckOutDate = ?, ReturnByDate = ? where inventoryID = ?");
					ps.setString(1, book.getInventoryID());
					ps.setString(2, book.getRentedBy());
					ps.setString(3, book.getCheckOutDate());
					ps.setString(4, book.getReturnByDate());
					ps.setString(5, book.getInventoryID());
				}else {
					ps = conn.prepareStatement("insert into loan values(?,?,?,?)");	
					ps.setString(1, book.getInventoryID());
					ps.setString(2, book.getRentedBy());
					ps.setString(3, book.getCheckOutDate());
					ps.setString(4, book.getReturnByDate());
				}
				int rs3 = ps.executeUpdate();
				ps.clearBatch();
				
				if(rs1 == 1 || rs2 == 1 || rs3 == 1) {
					success = 1;
				}
				conn.close();
			}catch(Exception e){
				System.out.println(e);
				System.out.println("updateError");
			}
			return success;
		}
}
