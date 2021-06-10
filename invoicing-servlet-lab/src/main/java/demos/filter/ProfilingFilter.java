package demos.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName="profiler", servletNames = {"ShoppingServlet"}) 
//dispatcherTypes={DispatcherType.REQUEST})
@Slf4j
public class ProfilingFilter implements Filter {
	ServletContext ctx;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		long start = System.nanoTime();
		chain.doFilter(request, response);
		long end = System.nanoTime();
		HttpServletRequest r = (HttpServletRequest) request;
		log.info("PROFILING " + r.getServletPath()
				+ " with METHOD " + r.getMethod() + ": Request processing time =  "
				+ (end - start) + " ns");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ctx = filterConfig.getServletContext();
	}

}
