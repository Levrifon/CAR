package car.tp4;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(name="Add", urlPatterns = "/add")
public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private BookManager bookBean;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String author = (String)request.getAttribute("author");
		String title = (String)  request.getAttribute("title");
		String year = (String)  request.getAttribute("year");
		bookBean.addBook(new Book(author,title,year));
		List<String> l=  bookBean.getAllAuthors();
		request.setAttribute("bookAuthor", l);
		 this.getServletContext().getRequestDispatcher( "/add.jsp" ).forward( request, response );
	}
}
