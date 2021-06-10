package demos.servlet;

import demos.dao.BookDBController;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class BookstoreContextListener
 *
 */
@WebListener
@Slf4j
public class BookstoreContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		BookDBController bdc = new BookDBController();
		try {
			bdc.init();
			bdc.reload();
			sce.getServletContext().setAttribute("bookController", bdc);
		} catch (ClassNotFoundException e) {
			log.error("Error initializing  book DB controller: {}", e);
		}
	}

	public void contextDestroyed(ServletContextEvent sce)  { 
		((BookDBController) sce.getServletContext().getAttribute("bookController")).destroy();;
	}

	
}
