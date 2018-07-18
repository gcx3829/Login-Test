package domain.manage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.search.DisplayAll;
import domain.search.OldSearch;
import domain.search.Search;
import domain.search.SearchBookDetails;
import domain.search.SearchUsers;
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
		OldSearch search = new OldSearch();
		String submitType = request.getParameter("submit");
		String book_display;
		String user_display;
		
		if(submitType.equals("addbook")) {
			request.getRequestDispatcher("addbook.jsp").forward(request, response);
		}else if(submitType.equals("addbookComplete")) {
			// *** CODE THAT NEEDS CHANGE START ***
			/*
			b.setISBN(request.getParameter("ISBN"));
			b.setTitle(request.getParameter("title"));
			b.setAuthor(request.getParameter("author"));
			b.setGenre(request.getParameter("category"));
			b.setEdition(request.getParameter("edition"));
			int status = bookDao.addBook(b);
			// *** CODE THAT NEEDS CHANGE END ***
			if(status == 1) {
				request.setAttribute("secondMessage", "Add book success!");
			}else {
				request.setAttribute("secondMessage", "Book is already in the database!!");
			}
			*/
			Book newBook = new Book(request.getParameter("ISBN"), request.getParameter("title"),
					request.getParameter("author"), request.getParameter("category"), request.getParameter("edition"));
			AddBook ab = new AddBook();
			String text = ab.add(newBook);
			request.setAttribute("secondMessage", text);
			Search sb = new SearchBookDetails("","","","","","","");
			book_display = sb.find();
			request.setAttribute("displayTable", book_display);
			Search su = new SearchUsers(); //new code
			user_display = su.find(); // new code
			request.setAttribute("displayTable2", user_display);
			request.getRequestDispatcher("welcome_admin.jsp").forward(request, response);
		}else if(submitType.equals("editbook")) {
			Search sb = new SearchBookDetails("","","","","","","");
			book_display = sb.find();
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("editbook.jsp").forward(request, response);
		}else if(submitType.equals("editbookcomplete")) {
			// *** CODE THAT NEEDS CHANGE START ***
			EditBooks eb = new EditBooks();
			Book newValues = new Book(request.getParameter("ISBN"), request.getParameter("title"),
					request.getParameter("author"), request.getParameter("category"), request.getParameter("edition"));
			
			String text = eb.edit(request.getParameter("TargetISBN"), request.getParameter("ISBN"),
					request.getParameter("title"), request.getParameter("author"), 
					request.getParameter("category"), request.getParameter("edition"));
			request.setAttribute("secondMessage", text);
			/*
			String ISBN = request.getParameter("TargetISBN");
			String InventoryID = request.getParameter("TargetInventoryID");
			b.setISBN(request.getParameter("ISBN"));
			b.setTitle(request.getParameter("title"));
			b.setAuthor(request.getParameter("author"));
			b.setGenre(request.getParameter("category"));
			b.setEdition(request.getParameter("edition"));
			b.setInventoryID(request.getParameter("CopyID"));
			b.setRentedBy(request.getParameter("RentedBy"));
			b.setCheckOutDate(request.getParameter("CheckOutDate"));
			b.setReturnByDate(request.getParameter("ReturnByDate"));
			b.setStatus(request.getParameter("Status"));
			int status=0;
			if (InventoryID.isEmpty()) { //if statement code for copyID stuff; gone with inventoryID
				status = eb.editTitle(b, ISBN);
			} else {
				status = eb.editCopy(b, ISBN, InventoryID);
			}
			// *** CODE THAT NEEDS CHANGE END ***
			
			if(status == 1) {
				request.setAttribute("secondMessage", "edit book success!");
			}else {
				request.setAttribute("secondMessage", "Book is not in the database yet!!");
			}
			*/
			//user_display = mu.displayUsers();
			Search sb = new SearchBookDetails("","","","","","","");
			book_display = sb.find();
			request.setAttribute("displayTable", book_display);
			Search su = new SearchUsers(); //new code
			user_display = su.find(); // new code
			request.setAttribute("displayTable2", user_display);
			request.getRequestDispatcher("welcome_admin.jsp").forward(request, response);
		}else if(submitType.equals("Repair Needed")) {
			ProcessReturn pr = new ProcessReturn();
			// *** CODE THAT NEEDS CHANGE START ***
			request.setAttribute("secondMessage", pr.processReturn(request.getParameter("InventoryID"), 2));
			// *** CODE THAT NEEDS CHANGE END ***
			List<String> returnable = new ArrayList<String>();
			returnable.add("2");returnable.add("3");
			Search sb = new SearchBookDetails("Status", returnable);
			book_display = sb.find();
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("processreturn.jsp").forward(request, response);
		}else if(submitType.equals("Ruined")) {
			// *** CODE THAT NEEDS CHANGE START ***
			ProcessReturn pr = new ProcessReturn();
			// *** CODE THAT NEEDS CHANGE END ***
			request.setAttribute("secondMessage", pr.processReturn(request.getParameter("InventoryID"), 3));
			List<String> returnable = new ArrayList<String>();
			returnable.add("2");returnable.add("3");
			Search sb = new SearchBookDetails("Status", returnable);
			book_display = sb.find();
			request.setAttribute("displayTable", book_display);
			request.getRequestDispatcher("processreturn.jsp").forward(request, response);
		}else if(submitType.equals("Presentable")) {
			/*String ISBN = request.getParameter("ISBN");
			int status=0;
			change call for book details; need to get copy you are talking about
			b.setNull();
			b.setISBN(ISBN);
			Search s = new Search();
			if ((bookDao.copyExists(ISBN, s.getCopyID(ISBN, "2")))==1) {
				status = bookDao.changeStatus(ISBN, s.getCopyID(ISBN, "2"), 1);
			} else if ((bookDao.copyExists(ISBN, s.getCopyID(ISBN, "3")))==1) {
				status = bookDao.changeStatus(ISBN, s.getCopyID(ISBN, "3"), 1);
			}
			
			if(status == 1) {
				request.setAttribute("secondMessage", "Book has been made available for Library patrons");
			}else {
				request.setAttribute("secondMessage", "Book has not been returned");
			} */
			// *** CODE THAT NEEDS CHANGE START ***
			ProcessReturn pr = new ProcessReturn();
			request.setAttribute("secondMessage", pr.processReturn(request.getParameter("InventoryID"), 1));
			// *** CODE THAT NEEDS CHANGE END ***
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
		}else if(submitType.equals("Return to Home page")) {
			//book_display = DisplayAll.displayCollection(); // 
			Search sb = new SearchBookDetails("","","","","","","");
			book_display = sb.find();
			request.setAttribute("displayTable", book_display);
			Search su = new SearchUsers(); //new code
			user_display = su.find(); // new code
			request.setAttribute("displayTable2", user_display); 
			request.getRequestDispatcher("welcome_admin.jsp").forward(request, response);
		}
	}

}
