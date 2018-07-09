package domain.loan;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.book.Book;
import domain.search.*;

/**
* Servlet implementation class Login
*/
@WebServlet("/LoanController")
public class LoanController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      

   public LoanController() {}
   
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CheckOut checkOut = new CheckOut();
		ReturnBook returnBook = new ReturnBook();
		Book b = new Book();
		Search s = new Search();
		String submitType = request.getParameter("submit");
		String ISBN = request.getParameter("ISBN");
		String Userid = (String) request.getSession().getAttribute("username");
		String user_display;
		//copy col = new copy(ISBN, (String)request.getSession().getAttribute("username"));
		if(submitType.equals("Check Out")){
			int status = checkOut.checkout(ISBN,Userid);
			//check out function using collection class
			if(status == 1) {
				String text = "Book "+ ISBN + " Check out success!";
				request.setAttribute("secondMessage", text);
			}else if(status == 2) {
				String text = "Book "+ ISBN + " Not found in database";
				request.setAttribute("secondMessage", text);
			}else if(status == 0) {
				request.setAttribute("secondMessage", "Book is not available now! Please select another one!");
			}else if(status == 3) {
				String text = "Book "+ ISBN + " Check out failed!!";
				request.setAttribute("secondMessage", text);
			}else if(status == 4 ) {
				String text = "You already rent Book " +ISBN+" !!! Check out failed";
				request.setAttribute("secondMessage", text);
			}
			b.setNull();
			b.setRentedBy(Userid);
			user_display = s.displayTitles(s.search(b));
			//user_display = DisplayAll.displayCollection();
			request.setAttribute("displayTable", user_display);
			request.getRequestDispatcher("welcome_user.jsp").forward(request, response);
		}else if(submitType.equals("Return")){
			int status = returnBook.returnBook(ISBN, Userid);
			//check out function using collection class
			if(status == 1) {
				String text = "Book "+ ISBN + " Return success!";
				request.setAttribute("secondMessage", text);
			}else if(status == 2) {
				String text = "Book "+ ISBN + " Not found in database";
				request.setAttribute("secondMessage", text);
			}else if(status == 0) {
				request.setAttribute("secondMessage", "You did not rent book "+ISBN+" !!! Return Failed!!!");
			}else if(status == 3) {
				String text = "Book "+ ISBN + " Check out failed!!";
				request.setAttribute("secondMessage", text);
			}
			b.setNull();
			b.setRentedBy(Userid);
			user_display = s.displayTitles(s.search(b));
			//user_display = DisplayAll.displayCollection();
			request.setAttribute("displayTable", user_display);
			request.getRequestDispatcher("welcome_user.jsp").forward(request, response);
		}else if(submitType.equals("Search")){
			request.getRequestDispatcher("search_user.jsp").forward(request, response);
		}

	}

}
