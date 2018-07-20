package Waitlist;

import java.util.List;

import domain.book.Book;
import domain.user.User;

public class WaitListDaoImpl implements WaitListDao {

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
