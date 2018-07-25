package domain.waitList;

import java.util.List;

import domain.book.Book;
import domain.user.User;

public interface WaitListDao {

	public void add(Book b, User u); // for when user first signs up for waitlist
	//note that in table, waitlist has fields for book ISBN, username, position, and rentByDate in waitlist in table
	public void deleteUser(Book b, User u); //for when user has moved to top of waitlist, and then sucessfully
	//checked out book
	public int checkUser(Book b, User u); // check if user is at position 0 in waitlist for book, or if 
	//no such waitlist exists; might not be necessary considering status will be changed if book is waitlisted
	

	public int moveUp(Book b); //moves up everyone in list except people at position 0
	//this occurs for every new book made available; if person moves up to position at 0, then
	// add date to rentByDate field; look at checkout function for inspiration
	public int setAvailability(Book b); //compares number of people of waitlist to number of books + 1
	// if more books available, then this function signals that the status can be set to available (1)
	//instead of waitlist (waitlist status is 5); if more books, moveUp might not be needed;
	

	public List<WaitListEntry> getBookPositions(User u); //for knowing what position a user is at for each book;
	//may return different type
	

	public void sleepingUser(); //clears any users who are at position 0 who have not checked out the book
	// that they are waiting for; uses rentByDate field for waitlist
	
}
