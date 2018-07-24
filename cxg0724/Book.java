package domain.book; //now using book class instead of title class

import java.util.Objects;

import domain.loan.Loan;
import domain.loan.LoanDao;
import domain.loan.LoanDaoImpl;
import Waitlist.WaitListDao;
import Waitlist.WaitListDaoImpl;

public class Book {
	private String ISBN;
	private String Title;
	private String Author;
	private String Genre;
	private String Edition;// everything below is from collection table
	private String RentedBy;
	private String CheckOutDate;
	private String ReturnByDate;
	private String Status;
	private String InventoryID;
	
	public Book() {
		
	}
	public Book(String isbn) {
		this.ISBN=isbn;
	}
	public Book(String ID, String field) {
		if (field.compareTo("ISBN")==0) {this.ISBN=ID;}
		else if (field.compareTo("InventoryID")==0) {
			this.InventoryID=ID;
		}
	}	
	public Book(String isbn, String title, String author, String genre, String edition) {
		this.ISBN=isbn;
		this.Title=title;
		this.Author=author;
		this.Genre=genre;
		this.Edition=edition;
	}
	
	public String editAll(String isbn, String title, String author, String genre, String edition, 
			String status, String renter, String checkOutDate, String returnDeadline) {
		BookDao bookDao = new BookDaoImpl();
		String message = "";
		int titleChange = 2;
		// cannot change inventory ID of objects
		if (status.compareTo("10")!=0) { //checks if given status value is 10, no changes necessary
			System.out.printf("We are changing the status " + status + this.Status + "\n" );
			if ((status.compareTo("0")==0) && (this.Status.compareTo("0")!=0)) {
				//create new loan details with given values
				LoanDao loanDao = new LoanDaoImpl();
				loanDao.add(new Loan(this, renter, checkOutDate, returnDeadline));
			} else if ((status.compareTo("0")!=0) && (this.Status.compareTo("0")==0)) {
				//loan is no longer valid
				LoanDao loanDao = new LoanDaoImpl();
				loanDao.deleteLoan(this); 
			} else {
				int temp = 0; //used to check if any loan changes included in query
				if (renter.length()>0) {
					this.RentedBy = renter;
					temp++;
				}
				if (checkOutDate.length()>0) {
					this.CheckOutDate = checkOutDate;
					temp++;
				}
				if (returnDeadline.length()>0) {
					this.ReturnByDate = returnDeadline;
					temp++;
				}
				if (temp > 0) {
					int temp2 = bookDao.updateBookDetail(this);
					if (temp2!=0) {
						message = ". Loan details were also changed successfully";
					} else {
						message = "Book with inventory ID " + this.InventoryID + " was not edited successfully";
						return message;
					}
				}
			}
			this.Status = status;
		}
		if (isbn.length()>0) {// only use if ISBN has changed
			this.ISBN = isbn;
			System.out.printf("We got to the right changing stuff \n");
			//only use if isbn has changed
			if (bookDao.getTitle(isbn).getTitle()==null) {
				// this means that book is part of a new title that didn't exist before
				//rest of title fields necessary to add new title to tables
				System.out.printf("We got to the right changing stuff \n");
				this.Title=title;
				this.Author=author;
				this.Genre=genre;
				this.Edition=edition;
				titleChange = bookDao.addTitle(this);
			}
			// otherwise just switching which title is referred to
		}
		
		if (titleChange == 2) { //no changes made to title field
			titleChange = bookDao.updateBook(this);
			if (titleChange==0) { //failed to make changes to title field
				message = "Book with inventory ID " + this.InventoryID + " was not edited successfully" + message;}
			else {
				message = "Book with inventory ID " + this.InventoryID + " was edited successfully" + message;
			}
		} else if (titleChange ==0) {
			message = "Book with inventory ID " + this.InventoryID + " was not edited successfully" + message;
		} else { // successfully changed title fields
			titleChange = bookDao.updateBook(this);
			if (titleChange==0) { 
				message = "Book with inventory ID " + this.InventoryID + " was not edited successfully" + message;}
			else {
				message = "Book with inventory ID " + this.InventoryID + " was been assigned a new "
						+ "title and new book details successfully" + message;
			}
		}		
		return message;
	}
	
