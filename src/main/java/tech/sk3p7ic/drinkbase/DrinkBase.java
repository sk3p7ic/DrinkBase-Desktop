package tech.sk3p7ic.drinkbase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.sk3p7ic.drinkbase.db.DatabaseManager;

public class DrinkBase {
  private static Logger logger = LoggerFactory.getLogger(DrinkBase.class);

  public static void main(String[] args) {
    System.out.println("Hello world!");
    try {
      DatabaseManager databaseManager = new DatabaseManager("drinkbase.db");
    } catch (Exception e) {
      logger.error(e.getMessage());
      System.out.println("Exiting...");
    }
  }
}
