package domain.useLoan;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.user.*;

/**
* Servlet implementation class Login
*/
@WebServlet("/LoanController")
public class LoanController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      

   public LoanController() {}
   
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String submitType = request.getParameter("submit");
		//Book b = new Book(request.getParameter("ISBN"), "ISBN");
		User u = (User)request.getSession().getAttribute("user"); //get user details again
		//User u = new User();
		//u.setUsername((String) request.getSession().getAttribute("username"));
		String user_display;
		//copy col = new copy(ISBN, (String)request.getSession().getAttribute("username"));
		if(submitType.equals("Check Out")){
			//String text = checkOut.checkout(b, u);
			//String text = lib.checkOut(b, u);
			String text = u.checkOutBook(request.getParameter("ISBN"));
			request.setAttribute("secondMessage", text);
			/*
			// *** CODE THAT NEEDS CHANGE START ***
			int status = checkOut.checkout(b, u);
			//check out function using collection class
			// *** CODE THAT NEEDS CHANGE END ***
			if(status == 1) {
				String text = "Book "+ b.getISBN() + " Check out success!";
				request.setAttribute("secondMessage", text);
			}else if(status == 2) {
				String text = "Book "+ b.getISBN() + " Not found in database";
				request.setAttribute("secondMessage", text);
			}else if(status == 0) {
				request.setAttribute("secondMessage", "Book is not available now! Please select another one!");
			}else if(status == 3) {
				String text = "Book "+ b.getISBN() + " Check out failed!!";
				request.setAttribute("secondMessage", text);
			}else if(status == 4 ) {
				String text = "You already rent Book " +b.getISBN()+" !!! Check out failed";
				request.setAttribute("secondMessage", text);
			}
			*/
			//Search sb = new SearchCheckedout(u.getUsername());
			//user_display = sb.find();
			u.setRentedBooks();
			user_display = u.displayBooks();
			request.setAttribute("displayTable", user_display);
			//user_display = DisplayAll.displayCollection();
			request.setAttribute("displayTable", user_display);
			request.getRequestDispatcher("welcome_user.jsp").forward(request, response);
		}else if(submitType.equals("Return")){
			//String text = returnBook.returnBook(b, u);
			//String text = lib.returnBook(b, u);
			String text = u.returnBook(request.getParameter("ISBN"));
			request.setAttribute("secondMessage", text);
			/*
			// *** CODE THAT NEEDS CHANGE START ***
			int status = returnBook.returnBook(b, u);
			// *** CODE THAT NEEDS CHANGE END ***
			if(status == 1) {
				String text = "Book "+ b.getISBN() + " Return success!";
				request.setAttribute("secondMessage", text);
			}else if(status == 2) {
				String text = "Book "+ b.getISBN() + " Not found in database";
				request.setAttribute("secondMessage", text);
			}else if(status == 0) {
				request.setAttribute("secondMessage", "You did not rent book "+b.getISBN()+" !!! Return Failed!!!");
			}else if(status == 3) {
				String text = "Book "+ b.getISBN() + " Check out failed!!";
				request.setAttribute("secondMessage", text);
			}
			*/
			//String username = request.getSession().getAttribute("username").toString();
			//Search sb = new SearchCheckedout(username);
			//user_display = sb.find();
			user_display = u.displayBooks();
			request.setAttribute("displayTable", user_display);
			//user_display = DisplayAll.displayCollection();
			request.setAttribute("displayTable", user_display);
			request.getRequestDispatcher("welcome_user.jsp").forward(request, response);
		}else if(submitType.equals("Search and Check Out")){
			request.getRequestDispatcher("search_user.jsp").forward(request, response);
		}

	}

}
