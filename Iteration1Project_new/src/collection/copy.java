package collection;

public class copy {
	private String ISBN;
	private String CopyID;
	private String Status;
	private String RentedBy;
	private String CheckOutDate;
	private String ReturnByDate;
	
	public copy(String ISBN, String username) {
		this.ISBN = ISBN;
		this.RentedBy = username;
	}
	
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}
	public String getCopyID() {
		return CopyID;
	}
	public void setTitle(String CopyID) {
		this.CopyID = CopyID;
	}
	public String getStatus() {
		return Status;
	}
	public void setAuthor(String Status) {
		this.Status = Status;
	}
	public String getRentedBy() {
		return RentedBy;
	}
	public void setStatus(String RentedBy) {
		this.RentedBy = RentedBy;
	}
	public String getCheckOutDate() {
		return CheckOutDate;
	}
	public void setCheckOutDate(String CheckOutDate) {
		this.CheckOutDate = CheckOutDate;
	}
	public String getReturnByDate() {
		return ReturnByDate;
	}
	public void setReturnByDate(String ReturnByDate) {
		this.ReturnByDate = ReturnByDate;
	}
}