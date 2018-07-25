package domain.waitList;

import domain.book.Book;
import domain.user.User;

public class WaitListEntry {
	private Book book;
	//private User waitlistee; //not used in this iteration
	private int position;
	
	public WaitListEntry(Book b, User u, int i) {
		this.book = b;
		//this.waitlistee = u;
		this.position = i;
	}
	
	public String tableBookPositionDetails() {
		String tableRowDisplay="";
		StringBuilder rowStringTableFormat = new StringBuilder();
		String posText;
		if (position == 0) {
			posText="Available for Pickup";
		} else { posText = "Position "+Integer.toString(position); }
		rowStringTableFormat.append("<td>").append(posText).append("</td>");
		rowStringTableFormat.append("<td>").append(book.getISBN()).append("</td>");
		rowStringTableFormat.append("<td>").append(book.getTitle()).append("</td>");
		rowStringTableFormat.append("<td>").append(book.getAuthor()).append("</td>");
		rowStringTableFormat.append("<td>").append(book.getGenre()).append("</td>");
		rowStringTableFormat.append("<td>").append(book.getEdition()).append("</td>");
		tableRowDisplay = rowStringTableFormat.toString();
		return tableRowDisplay;
	}

}
