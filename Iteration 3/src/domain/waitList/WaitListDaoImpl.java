package domain.waitList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import db.DbManager;
import domain.book.*;
import domain.user.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WaitListDaoImpl implements WaitListDao {
	static Connection conn;
	static PreparedStatement ps;
	static DbManager db = new DbManager();
	BookDao bookdao = new BookDaoImpl();
	@Override
	public void add(Book b, User u) {
		int position = 0;
		
		try {
			conn = db.getConnection();
			ps =conn.prepareStatement("select Position from waitlist where ISBN = ?");
			ps.setString(1, b.getISBN());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				if(rs.getInt("Position") >= position) {
					position = rs.getInt("Position") + 1;
				}
			}
			conn.close();
			
			conn = db.getConnection();
			ps = conn.prepareStatement("INSERT INTO waitlist "
					                 + "VALUES (?,?,?,?)");
			ps.setString(1, b.getISBN());
			ps.setString(2, u.getUsername());
			ps.setInt(3, position);
			ps.setString(4, LocalDateTime.now().plusDays(14).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			
			ps.executeUpdate();
			
			conn.close();
		}catch(Exception e){
			System.out.println(e);

		}
	}

	@Override
	public void deleteUser(Book b, User u) {
		// TODO Auto-generated method stub
		try {
			conn = db.getConnection();
			ps = conn.prepareStatement("delete from waitlist where ISBN = ? and username = ?");
			ps.setString(1, b.getISBN());
			ps.setString(2, u.getUsername());
			ps.executeUpdate();
			
			ps = conn.prepareStatement("update collection set status = 1 where ISBN = ? and status = 5");
			ps.setString(1, b.getISBN());
			ps.executeUpdate();
			conn.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	@Override
	public int checkUser(Book b, User u) {
		int bookStatus = bookdao.findStatus(b.getISBN());
		int status = 0;
		
		// user already have that book
		Book b1= u.getLoanedBook(b.getISBN());
		if (b1!=null) {
			status = 1;
			return status;
		}
		
		if(bookStatus == 0) {
			try {
				conn = db.getConnection();
				ps =conn.prepareStatement("select Position, username from waitlist where ISBN = ?");
				ps.setString(1, b.getISBN());
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()){
					if (status <=2) {
						status = 2; // the waiting of that book exists
					}
					
					if (rs.getString("username").equals(u.getUsername())) {
						if (status <=3) {     // that user is already in wait list
							status = 3;
						}
						
						if(rs.getInt("Position") == 0) {  // that user is in 0 position, can check out book
							status = 4;
						}
						
					}
				}
				conn.close();
			}catch(Exception e){
				System.out.println(e);

			}
			return status;
		}
		
		return 0;
	}

	@Override
	public int moveUp(Book b) {
		int rs1;
		int rs2;
		int success = 0;
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("update waitlist set RentByDate = ? where ISBN = ? and position = 1");
			// update info for user who is in position 1
			Date d = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ps.setString(1, df.format(new Date(d.getTime()+7*24*60*60*1000)));
			ps.setString(2, b.getISBN());
			rs1 = ps.executeUpdate();
			ps.clearBatch();
			
			ps = conn.prepareStatement("update waitlist set position = position -1 where position != 0");
			//move up user whos is waiting
			rs2 = ps.executeUpdate();
					
			conn.close();
			if ((rs1 == 1) && (rs2 == 2)) {
				success = 1;
			}
		}catch(Exception e){
			System.out.println(e);
		}
		return success;
	}

	@Override
	public int setAvailability(Book b) {
		ResultSet rs;
		int status = -1;
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select * from waitlist where ISBN = ? and position = 1");
			ps.setString(1, b.getISBN());
			rs = ps.executeQuery();
			if (rs.next()) {
				status = 5;
				// someone is waiting for the book, this one is reserved
			}else {
				status = 1;
				//no one waits, set the book available
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return status;
	}

	@Override
	public List<WaitListEntry> getBookPositions(User u) {
		// TODO Auto-generated method stub

		List<WaitListEntry> userPos = new ArrayList<WaitListEntry>();
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select titlelist.ISBN, titlelist.Title, titlelist.Author,"
					+ " titlelist.Genre,  titlelist.Edition, waitlist.Position from waitlist INNER JOIN" 
					+ " titlelist ON waitlist.ISBN = titlelist.ISBN where waitlist.Username = ?");
			ps.setString(1, u.getUsername());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				WaitListEntry w = new WaitListEntry(new Book(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5)), u, rs.getInt(6));
				userPos.add(w);
			}
		
		}catch(Exception e){
			System.out.println(e);
		}
		return userPos;
	}

	@Override
	public void sleepingUser() {
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select RentByDate, ISBN, Username"
					+ " from waitlist where Position = ?");
			ps.setInt(1, 0);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rs.getString(1);
			    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			    LocalDateTime date = LocalDateTime.parse(rs.getString(1), formatter);
			    if (date.isBefore(LocalDateTime.now())) {
			    	System.out.printf("User is about to be deleted from waitlist\n");
			    	ps = conn.prepareStatement("delete from waitlist where waitlist.ISBN = ? and  waitlist.Username = ?");
			    	ps.setString(1, rs.getString(2));
			    	ps.setString(2, rs.getString(3));
			    	ps.executeUpdate();
			    }
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}

	@Override
	public List<User> getWaitlist(Book b) {
		// TODO Auto-generated method stub
		return null;
	}

}
