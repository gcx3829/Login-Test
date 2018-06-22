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
		String user_display;
		//copy col = new copy(ISBN, (String)request.getSession().getAttribute("username"));
		if(submitType.equals("Check Out")){
			int status = checkOut.checkout(ISBN);
			//check out function using collection class
			if(status == 1) {
				String text = "Book "+ ISBN + " Check out success!";
				request.setAttribute("secondMessage", text);
			}else {
				request.setAttribute("secondMessage", "Book is not available now! Please select another one!");
			}
			user_display = DisplayAll.displayCollection();
			request.setAttribute("displayTable", user_display);
			request.getRequestDispatcher("welcome_user.jsp").forward(request, response);
		}else if(submitType.equals("Return")){
			int status = returnBook.returnBook(ISBN);
			//check out function using collection class
			if(status == 1) {
				String text = "Book "+ ISBN + " Return success!";
				request.setAttribute("secondMessage", text);
			}else {
				request.setAttribute("secondMessage", "Book is not Checked Out!");
			}
			user_display = DisplayAll.displayCollection();
			request.setAttribute("displayTable", user_display);
			request.getRequestDispatcher("welcome_user.jsp").forward(request, response);
		}

	}

}

