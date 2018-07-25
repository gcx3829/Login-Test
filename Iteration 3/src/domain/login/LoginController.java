package domain.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import domain.user.*;
import domain.waitList.WaitListDao;
import domain.waitList.WaitListDaoImpl;
//import domain.manage.ManageUsers;
//import domain.search.DisplayAll;
//import domain.search.OldSearch;
import domain.search.Search;
import domain.search.SearchBookDetails;
import domain.search.SearchUsers;

/**
 * Servlet implementation class Login
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public LoginController() {}
    
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserDao userDao = new UserDaoImpl();
		
		String username = request.getParameter("username");
		request.getSession().setAttribute("username", username);
		String pass = request.getParameter("password");
		String submitType = request.getParameter("submit");
		Login login = new Login(username, pass);
		User u = userDao.validateUser(login);
		request.setAttribute("user", u);
		request.getSession().setAttribute("user", u);
		//User ub = (User)request.getSession().getAttribute("user"); // code for getting user object in session
		String user_display;
		WaitListDao waitListDao = new WaitListDaoImpl();
		waitListDao.sleepingUser();
		if(submitType.equals("login") && u!=null && u.getName()!=null){
			request.setAttribute("message", "Hello "+u.getName());
			if(u.getUsertype()==1) {
				Search sb = new SearchBookDetails("","","","","","","");
				String book_display = sb.find();
				request.setAttribute("displayTable", book_display);
				Search su = new SearchUsers();
				user_display = su.find();
				request.setAttribute("displayTable2", user_display); // new code
				request.setAttribute("secondMessage", "Hello Administrator");
				request.getRequestDispatcher("welcome_admin.jsp").forward(request, response);
				}
			else {
				u.setRentedBooks();
				String OverDue_display = u.displayOverdue();
				request.setAttribute("displayTable1", OverDue_display);
				request.setAttribute("overdueMessage", u.getTotalOverdueFees(1));
				user_display = u.displayCheckedOutBooks();
				request.setAttribute("displayTable", user_display);
				request.setAttribute("displayTable2", u.getWaitlistPositions());
				request.getRequestDispatcher("welcome_user.jsp").forward(request, response);
			}
		}else if(submitType.equals("register")){
			u.setName(request.getParameter("name"));
			u.setUsername(request.getParameter("username"));
			u.setPassword(request.getParameter("password"));
			u.setUsertype( Integer.parseInt(request.getParameter("userType")));
			userDao.register(u);
			request.setAttribute("successMessage", "Registration done, please login!");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}else{
			request.setAttribute("message", "Data Not Found! Please register!");
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}

	}

}
