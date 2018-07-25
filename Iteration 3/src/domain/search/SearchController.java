package domain.search;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.user.User;

/**
* Servlet implementation class Login
*/
@WebServlet("/SearchController")
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public SearchController() {}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String submitType = request.getParameter("submit");
		String book_table;
		
		User u = (User)request.getSession().getAttribute("user");
		int usertype = u.getUsertype();
		
		if(submitType.equals("Search")) { // new code
			Search s = new SearchTitles(request.getParameter("ISBN"), request.getParameter("title"),
					request.getParameter("author"), request.getParameter("genre"), 
					request.getParameter("edition"));
			book_table = s.find();
			request.setAttribute("displayTable", book_table);
			request.setAttribute("displayTable2", u.getWaitlistPositions());
			request.getRequestDispatcher("search_user.jsp").forward(request, response);	// end of new code	
		}else if (submitType.equals("Search by InventoryID")){
			if (request.getParameter("InventoryID").length()>0) {
				Search s = new SearchBookDetails(request.getParameter("InventoryID"));
				book_table = s.find();
				request.setAttribute("displayTable", book_table);
			} else { request.setAttribute("displayTable", "<tr><th>Please enter an InventoryID</th></tr>"); }
			request.getRequestDispatcher("search_admin.jsp").forward(request, response);
		}else if (submitType.equals("Search by Parameters")){
			Search s = new SearchBookDetails(request.getParameter("ISBN"), request.getParameter("title"),
					request.getParameter("author"), request.getParameter("genre"), 
					request.getParameter("edition"), request.getParameter("RentedBy"), request.getParameter("Status"));
			book_table = s.find();
			request.setAttribute("displayTable", book_table);
			request.getRequestDispatcher("search_admin.jsp").forward(request, response);
		}else if (submitType.equals("Return to Home page")){
			if (usertype == 1) {
				String user_display;
				Search sb = new SearchBookDetails("","","","","","","");
				String book_display = sb.find();
				request.setAttribute("displayTable", book_display);
				Search s = new SearchUsers();
				user_display = s.find(); 
				request.setAttribute("displayTable2", user_display); 
				request.getRequestDispatcher("welcome_admin.jsp").forward(request, response);
			}else {
				
				u.setRentedBooks();
				String OverDue_display = u.displayOverdue();
				request.setAttribute("displayTable1", OverDue_display);
				request.setAttribute("overdueMessage", u.getTotalOverdueFees(1));
				String user_display = u.displayCheckedOutBooks();
				request.setAttribute("displayTable", user_display);
				request.setAttribute("displayTable2", u.getWaitlistPositions());
				
				request.getRequestDispatcher("welcome_user.jsp").forward(request, response);
			}
		}
	}
}