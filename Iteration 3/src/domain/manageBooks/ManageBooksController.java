package domain.manageBooks;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.search.Search;
import domain.search.SearchBookDetails;
import domain.search.SearchUsers;
import domain.book.*;

/**
* Servlet implementation class Login
*/
@WebServlet("/ManageBooksController")
public class ManageBooksController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ManageBooksController() {}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BookDao bookDao = new BookDaoImpl();
		String submitType = request.getParameter("submit");
		String book_display;
		String user_display;
		
		if(submitType.equals("addbook")) {
			Search sb = new SearchBookDetails("","","","","","","");
			book_display = sb.find();
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("addbook.jsp").forward(request, response);
		}else if(submitType.equals("add new book")) {
			
			Book newBook = new Book(request.getParameter("ISBN"), request.getParameter("title"),
					request.getParameter("author"), request.getParameter("genre"), request.getParameter("edition")); 
			String text = bookDao.addNewBook(newBook);
			request.setAttribute("secondMessage", text);
			Search sb = new SearchBookDetails("","","","","","","");
			book_display = sb.find();
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("addbook.jsp").forward(request, response);
		}else if(submitType.equals("editbook")) {
			Search sb = new SearchBookDetails("","","","","","","");
			book_display = sb.find();
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("editbook.jsp").forward(request, response);
		}else if(submitType.equals("Edit Book")) {
			String text;
			//code to edit the status and everything else in the book object
			Book book = bookDao.getBook(request.getParameter("TargetInventoryID"));
			if (book.getTitle()==null) {
				text = "Book with Inventory ID " + request.getParameter("TargetInventoryID") + " is not in the database!!";
			} else {
				text = book.editAll(request.getParameter("ISBN"), 
						request.getParameter("title"), request.getParameter("author"),
						request.getParameter("genre"), request.getParameter("edition"), 
						request.getParameter("Status"),request.getParameter("RentedBy"),
						request.getParameter("CheckOutDate"),request.getParameter("ReturnByDate"));
			}
			request.setAttribute("secondMessage", text);
			Search sb = new SearchBookDetails("","","","","","","");
			book_display = sb.find();
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("editbook.jsp").forward(request, response);
		}else if(submitType.equals("Edit Title")) {
			String text;
			
			Book book = bookDao.getTitle(request.getParameter("TargetISBN"));
			if (book.getTitle()==null) {
				text = "Book with ISBN " + request.getParameter("TargetISBN") + " is not in the database!!";
			}
			else {
				text = book.editTitle(request.getParameter("ISBN"), request.getParameter("title"),
						request.getParameter("author"), request.getParameter("genre"), request.getParameter("edition"));
			}
			request.setAttribute("secondMessage", text);
			Search sb = new SearchBookDetails("","","","","","","");
			book_display = sb.find();
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("editbook.jsp").forward(request, response);
		}else if(submitType.equals("Search")) {
			request.getRequestDispatcher("search_admin.jsp").forward(request, response);
		}else if (submitType.equals("Return to Home page")){
			Search sb = new SearchBookDetails("","","","","","","");
			book_display = sb.find();
			request.setAttribute("displayTable", book_display);
			Search s = new SearchUsers(); //new code
			user_display = s.find(); // new code
			request.setAttribute("displayTable2", user_display); // new code
			request.getRequestDispatcher("welcome_admin.jsp").forward(request, response);
		}
	}

}
