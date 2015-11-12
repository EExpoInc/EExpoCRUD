package pack;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Teste
 */
@WebServlet("/Teste")
public class Teste extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Teste() {
		super();
		
	}
	
	/** 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		request.setAttribute("before", "123");
//		@SuppressWarnings("rawtypes")
//		ListAction la = new ListAction<> (request, response, new ObjectfyDAO<>(Object.class, Long.class));
		
//		ListAction la = new ListAction<> (request, response, new JpaDAO(PageEntity.class, String.class, Persistence.createEntityManagerFactory("EExpoCrud-JPA2.0").createEntityManager()));
//		ObjectfyDAO dao = new ObjectfyDAO<>(Object.class, Long.class);
//		la.setDAO();
//		la.genTable(); 
//		response.getWriter().print(la.getIdClass());
	} 
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		// TODO Auto-generated method stub
	}
	
}
