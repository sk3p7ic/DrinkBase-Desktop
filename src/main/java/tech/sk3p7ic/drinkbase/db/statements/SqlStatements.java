package tech.sk3p7ic.drinkbase.db.statements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public class SqlStatements {
  private static Logger logger = LoggerFactory.getLogger(SqlStatements.class);

  public static final String GET_CABINETS    = "SELECT * FROM cabinets";
  public static final String GET_INGREDIENTS = "SELECT * FROM ingredients";
  public static final String GET_STOCK       = "SELECT * FROM stock";
  public static final String GET_RECIPES     = "SELECT * FROM recipes";
  public static final String GET_USES        = "SELECT * FROM uses_ingredient";

  public static PreparedStatement getCabinetById(final int cab_id, Connection connection) {
    String sql = GET_CABINETS + " WHERE cab_id = ?";
    try (PreparedStatement prepStmt = connection.prepareStatement(sql)) {
      prepStmt.setInt(1, cab_id);
      return prepStmt;
    } catch (SQLException e) {
      logger.warn("Error creating statement: " + e.getMessage());
      return null;
    }
  }

  public static PreparedStatement getCabinetByName(final String cab_name, Connection connection) {
    String sql = GET_CABINETS + " WHERE cab_name = ?";
    try (PreparedStatement prepStmt = connection.prepareStatement(sql)) {
      prepStmt.setString(1, cab_name);
      return prepStmt;
    } catch (SQLException e) {
      logger.warn("Error creating statement: " + e.getMessage());
      return null;
    }
  }

  public static PreparedStatement getIngredientById(final int ing_id, Connection connection) {
    String sql = GET_INGREDIENTS + " WHERE ing_id = ?";
    try (PreparedStatement prepStmt = connection.prepareStatement(sql)) {
      prepStmt.setInt(1, ing_id);
      return prepStmt;
    } catch (SQLException e) {
      logger.warn("Error creating statement: " + e.getMessage());
      return null;
    }
  }

  public static PreparedStatement getIngredientByName(final String ing_name, Connection connection) {
    String sql = GET_INGREDIENTS + " WHERE ing_name = ?";
    try (PreparedStatement prepStmt = connection.prepareStatement(sql)) {
      prepStmt.setString(1, ing_name);
      return prepStmt;
    } catch (SQLException e) {
      logger.warn("Error creating statement: " + e.getMessage());
      return null;
    }
  }

  public static PreparedStatement getIngredientByType(final String ing_type, Connection connection) {
    String sql = GET_INGREDIENTS + " WHERE ing_type = ?";
    try (PreparedStatement prepStmt = connection.prepareStatement(sql)) {
      prepStmt.setString(1, ing_type);
      return prepStmt;
    } catch (SQLException e) {
      logger.warn("Error creating statement: " + e.getMessage());
      return null;
    }
  }

  public static PreparedStatement getStockById(final int stock_id, Connection connection) {
    String sql = GET_STOCK + " WHERE stock_id = ?";
    try (PreparedStatement prepStmt = connection.prepareStatement(sql)) {
      prepStmt.setInt(1, stock_id);
      return prepStmt;
    } catch (SQLException e) {
      logger.warn("Error creating statement: " + e.getMessage());
      return null;
    }
  }

  public static PreparedStatement getStockByQuantity(final int stock_id, final String comp_opr, Connection connection) {
    String sql = GET_STOCK + " WHERE stock_id "+ comp_opr + " ?";
    try (PreparedStatement prepStmt = connection.prepareStatement(sql)) {
      // Double check that we were given a proper operator
      if (!Set.of("=", ">", ">=", "<", "<=", "!=").contains(comp_opr)) // Using Set for easier (lazier) checking
        throw new IllegalArgumentException("Invalid comparison operator supplied ('" + comp_opr + "').");
      // If everything was good, prepare the statement
      prepStmt.setInt(1, stock_id);
      return prepStmt;
    } catch (SQLException | IllegalArgumentException e) {
      logger.warn("Error creating statement: " + e.getMessage());
      return null;
    }
  }

  public static PreparedStatement getRecipeById(final int rec_id, Connection connection) {
    String sql = GET_RECIPES + " WHERE rec_id = ?";
    try (PreparedStatement prepStmt = connection.prepareStatement(sql)) {
      prepStmt.setInt(1, rec_id);
      return prepStmt;
    } catch (SQLException e) {
      logger.warn("Error creating statement: " + e.getMessage());
      return null;
    }
  }

  public static PreparedStatement getRecipeByName(final String rec_name, Connection connection) {
    String sql = GET_RECIPES + " WHERE rec_name = ?";
    try (PreparedStatement prepStmt = connection.prepareStatement(sql)) {
      prepStmt.setString(1, rec_name);
      return prepStmt;
    } catch (SQLException e) {
      logger.warn("Error creating statement: " + e.getMessage());
      return null;
    }
  }

  public static PreparedStatement getUsesByIngredient(final int ing_id, Connection connection) {
    String sql = GET_USES + " WHERE ing_id = ?";
    try (PreparedStatement prepStmt = connection.prepareStatement(sql)) {
      prepStmt.setInt(1, ing_id);
      return prepStmt;
    } catch (SQLException e) {
      logger.warn("Error creating statement: " + e.getMessage());
      return null;
    }
  }

  public static PreparedStatement getUsesByRecipe(final int rec_id, Connection connection) {
    String sql = GET_USES + " WHERE rec_id = ?";
    try (PreparedStatement prepStmt = connection.prepareStatement(sql)) {
      prepStmt.setInt(1, rec_id);
      return prepStmt;
    } catch (SQLException e) {
      logger.warn("Error creating statement: " + e.getMessage());
      return null;
    }
  }
}
