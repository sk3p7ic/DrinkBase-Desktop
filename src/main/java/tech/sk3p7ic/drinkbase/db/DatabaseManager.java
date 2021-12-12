package tech.sk3p7ic.drinkbase.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseManager {
  private static Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

  protected Connection connection;

  public DatabaseManager(String databasePath) throws Exception {
    if (!(new File(databasePath).exists())) {
      try {
        this.connection = createDB(databasePath);
      } catch (SQLException | FileNotFoundException e) {
        throw new Exception("There was an error creating database: " + e.getMessage());
      }
    } else {
      final String url = "jdbc:sqlite" + databasePath;
      try {
        this.connection = DriverManager.getConnection(url);
      } catch (SQLException e) {
        throw new Exception("There was an error loading the database: " + e.getMessage());
      }
    }
  }

  /**
   * Creates a new sqlite database file if one is not present.
   * @param databasePath The path to the database file.
   * @throws SQLException If there was an error creating the connection to the database file.
   * @throws FileNotFoundException If there was an error opening the DDL file.
   */
  private static Connection createDB(final String databasePath) throws SQLException, FileNotFoundException {
    final String url = "jdbc:sqlite:" + databasePath; // Get the path for db
    Connection conn = DriverManager.getConnection(url);
    if (conn != null) {
      // Execute the statements
      for (String code : readDDLFileLines().split("\n")) {
        Statement statement = conn.createStatement();
        statement.execute(code);
        logger.info("Executed statement: " + code);
      }
    }
    return conn;
  }

  /**
   * Reads the contents of the create_db.ddl file. Assumes that all comments are on their own line and are prefixed with
   * "--". Comments starting with a semicolon, ';', are not supported and will break the code.
   * @return A String containing the sql statements that are to be executed.
   */
  private static String readDDLFileLines() throws FileNotFoundException {
    final File ddlFile = new File("src\\main\\resources\\create_db.ddl");
    final Scanner fileScanner = new Scanner(ddlFile); // Used to read the ddl file
    final StringBuilder stringBuilder = new StringBuilder();
    while (fileScanner.hasNextLine()) {
      // Get the line
      final String line = fileScanner.nextLine();
      // Double check that line is not commented
      if (!line.contains("--")) { // Skip if the line is commented
        if (line.contains(";")) stringBuilder.append(line).append("\n"); // If the end of a statement is in the line
        else stringBuilder.append(line.strip()).append(" ");
      }
    }
    return stringBuilder.toString(); // Return the final sql code String
  }
}
