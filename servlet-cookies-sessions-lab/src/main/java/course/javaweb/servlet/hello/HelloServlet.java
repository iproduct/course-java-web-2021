package course.javaweb.servlet.hello;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet(name = "HelloServlet", value = "/hello", initParams = {
        @WebInitParam(name="color", value = "blue")
})

public class HelloServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Hello MVC Servlet World!</title>");
        out.println("</head>");
        out.printf("<body style=\"background-color: %s\">%n",
                getServletContext().getInitParameter("bgColor"));
        out.printf("<h1 style=\"color: %s\">%s</h1>%n", getInitParameter("color"),
                getServletContext().getInitParameter("appName"));
        out.println("Method: " + request.getMethod() + "<br>");
        out.println("Request URI: " + request.getRequestURI() + "<br>");
        out.println("Protocol: " + request.getProtocol() + "<br>");
        out.println("Context path: " + request.getContextPath() + "<br>");
        out.println("Servlet path: " + request.getServletPath() + "<br>");
        out.println("PathInfo: " + request.getPathInfo() + "<br>");
        out.println("Remote Address: " + request.getRemoteAddr() + "<br>");
        Enumeration e = request.getHeaderNames();
        out.println("<table>");
        while (e.hasMoreElements()) {
            out.println("<tr><td>");
            String name = (String) e.nextElement();
            out.print(name);
            out.print("</td><td>");
            String value = request.getHeader(name);
            out.print(value);
            out.println("</td></tr>");
        }
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }
}
