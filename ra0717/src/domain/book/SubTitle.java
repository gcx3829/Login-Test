package domain.book;

public class SubTitle extends Title {
	String ISBN;
	
	
	public SubTitle() {
		super("");
		super.getAuthor();
		
		BookDao bookDao = new BookDaoImpl();
	}
	
	@Override
	public String getISBN() {
		return ISBN;
	}

}
