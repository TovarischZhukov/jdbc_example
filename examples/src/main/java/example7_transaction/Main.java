package example7_transaction;

import commons.JDBCCredentials;
import org.jetbrains.annotations.NotNull;

import java.sql.DriverManager;
import java.sql.SQLException;

public final class Main {

    private static final @NotNull JDBCCredentials CREDS = JDBCCredentials.DEFAULT;

    public static void main(@NotNull String @NotNull [] args) {
        try (var connection = DriverManager.getConnection(CREDS.url(), CREDS.login(), CREDS.password())) {
            try (var statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                int id = 1;
                var name = "Ivanov I.I.";
                statement.executeUpdate("INSERT INTO person(id, name) VALUES (" + id + ",'" + name + "')");
                id++;
                name = "Petrov P.P.";
                statement.executeUpdate("INSERT INTO person(id, name) VALUES (" + id + ",'" + name + "')");
                connection.commit();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            System.out.println("Success");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
