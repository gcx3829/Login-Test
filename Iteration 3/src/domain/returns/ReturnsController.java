package domain.returns;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.search.Search;
import domain.search.SearchTitles;
import domain.user.*;

/**
* Servlet implementation class Login
*/
@WebServlet("/ReturnsController")
public class ReturnsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      

   public ReturnsController() {}
   
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String submitType = request.getParameter("submit");
		User u = (User)request.getSession().getAttribute("user"); //get user details again
		String user_display;
		String overDue_display;
		if(submitType.equals("Return")){
			String text = u.returnBook(request.getParameter("ISBN"));
			request.setAttribute("secondMessage", text);
			
			overDue_display = u.displayOverdue();
			request.setAttribute("displayTable1", overDue_display);
			user_display = u.displayCheckedOutBooks();
			request.setAttribute("displayTable", user_display);
			request.setAttribute("displayTable2", u.getWaitlistPositions());
			request.getRequestDispatcher("welcome_user.jsp").forward(request, response);
		}else if(submitType.equals("Search and Check Out")){
			Search s = new SearchTitles("","","","","");
			String book_table = s.find();
			request.setAttribute("displayTable", book_table);
			request.setAttribute("displayTable2", u.getWaitlistPositions());
			request.getRequestDispatcher("search_user.jsp").forward(request, response);
		}
	}
}
