package domain.loan;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String submitType = request.getParameter("submit");
		String ISBN = request.getParameter("ISBN");
		String Userid = (String) request.getSession().getAttribute("username");
		String user_display;
		//copy col = new copy(ISBN, (String)request.getSession().getAttribute("username"));
		if(submitType.equals("Check Out")){
			// *** CODE THAT NEEDS CHANGE START ***
			int status = checkOut.checkout(ISBN,Userid);
			//check out function using collection class
			// *** CODE THAT NEEDS CHANGE END ***
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
			String username = request.getSession().getAttribute("username").toString();
			Search sb = new SearchCheckedout(username);
			user_display = sb.find();
			request.setAttribute("displayTable", user_display);
			//user_display = DisplayAll.displayCollection();
			request.setAttribute("displayTable", user_display);
			request.getRequestDispatcher("welcome_user.jsp").forward(request, response);
		}else if(submitType.equals("Return")){
			// *** CODE THAT NEEDS CHANGE START ***
			int status = returnBook.returnBook(ISBN, Userid);
			// *** CODE THAT NEEDS CHANGE END ***
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
			String username = request.getSession().getAttribute("username").toString();
			Search sb = new SearchCheckedout(username);
			user_display = sb.find();
			request.setAttribute("displayTable", user_display);
			//user_display = DisplayAll.displayCollection();
			request.setAttribute("displayTable", user_display);
			request.getRequestDispatcher("welcome_user.jsp").forward(request, response);
		}else if(submitType.equals("Search and Check Out")){
			request.getRequestDispatcher("search_user.jsp").forward(request, response);
		}

	}

}
