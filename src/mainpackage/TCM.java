package mainpackage;

import java.io.*; 

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import coservlets.LinkFinder;

@WebServlet("/DisplayResults")
public class TCM extends HttpServlet {
	@Override
	  public void doGet(HttpServletRequest request,
	                    HttpServletResponse response)
	      throws ServletException, IOException {
		response.setContentType("text/html");
		String searchString = request.getParameter("searchstring");
		String type = request.getParameter("type");
		LinkFinder lf = new LinkFinder();
		String results = new String();
		try {
			results = lf.searchString(searchString,type);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		out.println(results);
	  }
}
