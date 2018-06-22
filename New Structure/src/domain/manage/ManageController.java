package domain.manage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.search.DisplayAll;
import domain.book.*;

/**
* Servlet implementation class Login
*/
@WebServlet("/ManageController")
public class ManageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ManageController() {}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BookDao bookDao = new BookDaoImpl();
		Title t=new Title();
		String submitType = request.getParameter("submit");
		String user_display;
		
		if(submitType.equals("addbook")) {
			request.getRequestDispatcher("addbook.jsp").forward(request, response);
		}else if(submitType.equals("addbookComplete")) {
			t.setISBN(request.getParameter("ISBN"));
			t.setTitle(request.getParameter("title"));
			t.setAuthor(request.getParameter("author"));
			t.setCategory("null");
			t.setEdition("null");
			int status = bookDao.addBook(t);
			if(status == 1) {
				request.setAttribute("secondMessage", "Add book success!");
			}else {
				request.setAttribute("secondMessage", "Book is already in the database!!");
			}
			
			user_display = DisplayAll.displayCollection();
			request.setAttribute("displayTable", user_display);
			request.getRequestDispatcher("welcome_admin.jsp").forward(request, response);
		}
	}

}
