<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
  <div style="margin: auto">
  <table border="0" cellpadding="0" width="100%" bgcolor="#FFFFFF">
    <tr>
      <td><b>TITLE</b></td>
      <td><b>AUTHOR</b></td>
      <td><b>YEAR</b></td>
      <td><b>PRICE</b></td>
      <td><b>QUANTITY</b></td>
      <td></td>
    </tr>
    <c:forEach var="cb" items="${shoppingcart.values()}">
    <tr>
      <td><b><c:out value="${cb.book.title}" /></b></td>
      <td><b><c:out value="${cb.book.authors}" /></b></td>
      <td><b><c:out value="${cb.book.publishDate}" /></b></td>
      <td><b><c:out value="${cb.book.price}" /></td>
      <td><b><c:out value="${cb.quantity}" /></b></td>
      <td>
        <form name="deleteForm" action="ShoppingServlet" method="POST">
          <input type="hidden" name= "bookId" value="${cb.book.id}">
          <input type="hidden" name="action" value="DELETE">
          <input type="submit" value="Delete">
         </form> 
      </td>
    </tr> 
   	</c:forEach>
  </table>
  <p>
  <form name="checkoutForm" action="ShoppingServlet" method="POST">
    <input type="hidden" name="action" value="CHECKOUT">
    <input type="submit" name="Checkout" value="Checkout">
  </form>
  </div>