	public String editTitle(String isbn, String title, String author, String genre, String edition) {
		BookDao bookDao = new BookDaoImpl();
		String message="";
		String oldISBN = this.ISBN;
		if (isbn.length()>0) {
			this.ISBN = isbn;
		}
		if (title.length()>0) {
			this.Title = title;
		}
		if (author.length()>0) {
			this.Author = author;
		}
		if (genre.length()>0) {
			this.Genre = genre;
		}
		if (edition.length()>0) {
			this.Edition = edition;
		}
		int status = bookDao.updateTitle(oldISBN, this);
		if (status==1) {
			message = "Book with ISBN " + oldISBN + " has been successfully edited!!";
		} else {
			message = "Book with ISBN " + oldISBN + " could not be edited";
		}
		return message;
	}
	public String process(int condition) {
		String message = "Book has not been returned";
		int status = 0;
		int status1 = 0;
		
		/*if(this.Status.compareTo("2")==0) {
			//status = bookDao.changeStatus(b, condition);
			status = this.updateStatus(condition);
		}else if(this.Status.compareTo("3")==0) {
			//status = bookDao.changeStatus(b, condition);
			status = this.updateStatus(condition);
		}*/
		if ((this.Status.compareTo("2")==0) || (this.Status.compareTo("3")==0)) {
			// ****** WATILIST CODE ****** //
			//check if condition == 1. If so, call waitlist functions
			//see if book can be made available, or if it needs to be set to waitlist status instead
			//if it needs to be set to waitlist status, set condition = 5 
			// ****** WATILIST CODE ****** //
			if (condition == 1 ) {
				// Call waitlist function
				WaitListDao wld = new WaitListDaoImpl();
				condition = wld.setAvailability(this);
				status1 = wld.moveUp(this);
			}
			status = this.updateStatus(condition);
		}
		
		if ((status!=0) && (status1!=0) && (condition==1)) {
			message ="Book has been made available for Library patrons";
		} else if ((status!=0) && (status1!=0) && (condition==3)) {
			message = "Book has been sent for repair";
		}else if ((status!=0) && (status1!=0) && (condition==4)) {
			message="Book has been ruined and cannot be made available to the public";
		}else if ((status!=0) && (status1!=0) && (condition==5)) { //if book has been waitlisted
			message="Book has been made available to the next person on the waitlist";
		}
		
		return message;
	}
	public int updateStatus(int condition) {
		BookDao bookDao = new BookDaoImpl();
		return bookDao.changeStatus(this, condition);
	}
	public String getISBN() {
		return this.ISBN;
	}
	public void setISBN(String ISBN) {
		this.ISBN=ISBN;
	}
	public String getTitle() {
		return this.Title;
	}
	public void setTitle(String Title) {
		this.Title=Title;
	}
	
	public String getAuthor() {
		return this.Author;
	}
	public void setAuthor(String Author) {
		this.Author=Author;
	}
	
	public String getGenre() {
		return this.Genre;
	}
	public void setGenre(String Genre) {
		this.Genre=Genre;
	}
	
	public String getEdition() {
		return this.Edition;
	}
	public void setEdition(String Edition) {
		this.Edition=Edition;
	}
	
	public String getRentedBy() {
		return this.RentedBy;
	}
	public void setRentedBy(String RentedBy) {
		this.RentedBy=RentedBy;
	}
	
	public String getCheckOutDate() {
		return this.CheckOutDate;
	}
	public void setCheckOutDate(String CheckOutDate) {
		this.CheckOutDate=CheckOutDate;
	}
	
	public String getReturnByDate() {
		return this.ReturnByDate;
	}
	public void setReturnByDate(String ReturnByDate) {
		this.ReturnByDate=ReturnByDate;
	}
	

	public String getStatus() { 
		return this.Status;
	}
	public void setStatus(String Status) {
		this.Status=Status;
	}
	
	public void setNull() {
		this.ISBN="";
		this.Title="";
		this.Author="";
		this.Genre="";
		this.Edition="";
		this.InventoryID="";
		this.RentedBy="";
		this.CheckOutDate="";
		this.ReturnByDate="";
		this.Status="";
	}

    // commented    
    @Override
    public int hashCode() {
        /*
        int result=17;
        result=31*result+ISBN.hashCode();
        result=31*result+(CopyID!=null ? CopyID.hashCode():1);
        return result;
        */
    	return Objects.hash(ISBN, InventoryID);
    }
	
    @Override
    public boolean equals(final Object obj){
        if(obj instanceof Book){
            final Book other = (Book) obj;
            return (ISBN.compareTo(other.ISBN)==0 && InventoryID.compareTo(other.InventoryID)==0);
            //return (Objects.equals(ISBN, other.ISBN) && Objects.equals(CopyID, this.CopyID));
        } else{
            return false;
        }
    }
	
	public String getInventoryID() {
		return this.InventoryID;
	}
	public void setInventoryID(String InventoryID) {
		this.InventoryID=InventoryID;
	}
    
	/*@Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Book))
            return false;
        if (obj == this)
            return true;
        Book book = (Book) obj;
        
        // need to change to include copyID
        return book.getISBN() == this.getISBN();
    }*/

}