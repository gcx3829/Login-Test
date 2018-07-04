package domain.manage;

public class Manage {
	
	public int editBook() {
		// function to edit books already in database
		return 0;
	}
	
	public int deleteBook() {
		// function to delete books already in database
		return 0;
	}
	
	public int processReturn(String ISBN, int newStatus) {
		// will also pass info to identify copy
		// function to process returned books
		
		// check if book status is 'checked out' or 'returned'
		
		//change status according to condition of book
		//status=titleDao.changeStatus(ISBN, newStatus);
		return 0;
	}

}
