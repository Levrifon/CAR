package car.tp4;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Init", urlPatterns = "/init")
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private BookManager bookBean;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		bookBean.init();
		this.getServletContext().getRequestDispatcher("/init.jsp")
				.forward(request, response);
	}

}
