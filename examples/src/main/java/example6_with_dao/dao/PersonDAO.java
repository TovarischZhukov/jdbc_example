package example6_with_dao.dao;

import commons.DAO;
import example6_with_dao.entity.Person;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"NotNullNullableValidation", "SqlNoDataSourceInspection", "SqlResolve"})
public final class PersonDAO implements DAO<Person> {

  private final @NotNull Connection connection;

  public PersonDAO(@NotNull Connection connection) {
    this.connection = connection;
  }

  @Override
  public @NotNull Person get(int id) {
    try (var statement = connection.createStatement()) {
      try (var resultSet = statement.executeQuery("SELECT id, name FROM person WHERE id = " + id)) {
        if (resultSet.next()) {
          return new Person(resultSet.getInt("id"), resultSet.getString("name"));
        }
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    throw new IllegalStateException("Record with id " + id + "not found");
  }

  @Override
  public @NotNull List<Person> all() {
    final var result = new ArrayList<Person>();
    try (var statement = connection.createStatement()) {
      try (var resultSet = statement.executeQuery("SELECT * FROM person")) {
        while (resultSet.next()) {
          result.add(new Person(resultSet.getInt("id"), resultSet.getString("name")));
        }
        return result;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public void save(@NotNull Person entity) {
    try (var preparedStatement = connection.prepareStatement("INSERT INTO person(id,name) VALUES(?,?)")) {
      preparedStatement.setInt(1, entity.id);
      preparedStatement.setString(2, entity.name);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(@NotNull Person entity) {
    try(var preparedStatement = connection.prepareStatement("UPDATE person SET name = ? WHERE id = ?")) {
      int fieldIndex = 1;
      preparedStatement.setString(fieldIndex++, entity.name);
      preparedStatement.setInt(fieldIndex, entity.id);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void delete(@NotNull Person entity) {
    try( var preparedStatement = connection.prepareStatement("DELETE FROM person WHERE id = ?")) {
      preparedStatement.setInt(1, entity.id);
      if (preparedStatement.executeUpdate() == 0) {
        throw new IllegalStateException("Record with id = " + entity.name + " not found");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
