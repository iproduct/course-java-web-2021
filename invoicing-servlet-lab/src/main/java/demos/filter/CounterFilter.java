package demos.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicLong;

@WebFilter(filterName = "counter", servletNames = {"ShoppingServlet"})
@Slf4j
public class CounterFilter implements Filter {
    ServletContext ctx;
    AtomicLong counter = new AtomicLong(0L);

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponseWrapper resWrap = new CharResponseWrapper((HttpServletResponse) response);
        chain.doFilter(request, resWrap);
        long count = counter.incrementAndGet();
        PrintWriter out = response.getWriter();
        if (resWrap.toString().indexOf("</body>") > 0) {
            out.write(resWrap.toString().substring(0,
                    resWrap.toString().indexOf("</body>") - 2));
            out.write("<p>" + "Visitor: " + count);
            out.write("</p></body></html>");
        } else {
            out.write(resWrap.toString());
        }
        response.setContentLength(resWrap.toString().getBytes().length);
        out.close();
        HttpServletRequest r = (HttpServletRequest) request;
        log.info("COUNTER: " + count);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ctx = filterConfig.getServletContext();
    }

}

class CharResponseWrapper extends HttpServletResponseWrapper {
    private CharArrayWriter output;

    public String toString() {
        return output.toString();
    }

    public CharResponseWrapper(HttpServletResponse response) {
        super(response);
        output = new CharArrayWriter();
    }

    public PrintWriter getWriter() {
        return new PrintWriter(output);
    }
}

