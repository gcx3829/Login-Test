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
		
		ManageBooks mb = new ManageBooks();
		ManageUsers mu = new ManageUsers();
		
		//int type = 0; // replace this code with something that gets userType
		String username = request.getSession().getAttribute("username").toString();
		User user = userDao.getTypeDB(username);
		int type = user.getUsertype();
		
		if (type==0) {
			if(submitType.equals("Search")) { // new code
				//Search function
				b.setISBN(request.getParameter("ISBN"));
				b.setTitle(request.getParameter("title"));
				b.setAuthor(request.getParameter("author"));
				//b.setCategory("");
				//b.setEdition("");
				book_table = search.search(b);
				request.setAttribute("displayTable", book_table);
				request.getRequestDispatcher("search_user.jsp").forward(request, response);	// end of new code		
			}else if(submitType.equals("Return to Home page")) {
				String user_display;
				user_display = DisplayAll.displayCollection();
				request.setAttribute("displayTable", user_display);
				request.getRequestDispatcher("welcome_user.jsp").forward(request, response);
			}
		}
		else {
			//code for admin user
		}
	}
}
