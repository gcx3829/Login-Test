package domain.manage;

import domain.book.*;
import domain.search.OldSearch;

public class EditBooks {
	
	public String edit(String targetISBN, String ISBN, String title, String author, String genre, String edition) { // new code
		BookDao bookDao = new BookDaoImpl();
		int status=0;
		String message ="";
		String newISBN, newTitle, newAuthor, newGenre, newEdition;
		
		
		
		if (bookDao.titleExists(targetISBN)==1) {
			status=1;
			Book oldBook = new Book(targetISBN, "ISBN");
			oldBook = bookDao.getTitle(oldBook);
			if (ISBN.length()>0) {newISBN = ISBN;}
			else { newISBN = oldBook.getISBN();}
			if (title.length()>0) {newTitle = title;}
			else { newTitle = oldBook.getTitle();}
			if (author.length()>0) {newAuthor = author;}
			else { newAuthor = oldBook.getAuthor();}
			if (genre.length()>0) {newGenre = genre;}
			else { newGenre = oldBook.getGenre();}
			if (edition.length()>0) {newEdition = edition;}
			else { newEdition = oldBook.getEdition();}
			Book newBook = new Book(newISBN, newTitle, newAuthor, newGenre, newEdition);
			status=bookDao.updateBook(oldBook, newBook);
		}
		if (status == 0){
			message = "Book with ISBN " + targetISBN + " is not in the database yet!!";
		} else if (status == 1) {
			message = "Book with ISBN " + targetISBN + " has been successfully edited!!";
		} else {
			message = "Book with ISBN " + targetISBN + " could not be edited";
		}
		
		return message;
	}
	
	/*
	public String edit(Book b, String ISBN) { // new code
		BookDao bookDao = new BookDaoImpl();
		int status=0;
		String message ="";
		
		
		
		if (bookDao.titleExists(ISBN)==1) {
			status=1;
			Book oldBook = new Book(ISBN, "ISBN");
			oldBook = bookDao.getTitle(oldBook);
			if (b.getISBN().length()>0) {
			}
			status=bookDao.updateBook(oldBook, b);
		}
		if (status == 0){
			message = "Book with ISBN " + ISBN + "is not in the database yet!!";
		} else {
			message = "Book with ISBN " + ISBN + "has been successfully edited!!";
		}
		
		return message;
	}
	
	public int editTitle(Book b, String ISBN) { // new code
		BookDao bookDao = new BookDaoImpl();
		int status=0;
		
		
		if (bookDao.titleExists(ISBN)==1) {
			status=1;
			
			//
			if (b.getISBN().length()>0) {
				bookDao.editTitleFields("ISBN", ISBN, b.getISBN());
				int max = bookDao.findCurrentCopyNum(ISBN);
				for (int i=1; i<=max; i++) {
					bookDao.editCopyFields("ISBN", ISBN, Integer.toString(i), b.getISBN());
				}
				ISBN = b.getISBN();
			}
			
			if (b.getTitle().length()>0) {
				bookDao.editTitleFields("Title", ISBN, b.getTitle());
			}
			
			if (b.getAuthor().length()>0) {
				bookDao.editTitleFields("Author", ISBN, b.getAuthor());
			}
			
			if (b.getGenre().length()>0) {
				bookDao.editTitleFields("Category", ISBN, b.getGenre());
			}
			
			if (b.getEdition().length()>0) {
				bookDao.editTitleFields("Edition", ISBN, b.getEdition());
			}
		}
		return status;
	}
	
	public int editCopy(Book b, String ISBN, String CopyID) { // new code
		BookDao bookDao = new BookDaoImpl();
		int status=0;
		
		if (bookDao.copyExists(ISBN, CopyID)==1) {
			status=1;
		
			if (b.getISBN().length()>0) {
				bookDao.editCopyFields("ISBN", ISBN, CopyID, b.getISBN());
				ISBN = b.getISBN();
			}
			
			if (b.getInventoryID().length()>0) {
				bookDao.editCopyFields("CopyID", ISBN, CopyID, b.getInventoryID());
				CopyID = b.getInventoryID();
			}
			
			if (b.getRentedBy().length()>0) {
				bookDao.editTitleFields("RentedBy", ISBN, b.getRentedBy());
			}
			
			if (b.getCheckOutDate().length()>0) {
				bookDao.editTitleFields("CheckOutDate", ISBN, b.getCheckOutDate());
			}
			
			if (b.getReturnByDate().length()>0) {
				bookDao.editTitleFields("ReturnByDate", ISBN, b.getReturnByDate());
			}
			
			if (b.getStatus().length()>0) {
				bookDao.changeStatus(ISBN, CopyID, Integer.parseInt(b.getStatus()));
				//bookDao.editTitleFields("Status", ISBN, t.getStatus());
			}
			
			
		}
		return status;
	}
	*/
}

