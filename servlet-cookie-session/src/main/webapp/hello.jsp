<%@ page import="demo.spring.cookiesession.dao.BookDBController" %>
<%@ page import="demo.spring.cookiesession.model.Book" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%!
    private BookDBController bookController;
    @Override
    public void jspInit(){
        bookController = (BookDBController) getServletConfig()
                .getServletContext().getAttribute("bookController");
    }
    %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<h2><%= "Your Books:" %></h2>
<ul>
<%
    for(Book b: bookController.getAllBooks()){
%>
    <li><%= b.getId() + ": " + b.getTitle() + " - " + b.getPrice()%></li>
<% } %>
</ul>
<br>
<a href="/">Start Shopping</a>
</html>
