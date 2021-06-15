<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
  <title>Best Books Forever</title>
</head>
<body bgcolor="#33CCFF">
  <h2>
    Best Books Forever
  </h2>
  <hr><p>

  <form name="shoppingForm"
    action="ShoppingServlet"
    method="POST">
  <b>Choose a Book:</b>
    <select name="bookId">
  	<c:set var="books" value="${applicationScope.bookController.allBooks}" />
  	<c:forEach var="book" items="${books}">
	    <option value="${book.id}">
	    	<c:out value="${book.title}" escapeXml="true"></c:out> -
	    	<c:out value="${book.price}" escapeXml="true"></c:out>
	    </option>
    </c:forEach>
   </select>
  <b>Quantity: </b><input type="text" name="quantity" SIZE="3" value=1>
  <input type="hidden" name="action" value="ADD">
  <input type="submit" name="Submit" value="Add to Cart">
  </form>
  <p>
  <c:if test="${fn:length(shoppingcart.values()) > 0}">
  	<jsp:include page="Cart.jsp" flush="true" />
  </c:if>
  <c:if test="${fn:length(requestScope.error) > 0}">
    <div style="color:red; background: #ffbbbb; border: 1px solid red; padding: 10px;">
      <c:out value="${requestScope.error}" escapeXml="true"></c:out>
    </div>
  </c:if>
</body>
</html>
