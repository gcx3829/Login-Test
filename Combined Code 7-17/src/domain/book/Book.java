package domain.book; //now using book class instead of title class

import java.util.Objects;

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
	//public Book(String isbn) {
	//	this.ISBN=isbn;
	//}
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