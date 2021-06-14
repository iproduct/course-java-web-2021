// Copyright 2020 (c) IPT - Intellectual Products & Technologies Ltd.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.package demo.spring.cookiesession.servlet;
package demo.spring.cookiesession.servlet;

import demo.spring.cookiesession.dao.BookDBController;
import demo.spring.cookiesession.exception.NonexistingEntityException;
import demo.spring.cookiesession.model.Book;
import demo.spring.cookiesession.model.CartBean;
import lombok.extern.java.Log;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "ShoppingServlet", urlPatterns = {"/"})
@Log
public class ShoppingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String SHOPPING_VIEW = "/WEB-INF/jsp/EShop.jsp";
    public static final String CART_VIEW = "/WEB-INF/jsp/Cart.jsp";
    public static final String CHECKOUT_VIEW = "/WEB-INF/jsp/Checkout.jsp";

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        boolean invalidateSession = false;
        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession(true);
//		session.setMaxInactiveInterval(30);
        res.setLocale(new Locale("en_US"));
        @SuppressWarnings("unchecked")
        Map<Long, CartBean> cart = (Map<Long, CartBean>) session.getAttribute("shoppingcart");
        if (cart == null) {
            cart = new HashMap<>(); // first order
            session.setAttribute("shoppingcart", cart);
        }
        BookDBController bookController = (BookDBController) getServletContext().getAttribute("bookController");
        String action = req.getParameter("action");
        String nextView = SHOPPING_VIEW;
        if (action != null && action.length() > 0) {
            if (action.equals("CHECKOUT")) {
                float total = 0;
                for (CartBean cb : cart.values()) {
                    double price = cb.getBook().getPrice();
                    int qty = cb.getQuantity();
                    total += (price * qty);
                }
                String amount = String.format("%.2f", total);
                req.setAttribute("amount", amount);
                nextView = CHECKOUT_VIEW;
                invalidateSession = true;
            } else {
                String bookIdStr = req.getParameter("bookId");
                long bookId = 0;
                if (bookIdStr != null && bookIdStr.trim().length() > 0) {
                    try {
                        bookId = Long.parseLong(bookIdStr);
                    } catch (NumberFormatException e) {
                        log.warning("Invalid Book ID: '" + bookIdStr + "'");
                        req.setAttribute("error", "Invalid Book ID: '" + bookIdStr + "'");
                    }
                    if (bookId > 0) {
                        if (action.equals("ADD")) {
                            String quantityStr = req.getParameter("quantity");
                            int quantity = 1;
                            try {
                                quantity = Integer.parseInt(quantityStr);
                            } catch (NumberFormatException e) {
                                log.warning("Invalid quantity: '" + quantityStr + "'");
                                req.setAttribute("error", "Invalid quantity: '" + quantityStr + "'");
                            }
                            var old = cart.get(bookId);
                            boolean bookInCart = false;
                            if (old != null) {
                                old.setQuantity(old.getQuantity() + quantity);
                            } else {
                                try {
                                    Book book = bookController.getBookById(bookId);
                                    cart.put(bookId, new CartBean(book, quantity));
                                } catch (NonexistingEntityException ex) {
                                    log.warning(ex.toString());
                                    req.setAttribute("Book with ID = '" + bookId + "' does not exist",
                                            ex.getMessage());
                                }
                            }
                        } else if (action.equals("DELETE")) {
                            var old = cart.remove(bookId);
                            if(old == null){
                                log.warning("Book with ID = '" + bookId + "' does not exist");
                                req.setAttribute("error", "Book with ID = '" + bookId + "' does not exist");
                            }
                        } else {
                            log.warning("Invalid action: " + action);
                            req.setAttribute("error", "Invalid action: " + action);
                        }
                    }
                } else {
                    log.warning("Book ID should not be empty");
                    req.setAttribute("error", "Book ID should not be empty");
                }
            }
        }
        RequestDispatcher rd = req.getRequestDispatcher(nextView);
        rd.forward(req, res);
        if (invalidateSession) {
            session.invalidate();
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
