package simple;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcDemo {
    public static void main(String[] args) throws IOException {
        // 1., load db props from file
        String propsParh = JdbcDemo.class.getClassLoader().getResource("db.properties").getPath();
        Properties props = new Properties();
        props.load(new FileInputStream(propsParh));

        // 2. Load DB driver
        try {
            Class.forName(props.getProperty("driver"));
            System.out.println("DB driver loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: DB driver not found");
            e.printStackTrace();
            return;
        }

        // 3. Connect to DB
        try(Connection connection = DriverManager.getConnection(props.getProperty("url"), props)){
            System.out.printf("Successfully connected to: %s", props.getProperty("url"));
            // 4. Create and execute PreparedStatement
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM employees");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
