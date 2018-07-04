package domain.book; //now using book class instead of title class

public class book {
	private String ISBN;
	private String Title;
	private String Author;
	private String Category;
	private String Edition;
	private String CopyID; // everything here and below is from collection table
	private String RendedBy;
	private String CheckOutDate;
	private String ReturnByDate;
	private String Status;
	
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
	
	public String getCategory() {
		return this.Category;
	}
	public void setCategory(String Category) {
		this.Category=Category;
	}
	
	public String getEdition() {
		return this.Edition;
	}
	public void setEdition(String Edition) {
		this.Edition=Edition;
	}
	
	public String getCopyIDe() {
		return this.CopyID;
	}
	public void setCopyID(String CopyID) {
		this.CopyID=CopyID;
	}
	
	public String getRentedBy() {
		return this.RentedBy;
	}
	public void setRendedBy(String RentedBy) {
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

}
