package demos.servlet;


import demos.dao.BookDBController;
import demos.exception.NonexistingEntityException;
import demos.model.Book;
import demos.model.CartBean;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Servlet implementation class ShoppingServlet
 */
@WebServlet(name="ShoppingServlet", urlPatterns = "/shop")
@Slf4j
public class ShoppingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String SHOPPING_VIEW = "/WEB-INF/jsp/EShop.jsp";
	public static final String CART_VIEW = "/WEB-INF/jsp/Cart.jsp";
	public static final String CHECKOUT_VIEW = "/WEB-INF/jsp/Checkout.jsp";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		boolean invalidateSession = false;
		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession(true);
		res.setLocale(new Locale("en_US"));
		@SuppressWarnings("unchecked")
		List<CartBean> cart = (List<CartBean>) session.getAttribute("shoppingcart");;
		if (cart == null) {
			cart = new ArrayList<CartBean>(); // first order
			session.setAttribute("shoppingcart", cart);
		}
		BookDBController bookController = (BookDBController) getServletContext().getAttribute("bookController");
		String action = req.getParameter("action");
		String nextView = SHOPPING_VIEW;
		if (action != null && action.length() > 0) {
			if (!action.equals("CHECKOUT")) {
				String bookIdStr = req.getParameter("bookId");
				long bookId = 0;
				if (bookIdStr != null && bookIdStr.trim().length() > 0) {
					try {
						bookId = Long.parseLong(bookIdStr);
					} catch (NumberFormatException e) {
					}
					if (bookId > 0) {
						if (action.equals("DELETE")) {
							for (CartBean b : cart)
								if (b.getBook().getId() == bookId) {
									cart.remove(b);
									break;
								}
						} else if (action.equals("ADD")) {
							String quantityStr = req.getParameter("quantity");
							int quantity = 1;
							try {
								quantity = Integer.parseInt(quantityStr);
							} catch (NumberFormatException e) {
							}
							boolean bookInCart = false;
							for (CartBean cb : cart) {
								if (cb.getBook().getId() == bookId) {
									cb.setQuantity(cb.getQuantity() + quantity);
									bookInCart = true;
									break;
								}
							}
							if (!bookInCart) {
								try {
									Book book = bookController.getBookById(bookId);
									cart.add(new CartBean(book, quantity));
								} catch (NonexistingEntityException ex) {
									log.warn(ex.toString());
									req.setAttribute("error", ex.getMessage());
								}
							}
						}
					}
				}
			} else if (action.equals("CHECKOUT")) {
				float total = 0;
				for (CartBean cb : cart) {
					double price = cb.getBook().getPrice();
					int qty = cb.getQuantity();
					total += (price * qty);
				}
				total += 0.005;
				String amount = new Float(total).toString();
				int n = amount.indexOf('.');
				amount = amount.substring(0, n + 3);
				req.setAttribute("amount", amount);
				nextView = CHECKOUT_VIEW;
				invalidateSession = true;
			}
		}
		RequestDispatcher rd = req.getRequestDispatcher(nextView);
		rd.forward(req, res);
		if(invalidateSession) {
			session.invalidate();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
