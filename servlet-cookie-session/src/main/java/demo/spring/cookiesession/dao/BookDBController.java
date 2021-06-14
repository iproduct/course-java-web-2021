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
// limitations under the License.

package demo.spring.cookiesession.dao;

import demo.spring.cookiesession.exception.NonexistingEntityException;
import demo.spring.cookiesession.model.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookDBController {
	public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/javaweb";
	public static final String DB_USER = "root";
	public static final String DB_PASSWORD = "root";

	private Map<Long, Book> availableBooks = new ConcurrentHashMap<>();

	public void init() throws ClassNotFoundException {
		Class.forName(DB_DRIVER); // load db driver
	}
	public void destroy() {
	}

	public void reload() {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery("SELECT * FROM mybooks ORDER BY id");
			while (rs.next()) {
				availableBooks.put(rs.getLong(1),
						new Book(rs.getLong(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6), rs.getDate(7), 
						rs.getDouble(8)));
			}
			System.out.println(availableBooks);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Collection<Book> getAllBooks() {
		return availableBooks.values();
	}

	public Book getBookById(Long id) throws NonexistingEntityException {
		return availableBooks.get(id);
	}

	public static void main(String[] args) throws Exception {
		BookDBController bdc = new BookDBController();
		bdc.init();
		bdc.reload();
		bdc.getAllBooks().stream().forEach(System.out::println);
	}
}
