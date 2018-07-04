package collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import db.DbManager;

public class LibraryImpl {
	static Connection conn;
	static PreparedStatement ps;
	static DbManager db = new DbManager();	
	
	public static String displayCollection() {
		// displays entire collection and all details
		String message = "";
		StringBuilder displayTable = new StringBuilder();
		displayTable.append("<tr><th>ISBN</th><th>Title</th><th>Author</th><th>Status</th></tr>"); //set up first line of table
		try {
			conn = db.getConnection();
			String selectSQL = "Select ISBN, Title, Author, Status From booklist";
			PreparedStatement ps = conn.prepareStatement(selectSQL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String statusText;
				//code to find the status of the books
				if (rs.getString(4).equals("1")) { //code to turn int for status into string
					statusText = "Available";
				}
				else {
					statusText = "Unavailable";
				}
				displayTable.append("<tr>"); //for start of line in table
				displayTable.append("<td>").append(rs.getString(1)).append("</td>"); // prints ISBN
				displayTable.append("<td>").append(rs.getString(2)).append("</td>"); // prints Title
				displayTable.append("<td>").append(rs.getString(3)).append("</td>"); // prints Author
				displayTable.append("<td>").append(statusText).append("</td>"); //prints status text
				displayTable.append("</tr>"); //for end of line in table
			}
		}catch(Exception e){
			System.out.println(e);
		} 
		message=displayTable.toString(); //transforms stringbuilder into string
		return message;
	}
	//Search function based on display function
	public static String search(String keyWord) { // new code
		String message = "";
		StringBuilder displayTable = new StringBuilder();
		displayTable.append("<tr><th>ISBN</th><th>Title</th><th>Author</th><th>Status</th></tr>"); //set up first line of table
		try {
			conn = db.getConnection();
			String selectSQL = "Select ISBN, Title, Author, Status From booklist where Title like '%"+keyWord+"%'";
			PreparedStatement ps = conn.prepareStatement(selectSQL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String statusText;
				//code to find the status of the books
				if (rs.getString(4).equals("1")) { //code to turn int for status into string
					statusText = "Available";
				}
				else {
					statusText = "Unavailable";
				}
				displayTable.append("<tr>"); //for start of line in table
				displayTable.append("<td>").append(rs.getString(1)).append("</td>"); // prints ISBN
				displayTable.append("<td>").append(rs.getString(2)).append("</td>"); // prints Title
				displayTable.append("<td>").append(rs.getString(3)).append("</td>"); // prints Author
				displayTable.append("<td>").append(statusText).append("</td>"); //prints status text
				displayTable.append("</tr>"); //for end of line in table
			}
		}catch(Exception e){
			System.out.println(e);
		} 
		message=displayTable.toString(); //transforms stringbuilder into string
		return message;
	}
	
	public static int addBook(Book b) {
		int status = 0;
		
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("insert into booklist values(?,?,?,?,?,?)");
			ps.setString(1, b.getISBN());
			ps.setString(2, b.getTitle());
			ps.setString(3, b.getAuthor());
			ps.setString(4, "null");
			ps.setString(5, "null");
			ps.setInt(6, 1);
			status = ps.executeUpdate();
			
			conn.close();

		}catch(Exception e){
			System.out.println(e);

		}
		return status;
	}
	
	
	public static int checkout(copy col) {
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
				//function using copy class
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
	
	public static int returnBook(copy col) {
		//check out function
		//run SQL query
		int status_available = 1;
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
		
		if(status_available==0) {
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
				//function using copy class
				ps =conn.prepareStatement("update Booklist set status = 1 where ISBN = ?");
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
