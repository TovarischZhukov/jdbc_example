package example1_simple;

import commons.FlywayInitializer;
import commons.JDBCCredentials;
import org.jetbrains.annotations.NotNull;

import java.sql.DriverManager;
import java.sql.SQLException;

public final class Main {

    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static void main(@NotNull String @NotNull [] args) {
        FlywayInitializer.initDb();
        try (var connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            try (var statement = connection.createStatement()) {
                int id = 1;
                var name = "Ivanov I.I.";
                statement.executeUpdate("INSERT INTO person(id, name) VALUES (" + id + ",'" + name + "')");
                id++;
                name = "Petrov P.P.";
                statement.executeUpdate("INSERT INTO person(id, name) VALUES (" + id + ",'" + name + "')");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            try (var statement = connection.createStatement()) {
                try (var resultSet = statement.executeQuery("SELECT id, name FROM person")) {
                    /*final var metaData = resultSet.getMetaData();
                    int columns = metaData.getColumnCount();
                    for (int i = 1; i <= columns; i++) {
                        System.out.print("\t\t" + metaData.getColumnLabel(i));
                    }*/
                    System.out.println();
                    while (resultSet.next()) {
                        final int id = resultSet.getInt("id");
                        final var name = resultSet.getString("name");
                        System.out.println("\t\t" + id + "\t\t" + name);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }


        System.out.println("Success");
    }
}
