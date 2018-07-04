package domain.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import domain.user.*;
import domain.search.DisplayAll;

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
		//Library library = new Library();
		String user_display;
		if(submitType.equals("login") && u!=null && u.getName()!=null){
			request.setAttribute("message", "Hello "+u.getName());
			
			
			//extra information for administrator 
			
			//test for showing book detail
			
			if(u.getUsertype()==1) {
				user_display = DisplayAll.displayCollection();
				request.setAttribute("displayTable", user_display);
				request.setAttribute("secondMessage", "Hello Administrator");
				request.getRequestDispatcher("welcome_admin.jsp").forward(request, response);
				}
			else {
				user_display = DisplayAll.displayCollection();
				request.setAttribute("displayTable", user_display);
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
