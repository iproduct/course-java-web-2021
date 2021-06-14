package course.javaweb.servlet.forms;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Servlet implementation class FormServlet
 */
@WebServlet("/form")
@Slf4j
public class FormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title> HTML Form Example</title>");
		out.println("</head>");
		out.println("<body>");
		out.print("<form method='POST'>");
		out.println("First Name:");
		out.println("<input type=text size=20 name=firstname>");
		out.println("<br>");
		out.println("Last Name:");
		out.println("<input type=text size=20 name=lastname>");
		out.println("<br>");
		out.println("<input type=submit>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		log.info("Request Content-Type: " + req.getContentType());
		res.setContentType("text/html;charset=UTF-8");
		PrintWriter out = res.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>HTML Form Example</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<table>");
		Map<String, String[]> params = req.getParameterMap();
		for (Map.Entry<String, String[]> p : params.entrySet()) {
			out.println("<tr><td>" + p.getKey() + ": </td><td>" + 
					String.join("<br>", p.getValue()) + "</td></tr>");
		}
		out.println("</table>");
		out.printf("<a href='%s'>Go to form ...</a>", req.getContextPath()+req.getServletPath());
		out.println("</body>");
		out.println("</html>");
	}

	@Override
	public long getLastModified(HttpServletRequest req){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			return sdf.parse("2021-06-14T14:18:00").getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}

}
