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
		Book b=new Book();
		ManageUsers mu = new ManageUsers();
		ManageBooks mb = new ManageBooks();
		String submitType = request.getParameter("submit");
		String book_display;
		String user_display;
		
		if(submitType.equals("addbook")) {
			request.getRequestDispatcher("addbook.jsp").forward(request, response);
		}else if(submitType.equals("search")){
			request.getRequestDispatcher("search_user.jsp").forward(request, response);
		}else if(submitType.equals("addbookComplete")) {
			b.setISBN(request.getParameter("ISBN"));
			b.setTitle(request.getParameter("title"));
			b.setAuthor(request.getParameter("author"));
			b.setCategory(request.getParameter("category"));
			b.setEdition(request.getParameter("edition"));
			int status = bookDao.addBook(b);
			if(status == 1) {
				request.setAttribute("secondMessage", "Add book success!");
			}else {
				request.setAttribute("secondMessage", "Book is already in the database!!");
			}
			
			book_display = DisplayAll.displayCollection();
			request.setAttribute("displayTable", book_display);
			user_display = mu.displayUsers();
			request.setAttribute("displayTable2", user_display);
			request.getRequestDispatcher("welcome_admin.jsp").forward(request, response);
		}else if(submitType.equals("editbook")) {
			book_display = DisplayAll.displayCollection();
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("editbook.jsp").forward(request, response);
		}else if(submitType.equals("editbookcomplete")) {
			String ISBN = request.getParameter("TargetISBN");
			b.setISBN(request.getParameter("ISBN"));
			b.setTitle(request.getParameter("title"));
			b.setAuthor(request.getParameter("author"));
			b.setCategory("");
			b.setEdition("");
			b.setStatus("");
			int status = mb.editBook(b, ISBN);
			if(status == 1) {
				request.setAttribute("secondMessage", "edit book success!");
			}else {
				request.setAttribute("secondMessage", "Book is not in the database yet!!");
			}
			
			//user_display = mu.displayUsers();
			book_display = DisplayAll.displayCollection();
			request.setAttribute("displayTable", book_display);
			user_display = mu.displayUsers();
			request.setAttribute("displayTable2", user_display);
			request.getRequestDispatcher("welcome_admin.jsp").forward(request, response);
		}else if(submitType.equals("Repair Needed")) {
			String ISBN = request.getParameter("ISBN");
			int status=0;
			if ((bookDao.getBookDetails(ISBN).getStatus().equals("2")) || (bookDao.getBookDetails(ISBN).getStatus().equals("3"))) {
				bookDao.changeStatus(ISBN, 3);
			}
			
			if(status == 1) {
				request.setAttribute("secondMessage", "Book has been sent for repair");
			}else {
				request.setAttribute("secondMessage", "Book has not been returned");
			}
			book_display = DisplayAll.displayCollection(); // should just display returned books
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("processreturn.jsp").forward(request, response);
		}else if(submitType.equals("Ruined")) {
			String ISBN = request.getParameter("ISBN");
			int status=0;
			if ((bookDao.getBookDetails(ISBN).getStatus().equals("2")) || (bookDao.getBookDetails(ISBN).getStatus().equals("3"))) {
				bookDao.changeStatus(ISBN, 4);
			}
			
			if(status == 1) {
				request.setAttribute("secondMessage", "Book has been sent for repair");
			}else {
				request.setAttribute("secondMessage", "Book has not been returned");
			}
			book_display = DisplayAll.displayCollection(); // should just display returned books
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("processreturn.jsp").forward(request, response);
		}else if(submitType.equals("Presentable")) {
			String ISBN = request.getParameter("ISBN");
			int status=0;
			if ((bookDao.getBookDetails(ISBN).getStatus().equals("2")) || (bookDao.getBookDetails(ISBN).getStatus().equals("3"))) {
				bookDao.changeStatus(ISBN, 0);
			}
			
			if(status == 1) {
				request.setAttribute("secondMessage", "Book has been sent for repair");
			}else {
				request.setAttribute("secondMessage", "Book has not been returned");
			}
			book_display = DisplayAll.displayCollection(); // should just display returned books
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("processreturn.jsp").forward(request, response);
		}else if(submitType.equals("process returned books")) {
			//
			book_display = DisplayAll.displayCollection(); // should just display returned books
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("processreturn.jsp").forward(request, response);
		}else if(submitType.equals("Return to home page")) {
			book_display = DisplayAll.displayCollection(); // 
			request.setAttribute("displayTable", book_display);
			user_display = mu.displayUsers();
			request.setAttribute("displayTable2", user_display);
			request.getRequestDispatcher("welcome_admin.jsp").forward(request, response);
		}
	}

}
