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
		
		//int type = 0; // replace this code with something that gets userType
		//User user = userDao.getTypeDB(username);
		//int usertype = user.getUsertype();
		//int usertype = Integer.parseInt(request.getSession().getAttribute("usertype").toString());
		User u = (User)request.getSession().getAttribute("user");
		int usertype = u.getUsertype();
		
		if(submitType.equals("Search")) { // new code
			Search s = new SearchTitles(request.getParameter("ISBN"), request.getParameter("title"),
					request.getParameter("author"), request.getParameter("category"), 
					request.getParameter("edition"));
			book_table = s.find();
			request.setAttribute("displayTable", book_table);
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
					request.getParameter("author"), request.getParameter("category"), 
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
				Search s = new SearchUsers(); //new code
				user_display = s.find(); // new code
				request.setAttribute("displayTable2", user_display); // new code
				request.getRequestDispatcher("welcome_admin.jsp").forward(request, response);
			}else {
				//Search sb = new SearchCheckedout(username);
				//String user_display = sb.find();
				String user_display = u.displayBooks();
				request.setAttribute("displayTable", user_display);
				request.getRequestDispatcher("welcome_user.jsp").forward(request, response);

			}
		}/*else{
			if (usertype == 1) {
				String user_display;
				//ManageUsers mu = new ManageUsers();
				//b.setNull();
				//user_display = search.displayBooks(search.search(b));
				//request.setAttribute("displayTable", user_display);
				//user_display = mu.displayUsers(); // new code
				Search sb = new SearchBookDetails("","","","","","","");
				String book_display = sb.find();
				request.setAttribute("displayTable", book_display);
				Search su = new SearchUsers(); //new code
				user_display = su.find(); // new code
				request.setAttribute("displayTable2", user_display); // new code
				request.getRequestDispatcher("welcome_admin.jsp").forward(request, response);
			}else {
				String user_display;
				b.setNull();
				user_display = search.displayTitles(search.search(b));
				request.setAttribute("displayTable", user_display);
				request.getRequestDispatcher("welcome_user.jsp").forward(request, response);
				
			}
		}*/
	}
}