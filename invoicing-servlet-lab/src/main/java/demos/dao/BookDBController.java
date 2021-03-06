package demos.dao;



import demos.exception.NonexistingEntityException;
import demos.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDBController {
	public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/javaweb";
	public static final String DB_USER = "root";
	public static final String DB_PASSWORD = "root";

	private List<Book> availableBooks = new ArrayList<Book>();

	public void init() throws ClassNotFoundException {
		Class.forName(DB_DRIVER); // load db driver
	}

	public void reload() {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery("SELECT * FROM mybooks ORDER BY id");
			while (rs.next()) {
				availableBooks.add(new Book(rs.getLong(1), rs.getString(2), rs.getString(3), 
						rs.getString(4), rs.getString(5), rs.getString(6), rs.getDate(7), 
						rs.getDouble(8)));
			}
			System.out.println(availableBooks);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void destroy() {
		// close connection here
	}

	public List<Book> getAllBooks() {
		return availableBooks;
	}

	public Book getBookById(long id) throws NonexistingEntityException {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery("SELECT * FROM mybooks WHERE id=" + id);
			if (rs.next()) {
				return new Book(rs.getLong(1), rs.getString(2), rs.getString(3), 
						rs.getString(4), rs.getString(5), rs.getString(6), rs.getDate(7), 
						rs.getDouble(8));
			}
		} catch (SQLException ex) {
			throw new NonexistingEntityException("Book with ID=" + id +" not found.", ex);
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		BookDBController bdc = new BookDBController();
		bdc.init();
		bdc.reload();
		bdc.getAllBooks().stream().forEach(System.out::println);
	}
}
