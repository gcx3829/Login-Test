package domain.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import collection.LibraryImpl;
import collection.Book;
import collection.collection;

/**
 * Servlet implementation class Login
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public LoginController() {}
    
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CustomerDao customerDao = new CustomerDaoImpl();
		
		String username = request.getParameter("username");
		request.getSession().setAttribute("username", username);
		String pass = request.getParameter("password");
		String submitType = request.getParameter("submit");
		String ISBN = request.getParameter("ISBN");
		Login login = new Login(username, pass);
		Customer c = customerDao.validateCustomer(login);
		Book b = LibraryImpl.getBook();
		//test for showing book detail
		collection col = new collection(ISBN, (String)request.getSession().getAttribute("username"));
		if(submitType.equals("login") && c!=null && c.getName()!=null){
			request.setAttribute("message", "Hello "+c.getName());
			
			if(c.getUsertype()==1) {
				request.setAttribute("secondMessage", "Hello Administrator");}
			//extra information for administrator 
			
			request.setAttribute("bookMessage", b.getISBN()+" "+b.getAuthor()+" "+b.getTitle()+" "+b.getStatus()+"<br>");
			//test for showing book detail
			
			request.getRequestDispatcher("welcome.jsp").forward(request, response);
		}else if(submitType.equals("register")){
			c.setName(request.getParameter("name"));
			c.setUsername(request.getParameter("username"));
			c.setPassword(request.getParameter("password"));
			c.setUsertype( Integer.parseInt(request.getParameter("userType")));
			customerDao.register(c);
			request.setAttribute("successMessage", "Registration done, please login!");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}else if(submitType.equals("Check Out")){
			int status = LibraryImpl.checkout(col);
			//check out function using collection class
			if(status == 1) {
				request.setAttribute("secondMessage", "Check out success!");
			}else {
				request.setAttribute("secondMessage", "Book is not available now! Please select another one!");
			}
			request.getRequestDispatcher("welcome.jsp").forward(request, response);
		}else{
			request.setAttribute("message", "Data Not Found! Please register!");
			request.getRequestDispatcher("register.jsp").forward(request, response);
		}

	}

}
