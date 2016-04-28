package car.tp4;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(name="Client", urlPatterns = "/client")
public class Client extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@EJB
	private BookItf book;
	
	public void service(HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("text/html");
		PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean res = book.addBook("Test", "Test", "1950");
		if(res){
			out.println("<html><body>Ajout reussi</body></html>");
		} else {
			out.println("<html><body>Ajout non reussi</body></html>");
		}
		
	}
	

}
