package Waitlist;

import java.util.List;
import java.sql.Connection;

import domain.book.Book;
import domain.user.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.DbManager;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

public class WaitListDaoImpl implements WaitListDao {
	static Connection conn;
	static PreparedStatement ps;
	static DbManager db = new DbManager();

	@Override
	public int add(Book b, User u) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteUser(Book b, User u) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int checkUser(Book b, User u) {
		// TODO Auto-generated method stub
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
