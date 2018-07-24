package Waitlist;

import java.util.List;

import domain.book.*;
import domain.user.*;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.DbManager;
import domain.search.Search;

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
			
			int status = ps.executeUpdate();
			
			conn.close();
		}catch(Exception e){
			System.out.println(e);

		}
	}

	@Override
	public void deleteUser(Book b, User u) {
		// TODO Auto-generated method stub
		int status;
		try {
			conn = db.getConnection();
			ps = conn.prepareStatement("delete from waitlist where ISBN = ? and username = ?");
			ps.setString(1, b.getISBN());
			ps.setString(2, u.getUsername());
			status = ps.executeUpdate();
			
			ps = conn.prepareStatement("update collection set status = 1 where ISBN = ? and status = 5");
			ps.setString(1, b.getISBN());
			status = ps.executeUpdate();
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int setAvailability(Book b) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBookPositions(Book b, User u) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void sleepingUser() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<User> getWaitlist(Book b) {
		// TODO Auto-generated method stub
		return null;
	}

}
