package example6_with_dao;

import commons.JDBCCredentials;
import example6_with_dao.dao.PersonDAO;
import example6_with_dao.entity.Person;
import org.jetbrains.annotations.NotNull;

import java.sql.DriverManager;
import java.sql.SQLException;

public final class Main {

  private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

  public static void main(@NotNull String @NotNull[] args) {
    try (var connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
      final var dao = new PersonDAO(connection);

      final var name = "Pupkin V.V.";
      dao.save(new Person(4, name));
      dao.all().forEach(System.out::println);
      System.out.println("==============================");
      dao.delete(new Person(1, "Ivanov I.I."));
      dao.update(new Person(4, "Vasiliev V.V"));
      dao.all().forEach(System.out::println);
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }
}
