package domain.search;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.search.DisplayAll;
import domain.user.UserDaoImpl;
import domain.user.UserDao;
import domain.user.User;
import domain.book.*;
import domain.manage.ManageBooks;
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
		int usertype = (int) request.getSession().getAttribute("usertype");
		
		if(submitType.equals("Search")) { // new code
			//Search function
			b.setISBN(request.getParameter("ISBN"));
			b.setTitle(request.getParameter("title"));
			b.setAuthor(request.getParameter("author"));
			b.setCategory(request.getParameter("category"));
			b.setEdition(request.getParameter("edition"));
			
			book_table = search.search(b, usertype);
			request.setAttribute("displayTable", book_table);
			request.getRequestDispatcher("search_user.jsp").forward(request, response);	// end of new code		
		}else{
			if (usertype == 1) {
				String user_display;
				ManageUsers mu = new ManageUsers();
				user_display = DisplayAll.displayCollection();
				request.setAttribute("displayTable", user_display);
				user_display = mu.displayUsers(); // new code
				request.setAttribute("displayTable2", user_display); // new code
				request.getRequestDispatcher("welcome_admin.jsp").forward(request, response);
			}else {
				String user_display;
				user_display = DisplayAll.displayCollection();
				request.setAttribute("displayTable", user_display);
				request.getRequestDispatcher("welcome_user.jsp").forward(request, response);

			}
		}
	}
}
