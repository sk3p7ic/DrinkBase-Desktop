package tech.sk3p7ic.drinkbase.db.tables.objects;

import javafx.scene.image.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.sk3p7ic.drinkbase.db.DatabaseManager;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static tech.sk3p7ic.drinkbase.db.statements.SqlStatements.*;

public class IngredientsObject {
  private static final Logger logger = LoggerFactory.getLogger(IngredientsObject.class);

  public final int ing_id;
  public final String ing_name;
  public final String ing_type;
  public final Image ing_picture;

  private DatabaseManager databaseManager;

  public IngredientsObject(final ResultSet resultSet, DatabaseManager databaseManager) throws SQLException {
    ing_id = resultSet.getInt("ing_id");
    ing_name = resultSet.getString("ing_name");
    ing_type = resultSet.getString("ing_type");
    ing_picture = new Image(resultSet.getBinaryStream("ing_picture"));
    this.databaseManager = databaseManager;
  }

  public IngredientsObject(final int ing_id, final String ing_name, final String ing_type, final Image ing_picture,
                           DatabaseManager databaseManager) {
    this.ing_id = ing_id;
    this.ing_name = ing_name;
    this.ing_type = ing_type;
    this.ing_picture = ing_picture;
    this.databaseManager = databaseManager;
  }

  public static IngredientsObject getIngredientById(final int ing_id, DatabaseManager databaseManager) {
    String sql = GET_INGREDIENTS + " WHERE ing_id = ?";
    try (PreparedStatement prepStmt = databaseManager.getConnection().prepareStatement(sql)) {
      prepStmt.setInt(1, ing_id);
      return new IngredientsObject(databaseManager.execute(prepStmt), databaseManager);
    } catch (SQLException e) {
      logger.warn("Error executing statement: " + e.getMessage());
      return null;
    }
  }

  public static IngredientsObject getIngredientByName(final String ing_name, DatabaseManager databaseManager) {
    String sql = GET_INGREDIENTS + " WHERE ing_name = ?";
    try (PreparedStatement prepStmt = databaseManager.getConnection().prepareStatement(sql)) {
      prepStmt.setString(1, ing_name);
      return new IngredientsObject(databaseManager.execute(prepStmt), databaseManager);
    } catch (SQLException e) {
      logger.warn("Error creating statement: " + e.getMessage());
      return null;
    }
  }

  public static IngredientsObject getIngredientByType(final String ing_type, DatabaseManager databaseManager) {
    String sql = GET_INGREDIENTS + " WHERE ing_type = ?";
    try (PreparedStatement prepStmt = databaseManager.getConnection().prepareStatement(sql)) {
      prepStmt.setString(1, ing_type);
      return new IngredientsObject(databaseManager.execute(prepStmt), databaseManager);
    } catch (SQLException e) {
      logger.warn("Error creating statement: " + e.getMessage());
      return null;
    }
  }

  public boolean insertValues() {
    try (PreparedStatement prepStmt = databaseManager.getConnection().prepareStatement(SET_INGREDIENT)) {
      prepStmt.setInt(1, ing_id);
      prepStmt.setString(2, ing_name);
      prepStmt.setString(3, ing_type);
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); // Create stream for storing image as bytes
      ImageIO.write((RenderedImage) ing_picture, "png", outputStream); // Write the image to the stream
      prepStmt.setBlob(4, new ByteArrayInputStream(outputStream.toByteArray()));
      // Execute the statement
      prepStmt.executeUpdate();
      logger.info("Executed the following: " + prepStmt.toString());
      return true;
    } catch (SQLException | IOException e) {
      logger.warn("Error executing statementL " + e.getMessage());
      return false;
    }
  }

}
