package domain.search;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.user.UserDaoImpl;
import domain.user.UserDao;
import domain.user.User;
import domain.book.*;
import domain.manage.ManageUsers;

/**
* Servlet implementation class Login
*/
@WebServlet("/SearchController")
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public SearchController() {}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Book b=new Book();
		String submitType = request.getParameter("submit");
		Search search = new Search();
		String book_table;
		UserDao userDao = new UserDaoImpl();
		
		//int type = 0; // replace this code with something that gets userType
		String username = request.getSession().getAttribute("username").toString();
		User user = userDao.getTypeDB(username);
		int usertype = user.getUsertype();
		//int usertype = Integer.parseInt(request.getSession().getAttribute("usertype").toString());
		
		if(submitType.equals("Search")) { // new code
			//Search function
			b.setNull();
			b.setISBN(request.getParameter("ISBN"));
			b.setTitle(request.getParameter("title"));
			b.setAuthor(request.getParameter("author"));
			b.setCategory(request.getParameter("category"));
			b.setEdition(request.getParameter("edition"));
			String dest;
			if (usertype == 1) {
				b.setCopyID(request.getParameter("CopyID"));
				b.setStatus(request.getParameter("Status"));
				b.setRentedBy(request.getParameter("RentedBy"));
				b.setCheckOutDate(request.getParameter("CheckOutDate"));
				b.setReturnByDate(request.getParameter("ReturnByDate"));
				book_table = search.displayBooks(search.search(b));
				dest = "search_admin.jsp";
			} else {
				book_table = search.displayTitles(search.search(b));
				dest = "search_user.jsp";
			}
			request.setAttribute("displayTable", book_table);
			request.getRequestDispatcher(dest).forward(request, response);	// end of new code		
		}else if (submitType.equals("Return to Home page")){
			if (usertype == 1) {
				String user_display;
				ManageUsers mu = new ManageUsers();
				b.setNull();
				user_display = search.displayBooks(search.search(b));
				request.setAttribute("displayTable", user_display);
				user_display = mu.displayUsers(); // new code
				request.setAttribute("displayTable2", user_display); // new code
				request.getRequestDispatcher("welcome_admin.jsp").forward(request, response);
			}else {
				String user_display;
				b.setNull();
				user_display = search.displayTitles(search.search(b));
				request.setAttribute("displayTable", user_display);
				request.getRequestDispatcher("welcome_user.jsp").forward(request, response);

			}
		}else{
			if (usertype == 1) {
				String user_display;
				ManageUsers mu = new ManageUsers();
				b.setNull();
				user_display = search.displayBooks(search.search(b));
				request.setAttribute("displayTable", user_display);
				user_display = mu.displayUsers(); // new code
				request.setAttribute("displayTable2", user_display); // new code
				request.getRequestDispatcher("welcome_admin.jsp").forward(request, response);
			}else {
				String user_display;
				b.setNull();
				user_display = search.displayTitles(search.search(b));
				request.setAttribute("displayTable", user_display);
				request.getRequestDispatcher("welcome_user.jsp").forward(request, response);

			}
		}
	}
}