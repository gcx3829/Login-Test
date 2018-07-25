package domain.handleReturnedBooks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
@WebServlet("/ProcessReturnsController")
public class ProcessReturnsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ProcessReturnsController() {}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BookDao bookDao = new BookDaoImpl();
		String submitType = request.getParameter("submit");
		String book_display;
		String user_display;
		String text;
		
		if(submitType.equals("Repair Needed")) {
			Book b = bookDao.getBook(request.getParameter("InventoryID"));
			if (b.getStatus()==null) {text = "Book with Inventory ID " + b.getInventoryID() + " does not exist!"; } //
			else {text = b.process(3);}
			request.setAttribute("secondMessage", text);
			List<String> returnable = new ArrayList<String>();
			returnable.add("2");returnable.add("3");
			Search sb = new SearchBookDetails("Status", returnable);
			book_display = sb.find();
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("processreturn.jsp").forward(request, response);
		}else if(submitType.equals("Ruined")) {
			Book b = bookDao.getBook(request.getParameter("InventoryID"));
			if (b.getStatus()==null) {text = "Book with Inventory ID " + b.getInventoryID() + " does not exist!"; } //
			else {text = b.process(4);}
			request.setAttribute("secondMessage", text);
			List<String> returnable = new ArrayList<String>();
			returnable.add("2");returnable.add("3");
			Search sb = new SearchBookDetails("Status", returnable);
			book_display = sb.find();
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("processreturn.jsp").forward(request, response);
		}else if(submitType.equals("Presentable")) {
			Book b = bookDao.getBook(request.getParameter("InventoryID"));
			if (b.getStatus()==null) {text = "Book with Inventory ID " + b.getInventoryID() + " does not exist!"; } //
			else {text = b.process(1);}
			request.setAttribute("secondMessage", text);
			List<String> returnable = new ArrayList<String>();
			returnable.add("2");returnable.add("3");
			Search sb = new SearchBookDetails("Status", returnable);
			book_display = sb.find();
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("processreturn.jsp").forward(request, response);
		}else if(submitType.equals("process returned books")) {
			List<String> returnable = new ArrayList<String>();
			returnable.add("2");returnable.add("3");
			Search sb = new SearchBookDetails("Status", returnable);
			book_display = sb.find();
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("processreturn.jsp").forward(request, response);
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